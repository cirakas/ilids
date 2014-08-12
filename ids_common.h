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
#include <mysql.h>
#include <pthread.h>


#define TRUE 1
#define FALSE 0
#define FDMAX 1024
#define MAXSIZE 1024
#define MSG_SIZE 256


typedef unsigned char BYTE;
typedef unsigned short WORD;

char msg_to_log[MSG_SIZE];
int clientfd;
struct sockaddr_in server_addr;
socklen_t servlen;
fd_set socket_set,temp_set;
BYTE temp_buf[MAXSIZE];
int bufindex;
char cmd_str[256];
int log_mode;
int fwritefd,fverify;
char * paddr;
char * saddr;
int dev_id[32];
int no_of_devices;


extern void open_log();
extern void log_to_file(char * log_msg,int log_count);
extern void init_slave_params();
