#include <stdio.h>
#include <time.h>

#include "clib/clib.h"

DLLEXPORT char *get_time() {
  time_t ltime;
  time(&ltime);
  return ctime(&ltime);
}

DLLEXPORT void say_hello(char *message) {
  printf("Hello from dll!\n%s", message);
}
