//------------------------------------------------------------------------------
//
// memtrace
//
// trace calls to the dynamic memory manager
//
#define _GNU_SOURCE

#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <memlog.h>
#include <memlist.h>
#include "callinfo.h"

//
// function pointers to stdlib's memory management functions
//
static void *(*mallocp)(size_t size) = NULL;
static void (*freep)(void *ptr) = NULL;
static void *(*callocp)(size_t nmemb, size_t size);
static void *(*reallocp)(void *ptr, size_t size);

//
// statistics & other global variables
//
static unsigned long n_malloc  = 0;
static unsigned long n_calloc  = 0;
static unsigned long n_realloc = 0;
static unsigned long n_allocb  = 0;
static unsigned long n_freeb   = 0;
static item *list = NULL;



void *malloc(size_t size) 
{
    char* error;
    void *ptr;
    
    if(!mallocp) {
       mallocp = dlsym(RTLD_NEXT, "malloc");
       if((error = dlerror()) != NULL) {     
	  fputs(error, stderr);
          exit(1);
       }
    }
    ptr = mallocp(size);
    n_malloc += 1;
    n_allocb += size;
    alloc(list, ptr, size);
    LOG_MALLOC(size, ptr);
    return ptr;
}

void free(void *ptr) {
   char* error;
   LOG_FREE(ptr);
   
   if(!freep) {
     freep = dlsym(RTLD_NEXT, "free");
     if ((error = dlerror()) != NULL) { 
       fputs(error, stderr);
       exit(1);
     } 
   }

   if(ptr == NULL || find(list, ptr) == NULL) {
     LOG_ILL_FREE();
   } else if(find(list,ptr)->cnt <=0) {
     LOG_DOUBLE_FREE();
   } else {
     n_freeb += find(list, ptr)->size;
     dealloc(list, ptr); 
     freep(ptr);
   }
}

void *calloc(size_t nmemb, size_t size) 
{
    char* error;
    void *ptr;
   
    if(!callocp) {
       callocp = dlsym(RTLD_NEXT, "calloc");
       if((error = dlerror()) != NULL) {
          fputs(error, stderr);
          exit(1);
	}
    }
    ptr = callocp(nmemb, size);
    n_calloc += 1;
    n_allocb += size*nmemb;
    alloc(list, ptr, size*nmemb);
    LOG_CALLOC(nmemb, size, ptr);
    return ptr;
}

void *realloc(void *ptr, size_t size) {
    char *error;
    void *new_ptr = ptr;
    void *ori_ptr = ptr;

    if(!reallocp) {
      reallocp = dlsym(RTLD_NEXT, "realloc");
      if((error = dlerror()) != NULL) {
        fputs(error, stderr);
        exit(1);
      }
    }         
    n_realloc += 1;
    n_allocb += size;
    int pt_ch = 0;

    if(find(list, ptr) == NULL){   
	pt_ch = 1;
	ptr = NULL;
    }
    else if(find(list, ptr)->cnt == 0){
	pt_ch = 2;
        ptr = NULL;
    }
    else{
	n_freeb += find(list, ptr)->size;
        dealloc(list, ptr);
    }
    new_ptr = reallocp(ptr, size);
    if(size != 0)
    	alloc(list, new_ptr, size);   
    LOG_REALLOC(ori_ptr, size, new_ptr);

    if(pt_ch == 1) 
      LOG_ILL_FREE();
    else if(pt_ch == 2)
      LOG_DOUBLE_FREE();

    return new_ptr;
}
//
// init - this function is called once when the shared library is loaded
//
__attribute__((constructor))
void init(void)
{
  char* error;
   
  LOG_START();
  // initialize a new list to keep track of all memory (de-)allocations
  // (not needed for part 1)

  list = new_list();   
 
 // ...
}

//
// fini - this function is called once when the shared library is unloaded
//
__attribute__((destructor))
void fini(void)
{
  // ...
  unsigned long avg = 0;
  if((n_malloc + n_calloc + n_realloc) != 0) 
    avg = n_allocb /(unsigned long)(n_malloc+n_calloc+n_realloc);
   
  LOG_STATISTICS(n_allocb, avg, n_freeb); 
  int log_check = 0;
  
  while(list->next != NULL) {
    list = list->next;
    if(list->cnt != 0) {
       if(log_check == 0){
         LOG_NONFREED_START(); 
         log_check = 1;
       }
       LOG_BLOCK(list->ptr, list->size,
                 list->cnt, list->fname, list->ofs);
    }
  }

  LOG_STOP();

  // free list (not needed for part 1)
  free_list(list);
}

// ...
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
