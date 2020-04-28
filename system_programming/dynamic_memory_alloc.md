## Virtual Memory
#### 5th-week lecture
### dynamic memory allocation 
 ----------------------

+ Explicit allocators : 'malloc' package
+ Implicit allocators(garbage collector) : 'garbage collection'      
-> throughput과 utilization의 관계 중요하다.

1. malloc and free functions (heap -> run time)    
    heap top <- brk    
    heap grows toward upward(higher address)  

    malloc : <strong> mnap </strong> and <strong> munmap </strong>  or sbrk function    
    free :  freeing ptr


### Fragmentation
 ----------------------
#### poor heap utilization    

+ Internal Fragmentation   
   allocated block이 payload보다 클 때. 

+ External Fragmentation    
  조각 조각 memory 부분을 합치면 요청된 memory size를 충족시킬 수 있지만 연속되어 저장할 수 있는 공간이 부족할 때,    


#### Implicit Free Lists 
 ----------------------
 Header     
 Block Size     
 Payload      
 Padding(optional)

 장점: 간단하다
 단점: O(n) time complexity

 + first fit: 처음부터 search -> 
 + next fit: previous search
 + best fit: 모든 free block 


#### False Fragment



#### Coalescing 