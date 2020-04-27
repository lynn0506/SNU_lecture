#include <stdlib.h>
#include <stdio.h> 
#define UNW_LOCAL_ONLY
#include <libunwind.h> 
#include <string.h> 


int get_callinfo(char *fname, size_t fnlen, unsigned long long *ofs)
{ 
  unw_context_t context;
  unw_cursor_t cursor;
  unw_word_t off;
  unw_proc_info_t pip;
  char procname[256];
  int ret;

  if(unw_getcontext(&context))
    return -1;

  if(unw_init_local(&cursor, &context))
    return -1;
    
  while(unw_step(&cursor) > 0) {
    if(unw_get_proc_info(&cursor, &pip))
       return -1;

    ret = unw_get_proc_name(&cursor, procname, 256, &off); 
    if(ret && ret != -UNW_ENOMEM) {
       procname[0] = '?';
       procname[1] = 0;
    }
   
    if(strcmp(procname,  "main") == 0) 
      break;
  }

  if(strcmp(procname, "main") != 0) 
    return -1;

  *ofs = (unsigned long int) (off -5);
  strcpy(fname, procname);
  return 0;
}


