#include<stdio.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/stat.h>
#include <arpa/inet.h>
#include <pthread.h>


#define TRUE 1
#define FALSE 0
#define FDMAX 1024
#define MAXSIZE 1024
#define MSG_SIZE 256


typedef unsigned char BYTE;

char msg_to_log[MSG_SIZE];

extern void open_log();
extern void log_to_file(char * log_msg,int log_count);
