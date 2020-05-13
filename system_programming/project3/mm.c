/*
 * 2014-19498 EUNJOO JUNG
 * PERFORMANCE = 95/100
 * mm.c - implemented by the explicit allocator and the segregated list
 *        and it uses best fit for the finding the appropriate free blocks.
 *
 * At first by the init function, heap is initialized and also the segregated
 * list is initialized. Each block has the 4 byte (WSIZE) Header and the 4 byte
 * (WSIZE) Footer which has size info and allocation info. Allocated Block has
 * Header and Footer with size info and allocation info and the Freed Block has
 * Previous free block pointer and Next free block pointer in the class of
 * segregated list. The segregated list's size is WSIZE * the mumber of seglists(24)
 * In each class of Segregated list has size which is multiply of 2 from 2^n until
 * 2^(n+1)-1. Coalescing is needed whenever the blcok is freed and realloc is
 * implemented for the minimum use of the memcpy and mm_malloc and free function
 * so that it can maximizes the utility and throughput of realloc.
 */

/* ALLOCED BLOCK
 *       31  30  29  28  27  26  25  24 ..........  4  3  2  1  0
 *      |--------------------------------------------------------|
 *      |  HEADER ( SIZE INFO )             -> alloced |  |  | 1 |
 * bp-> |--------------------------------------------------------|
 *      |                                                        |
 *      |                 PAYLOAD AND PADDING                    |
 *      |                                                        |
 *      |--------------------------------------------------------|
 *      | FOOTER (SIZE INFO )               -> alloced |  |  | 1 |
 *      |--------------------------------------------------------|
 */

/* FREED BLOCK
 *       31  30  29  28  27  26  25  24 ..........  4  3  2  1  0
 *      |--------------------------------------------------------|
 *      |  HEADER ( SIZE INFO )           -> dealloced |  |  | 0 |
 * bp-> |--------------------------------------------------------|
 *      |  PREV FREE BLOCK POINTER                               |
 *      |--------------------------------------------------------|
 *      |  NEXT FREE BLOCK POINTER                               |
 *      |--------------------------------------------------------|
 *      |                                                        |
 *      |--------------------------------------------------------|
 *      | FOOTER ( SIZE INFO )            -> dealloced |  |  | 0 |
 *      |--------------------------------------------------------|
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <string.h>

#include "mm.h"
#include "memlib.h"

/* single word (4) or double word (8) alignment */
#define ALIGNMENT 8

/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(size) (((size) + (ALIGNMENT-1)) & ~0x7)
#define SIZE_T_SIZE (ALIGN(sizeof(size_t)))

/* Basic constants and macros */
#define WSIZE   4               /* Word and header/footer size (bytes) */
#define DSIZE   8               /* Double word size (bytes) */
#define CHUNKSIZE (1<<6)        /* Extend heap by this amount (bytes) */
#define DCHUNKSIZE (1<<12)
#define MAX(x, y) ((x) > (y)?  (x) : (y))

/* Pack a size and allocated bit into a word */
#define PACK(size, alloc) ((size) | (alloc))

/* Read and write a word at address p */
#define GET(p)      (*(unsigned int *)(p))
#define PUT(p, val) (*(unsigned int *)(p) = (val))

/* Read the size and allocated fields from address */
#define GET_SIZE(p)     (GET(p) & ~0x7)
#define GET_ALLOC(p)    (GET(p) & 0x1)

/* Given block ptr bp, compute address of its header and footer */
#define HDRP(bp)    ((char *)(bp) - WSIZE)
#define FTRP(bp)    ((char *)(bp) + GET_SIZE(HDRP(bp)) - DSIZE)

/* Given block ptr bp, compute address of next and previous blocks */
#define NEXT_BLKP(bp)   ((char *)(bp) + GET_SIZE(((char *)(bp) - WSIZE)))
#define PREV_BLKP(bp)   ((char *)(bp) - GET_SIZE(((char *)(bp) - DSIZE)))

/* Segregated list max number */
#define SEG_LIST  24
/* Minimum size for the efficiency */
#define MINSIZE 200

/* Helper macros for the segregated list */
#define GET_VAL(ptr)        ((char **)(ptr))
#define UNSIGN(p)           ((unsigned int)(p))
#define GET_BLK_SIZE(ptr)   (GET_SIZE(HDRP(ptr)))
#define SET_SEG_LIST_PTR(ptr, idx, val)  (*(GET_VAL(ptr) + idx) = val)
#define GET_SEG_LIST_PTR(ptr, idx) (*(GET_VAL(ptr) + idx))

/* each class's address in the seglist */
#define GET_PREV_ADR(bp)    ((char *)(bp))
#define GET_NEXT_ADR(bp)    ((char *)(bp) + WSIZE)

/* each actual address of classes in the seglist */
#define GET_PREV_BLK(bp)    (*(GET_VAL(bp)))
#define GET_NEXT_BLK(bp)    (*(GET_VAL(GET_NEXT_ADR(bp))))

/* global pointer variables */
static char *heap_listp = 0;
static void *seg_listp;   /* pointer for the first element of seg_list*/

/* function prototypes for helper functions */
static void *extend_heap(size_t words);
static void *coalesce(void *bp);
static void *find_fit(size_t asize, size_t csize, int index);
static void *place(void *bp, size_t asize);
static void add_free_block(void *bp, size_t blk_size);
static void delete_free_block(void *bp);
static int mm_check(void);


/*
 * mm_init - initialize the malloc package.
 * seg lists are initialized at the bottom of heap.
 * and creates the initial empty heap.
 */
int mm_init(void)
{
    /* Making space for the seg lists */
    seg_listp = mem_sbrk(SEG_LIST * WSIZE);
    
    /* initialize the seg_lists */
    for(int i = 0; i<SEG_LIST; i++) {
        SET_SEG_LIST_PTR(seg_listp, i, NULL);
    }
    
    /* Create the initial empty heap */
    if ((heap_listp = mem_sbrk(4*WSIZE)) == (void *)-1)
        return -1;
        
    PUT(heap_listp, 0);                             /* Alignment padding */
    PUT(heap_listp + (1*WSIZE), PACK(DSIZE, 1));    /* Prologue header */
    PUT(heap_listp + (2*WSIZE), PACK(DSIZE, 1));    /* Prologure header */
    PUT(heap_listp + (3*WSIZE), PACK(0, 1));        /* Epilogue header */
    heap_listp += (2*WSIZE);
    
    /* Extend the empty heap with a free block of CHUNKSIZE bytes */
    if (extend_heap(CHUNKSIZE/WSIZE) == NULL)
        return -1;
    
    return 0;
}

 
/*
 * mm_malloc - Allocate a block by incrementing the brk pointer.
 * Always allocate a block whose size is a multiple of the alignment.
 * if the appropriate size of block is found through the function
 * find_fit, then it can be allocated by function place. Otherwise
 * heap should be extended for the allocation.
 */
void *mm_malloc(size_t size)
{
    size_t asize;            /* Adjusted block size */
    size_t extendsize;       /* Amount to extend heap if no fit */
    char *bp;

    /* Ignore spurious requests */
    if (size <= 0)
        return NULL;
       
    /* Adjust block size to include overhead and alignment reqs */
    if (size <= 2*DSIZE)
         asize = 3*DSIZE;
    else
         asize = ALIGN(size + DSIZE);

    /* Search the free list for a fit */
    if ((bp = find_fit(asize, asize, 0)) != NULL) {
        return place(bp, asize);
    }
    
    /* No fit found. Get more memory and place the block */
    extendsize = MAX(asize, DCHUNKSIZE);
    if ((bp = extend_heap(extendsize/WSIZE)) == NULL)
        return NULL;

    return place(bp, asize);
}


/*
 * mm_free - Freeing a block by update allocation bit, and insert
 * into the seg_list for reuse. Coalesce if possible.
 */
void mm_free(void *bp)
{
    size_t size = GET_BLK_SIZE(bp);

    PUT(HDRP(bp), PACK(size, 0));
    PUT(FTRP(bp), PACK(size, 0));
    
    add_free_block(bp, size);
    coalesce(bp);

    return;
}

/*
 * mm_realloc - Implemented in terms of mm_malloc and mm_free
 * there are many cases to consider
 * 1) the pointer is NULL -> mm_malloc
 * 2) the size is ZERO -> mm_free
 * 3) (old size == new size) -> return old_ptr
 * 4) (old size > new size) -> make free block and realloc
 * 5) (old size < new size) -> if there's space in previous or
 *    next block, and if they are free blocks then they can be
 *    merged with current block and it means "realloced"
 * 6) totally new allocated -> use mm_malloc and free
 */
void *mm_realloc(void *ptr, size_t size)
{
    void *oldptr = ptr;
    void *newptr = NULL;
    int realloced = 0;

    /* if the ptr is Null then just calling malloc(ptr) */
    if(ptr == NULL)
        return mm_malloc(size);

    /* if size is zero then just free the ptr */
    if(size == 0) {
        mm_free(ptr);
        return NULL;
    }
    
    /* except the size of header and footer */
    size_t csize = GET_BLK_SIZE(ptr) - DSIZE;
    
    /* 8 byte aligned */
    size_t asize = ALIGN(size);
    
    /* cheking the size between before realloc and after realloc */
    /* if the size of ptr to realloc is the same as the oldptr's size */
    size_t padding = 0;
    if(asize == csize)
        return oldptr;

    /* if the size of ptr to realloc is smaller than the oldptr's size */
    if(asize < csize) {
        padding = csize - asize;
        if(padding <= DSIZE) {
            return oldptr;
        }
        PUT(HDRP(oldptr), PACK(asize+DSIZE, 1));
        PUT(FTRP(oldptr), PACK(asize+DSIZE, 1));
        newptr = oldptr;
        realloced = 1;
    }
    /* if the size of ptr to realloc is bigger than the oldptr's size */
    else if (asize > csize) {
        void *next_ptr = NULL;
        void *prev_ptr = NULL;
        
        next_ptr = NEXT_BLKP(oldptr);
        prev_ptr = PREV_BLKP(oldptr);
        size_t nsize, psize;
        size_t new_size = 0;
        
        nsize = (next_ptr) ? GET_BLK_SIZE(next_ptr) : 0;
        psize = (prev_ptr) ? GET_BLK_SIZE(prev_ptr) : 0;
        
        /* if previous block can be merged */
        if(prev_ptr!= NULL && !GET_ALLOC(HDRP(prev_ptr))) {
            if(psize + csize >= asize)
            {
                padding = csize + psize - asize;
                delete_free_block(prev_ptr);
                new_size = (padding <= DSIZE) ?
                            csize+psize+DSIZE : asize+DSIZE;
                newptr = prev_ptr;
                /* realloc with new size */
                PUT(HDRP(newptr), PACK(new_size, 1));
                memmove(newptr, oldptr, csize+DSIZE);
                PUT(FTRP(newptr), PACK(new_size, 1));
                realloced = 1;
            }
        }
        /* if next block can be merged */
        else if(next_ptr != NULL && !GET_ALLOC(HDRP(next_ptr))) {
            if(nsize + csize >= asize) {
                padding = csize + nsize - asize;
                delete_free_block(next_ptr);
                new_size = (padding <= DSIZE) ?
                            csize+nsize+DSIZE : asize+DSIZE;
                /* realloc with new size */
                PUT(HDRP(oldptr), PACK(new_size, 1));
                PUT(FTRP(oldptr), PACK(new_size, 1));
                newptr = oldptr;
                realloced = 1;
            }
        }
        
        /* if the sum of previous, next and current block size satisfies
         * the new size, then they can be merged and realloced */
        if(!GET_ALLOC(HDRP(prev_ptr)) && !GET_ALLOC(HDRP(next_ptr))) {
            if(psize + nsize + csize >= asize)
            {
                padding = csize + psize + nsize - asize;
                delete_free_block(next_ptr);
                delete_free_block(prev_ptr);
                new_size = (padding<=DSIZE) ?
                            csize+psize+nsize+DSIZE : asize+DSIZE;
                newptr = prev_ptr;
                /* realloc with new size */
                PUT(HDRP(newptr), PACK(new_size, 1));
                memmove(newptr, oldptr, csize+DSIZE);
                PUT(FTRP(newptr), PACK(new_size, 1));
                realloced = 1;
            }
        }
    }
    /* For the case of success of REALLOC */
    if(realloced) {
        void * ret = NULL;
        /* if the remaining size after the realloc is bigger than the block
         * size then the remaining block can be free block */
        if(padding > DSIZE) {
            ret = NEXT_BLKP(newptr);
            PUT(HDRP(ret), PACK(padding, 0));
            PUT(FTRP(ret), PACK(padding, 0));
            add_free_block(ret, GET_BLK_SIZE(ret));
            coalesce(ret);
        }
    }
    /* For the case of Failure of REALLOC */
    else {
        newptr = mm_malloc(size);
        if(newptr == NULL)
            return NULL;
        memcpy(newptr, oldptr, csize+DSIZE);
        mm_free(oldptr);
    }
    
    return newptr;
}


/*
 * extend_heap - invoked in two different circumstances
    (1) when the heap is initialized and (2) when mm_malloc is unable to find a suitable fit.
    To maintain alignment, extend_heap rounds up the requesteds size to the nearest multiple
    of 8bytes and the requests the additional heap size from the memory system
 */
static void *extend_heap(size_t words)
{
    char *bp;
    size_t size;
    
    /* Allocate an even number of words to maintain alignment */
    size = (words % 2) ? (words + 1)*WSIZE : words * WSIZE;
    if ((long)(bp = mem_sbrk(size)) == -1)
        return NULL;
    
    /* Initialize free block header/footer and the epilogue header */
    PUT(HDRP(bp), PACK(size, 0));           /* Free block header */
    PUT(FTRP(bp), PACK(size, 0));           /* Free block footer */
    PUT(HDRP(NEXT_BLKP(bp)), PACK(0, 1));   /* New epilogue header */
    
    /* insert free block */
    add_free_block(bp, size);

    /* Coalesce if the previous block was free */
    return coalesce(bp);
}


/*
    coalesce - if previous or next block is free block then merge blocks.
    (1) if previous block and next block are allocated blocks.
    (2) if only next block is free block.
    (3) if only previous block is free block.
    (4) if both next and previous blocks are free blocks.
 */
static void * coalesce(void *bp)
{
    size_t prev_alloc = GET_ALLOC(HDRP(PREV_BLKP(bp)));
    size_t next_alloc = GET_ALLOC(HDRP(NEXT_BLKP(bp)));
    size_t size = GET_SIZE(HDRP(bp));

    /* Case 1 if previous block and next block are allocated blocks. */
    if(prev_alloc && next_alloc) {
        return bp;
    }
    
    /* coalescing needed and the current free block can be deleted */
    delete_free_block(bp);

    /* Case 2 if only next block is free block. */
    if (prev_alloc && !next_alloc) {
        size += GET_SIZE(HDRP(NEXT_BLKP(bp)));
        delete_free_block(NEXT_BLKP(bp));
        PUT(HDRP(bp), PACK(size, 0));
        PUT(FTRP(bp), PACK(size, 0));
    }
    /* Case 3 if only previous block is free block. */
    else if (!prev_alloc && next_alloc) {
        delete_free_block(PREV_BLKP(bp));
        size += GET_SIZE(HDRP(PREV_BLKP(bp)));
        PUT(FTRP(bp), PACK(size, 0));
        PUT(HDRP(PREV_BLKP(bp)), PACK(size, 0));
        bp = PREV_BLKP(bp);
    }
    /* Case 4 if both next and previous blocks are free blocks */
    else {
        size += GET_SIZE(HDRP(PREV_BLKP(bp))) + GET_SIZE(FTRP(NEXT_BLKP(bp)));
        delete_free_block(PREV_BLKP(bp));
        delete_free_block(NEXT_BLKP(bp));
        PUT(HDRP(PREV_BLKP(bp)), PACK(size, 0));
        PUT(FTRP(NEXT_BLKP(bp)), PACK(size, 0));
        bp = PREV_BLKP(bp);
    }

    /* add new free block according to the coalescing rule */
    add_free_block(bp, size);
    return bp;
}


/*
 * find_fit - returns a ptr to a free block that uses best-fit strategy,
 * scanning through the list for the smallest block that fits asize.
 * Using the recursive function, search through all the classes in list
 * until the appropriate smallest block is found.
 */

static void *find_fit(size_t asize, size_t csize, int index)
{
    /* best-fit search */
    if(index > SEG_LIST)
        return NULL;
    
    void *seg_ptr = NULL;
    /* set the list ptr for the seg list */
    seg_ptr = GET_SEG_LIST_PTR(seg_listp, index);
    
    /* search the best block for the asize */
    if(csize <= 1 && seg_ptr != NULL) {
        while(seg_ptr != NULL && (asize > GET_BLK_SIZE(seg_ptr)))
            seg_ptr = GET_PREV_BLK(seg_ptr);
        /* if found then return */
        if(seg_ptr != NULL)
            return seg_ptr;
    }
    csize >>= 1;
    /* if not fount search another class */
    return find_fit(asize, csize, index+1);
}

/*
 * place - returns a pointer for the appropriate position for the
 * requested size of block in the free list so that this block
 * can be allocated later in mm_malloc function. For the efficiency
 * of the performance the MINSIZE is manually added.
 */
static void *place(void *bp, size_t asize)
{
    size_t csize = GET_BLK_SIZE(bp);
    size_t padding = csize - asize;
    void *nbp = NULL;
    delete_free_block(bp);
    
    /* if the remaining is not enough for the minimum block size(16 byte) */
    if(padding < 2*DSIZE) {
        PUT(HDRP(bp), PACK(csize, 1));
        PUT(FTRP(bp), PACK(csize, 1));
        return bp;
    }
    /* the boundary size is set arbitrary for the total performance */
    else if(asize <= MINSIZE) {
        PUT(HDRP(bp), PACK(padding, 0));
        PUT(FTRP(bp), PACK(padding, 0));
        nbp = NEXT_BLKP(bp);
        PUT(HDRP(nbp), PACK(asize, 1));
        PUT(FTRP(nbp), PACK(asize, 1));
        add_free_block(bp, padding);
        return nbp;
    } else {
        PUT(HDRP(bp), PACK(asize, 1));
        PUT(FTRP(bp), PACK(asize, 1));
        nbp = NEXT_BLKP(bp);
        PUT(HDRP(nbp), PACK(padding, 0));
        PUT(FTRP(nbp), PACK(padding, 0));
        add_free_block(nbp, padding);
        return bp;
    }
}

/*
 * add free block - for the segregated list, this function manages
 * the seg list with the declared macros which are pointers for the
 * classes and addresses of each class in seg list. At first it has
 * none. When the new free block comes in then it's position is set by
 * the presence of the previous and the next block address.
 */
static void add_free_block(void *bp, size_t blk_size)
{
    void *pos = NULL;
    void *prev_pos = NULL;
    int index;
  
    /* find the offset(=index) of the seg list */
    for(index = 0; index < SEG_LIST-1; index++) {
        /* overflow occured -> found the index of seglist */
        if(blk_size <= 1)
            break;
        
        blk_size >>= 1;
    }
    
    /* the blk_size's class address of segregated list */
    pos = GET_SEG_LIST_PTR(seg_listp, index);
    
    while(pos != NULL && blk_size > GET_BLK_SIZE(pos)) {
        prev_pos = pos;
        pos = GET_PREV_BLK(pos);
    }
    
    /* bp is between seg list and the next item */
    if (pos && prev_pos) {
        PUT(GET_PREV_ADR(prev_pos), UNSIGN(bp));
        PUT(GET_NEXT_ADR(bp), UNSIGN(prev_pos));
        PUT(GET_PREV_ADR(bp),UNSIGN(pos));
        PUT(GET_NEXT_ADR(pos), UNSIGN(bp));
    }
    else if (pos && !prev_pos) {
    /* bp is the first item in seg list, insert at start */
        PUT(GET_NEXT_ADR(pos), UNSIGN(bp));
        PUT(GET_PREV_ADR(bp), UNSIGN(pos));
        PUT(GET_NEXT_ADR(bp), UNSIGN(NULL));
        SET_SEG_LIST_PTR(seg_listp, index, bp);
    }
    /* next to bp there is no item  */
    else if (!pos && prev_pos) {
        PUT(GET_NEXT_ADR(bp), UNSIGN(prev_pos));
        PUT(GET_PREV_ADR(prev_pos), UNSIGN(bp));
        PUT(GET_PREV_ADR(bp), UNSIGN(NULL));
    }
    /* bp is the first item and next to bp there is no item */
    else {
        PUT(GET_PREV_ADR(bp), UNSIGN(NULL));
        PUT(GET_NEXT_ADR(bp), UNSIGN(NULL));
        SET_SEG_LIST_PTR(seg_listp, index, bp);
    }
    return;
}
    
/*
 * delete free block - for the segregated list, this function manages
 * the seg list with the declared macros which are pointers for the
 * classes and addresses of each class in seg list. If the allocate
 * block should be deleted then it should first find the location in
 * the seg list and then according to the previous adrress and next
 * block it can be deletes from the free list
 */
static void delete_free_block(void *bp)
{
    void * prev_adr = GET_PREV_BLK(bp);
    void * next_adr = GET_NEXT_BLK(bp);
    size_t size = GET_BLK_SIZE(bp);
    void *list_ptr = NULL;
    
    int index;
    /* find the offset(=index) of the seg list */
    for(index = 0; index < SEG_LIST-1; index++) {
       /* overflow occured -> found the index of seglist */
       if(size <= 1)
          break;
        
       size >>= 1;
    }
    
    /* removing the block depends on the next_adr and prev_adr */
    if(next_adr == NULL) {
        SET_SEG_LIST_PTR(seg_listp, index, prev_adr);
        list_ptr = GET_SEG_LIST_PTR(seg_listp, index);
        /* !next_adr && list_ptr */
        if(list_ptr != NULL)
            PUT(GET_NEXT_ADR(list_ptr), UNSIGN(NULL));
        
    } else {
        PUT(GET_PREV_ADR(next_adr), UNSIGN(prev_adr));
        /* next_adr && prev_adr */
        if(prev_adr != NULL)
            PUT(GET_NEXT_ADR(prev_adr), UNSIGN(next_adr));
    }

    return;
}

/*
 * mm_check - check all the cases for the consistency
 * 1) Checking if the allocated block is in free list
 * 2) Checking if the free block address is invalid
 * 3) Checking for the header and footer consistency(size && alloc)
 * 4) Checking for the alignment rule - 8 bytes
 * 5) Checking if the blocks are appropriately coalesced
 */
static int mm_check(void) {
    int errnum = 0;
    void * blkp = NULL;
    void * nblkp = NULL;
    
    /* segregated free list valid checking */
    for(int i = 0; i<SEG_LIST; i++) {
        blkp = GET_SEG_LIST_PTR(seg_listp, i);

        while(blkp != NULL) {
            /* Checking if the allocated block is in free list */
            if(GET_ALLOC(blkp)) {
                printf("FREE BLOCK %p MARKED ALLOC\n", blkp);
                errnum = -1;
            }
            /* Checking if the free block address is invalid */
            if(blkp < mem_heap_lo() || blkp > mem_heap_hi()) {
                printf("FREE BLOCK %p INVALID\n", blkp);
                errnum = -1;
            }
            /* Checking for the header and footer consistency */
            if(GET_SIZE(HDRP(blkp)) != GET_SIZE(FTRP(blkp))) {
                printf("FREE BLOCK %p HEADER AND FOOTER HAS DIFFERENT SIZE\n", blkp);
                errnum = -1;
            }
            /* Checking for the alignment rule - 8 bytes */
            if(UNSIGN(blkp) % DSIZE != 0) {
                printf("FREE BLOCK %p SHOULD BE 8 BYTE ALIGNED\n", blkp);
                errnum = -1;
            }
            nblkp = GET_PREV_BLK(blkp);
            
            /* Checking if the free blocks are appropriately coalesced */
            if(nblkp != NULL && HDRP(blkp) - FTRP(blkp) == DSIZE) {
                printf("FREE BLOCK %p SHOULD BE COALESCED\n", blkp);
                errnum = -1;
            }
            blkp = nblkp;
        }
    }
    void *list_ptr = NULL;
    list_ptr = heap_listp;

    /* heap valid checking */
    while(list_ptr != NULL && GET_SIZE(HDRP(list_ptr)) != 0) {
       /* Checking for the header and footer consistency */
       if(GET_ALLOC(HDRP(list_ptr)) != GET_ALLOC(FTRP(list_ptr))) {
           printf("BLOCK %p HEADER AND FOOTER HAS DIFFERENT ALLOCATION BIT\n", blkp);
           errnum = -1;
       }
       /* Checking for the header and footer consistency */
       if(GET_SIZE(HDRP(list_ptr)) != GET_SIZE(FTRP(list_ptr))) {
           printf("BLOCK %p HEADER AND FOOTER HAS DIFFERENT SIZE\n", blkp);
           errnum = -1;
       }
       /* Checking if the block address is invalid */
       if(list_ptr < mem_heap_lo() || list_ptr > mem_heap_hi()) {
           printf("BLOCK %p INVALID\n", list_ptr);
       }
       
       list_ptr = NEXT_BLKP(list_ptr);
    }
    
    return errnum;

}
