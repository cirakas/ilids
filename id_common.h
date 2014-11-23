/*
 * id_common.h
 *
 * This is the common header file for idriver module in ilids
 * project.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */



#include <stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <termios.h>
#include <string.h>
#include <sys/time.h>
#include <time.h>
#include <pthread.h>
#include <signal.h>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <sys/select.h>
#include <errno.h>
#include <mysql.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <dirent.h>



#define UINT32 unsigned int
#define BYTE unsigned char
#define WORD unsigned short

#define TRUE 1
#define FALSE 0

#define BAUDRATE B9600
#define BUF_SIZE 1024
#define READ_BUF_SIZE 512
#define WRITE_BUF_SIZE 512
#define WRITE_TIMEOUT 1000

#define SERVER_PORT 1388
#define SERVER_IP "10.0.1.29"
#define SERVER_LOCAL_IP "127.0.0.1"
#define NW_RESET_TIME 120


#define POLL_INTERVAL 500


#define RTIMEOUT 100
#define DEFAULT_SLID 17
#define DEFAULT_PORT "/dev/ttyUSB0"

#define POLL_PKT_SIZE 6
#define POLL_PKT 0x3

#define MAXSLAVE 32

#define MSG_SIZE 256

#define MAXCMD 9

#define MAX_REG 90

#define MAXPARAMS_A 24
#define MAXPARAMS_B 40
#define MAXPARAMS_C 15

#define addr_MAXPARAMS_A 0
#define addr_MAXPARAMS_B 512
#define addr_MAXPARAMS_C 1280

#define nwrds_MAXPARAMS_A 48
#define nwrds_MAXPARAMS_B 80
#define nwrds_MAXPARAMS_C 30

#define QUERRY_MAXSIZE 256
#define F_PRECISION 0.1
#define RANGEVAL 20

#define Read_Holding_Registers 0x3
#define Preset_Single_Register 0x6
#define Preset_Multiple_Registers 0x10

#define ECLOSE_WAIT 1000

#define FDMAX 1024
#define MAXSIZE 1024
#define MAXCLIENTS 12


extern void open_log();
extern void log_to_file(char * log_msg,int log_count,int log_level);
extern void da_wait(int nsecs,int nmsecs);

extern void  writecom(BYTE * msg, int ncount);

extern int db_start();
extern void db_close();

extern WORD Add_CRC(BYTE buf[], int len);
extern int Check_CRC(BYTE * pktdata, int pktcount);
extern BYTE LoByte(WORD val);
extern BYTE HiByte(WORD val);
extern WORD LoWord(unsigned int val);
extern WORD HiWord(unsigned int val);
extern void ShowBits(int y,int no_of_bytes);

extern void switch_params(BYTE * pktdata, int pktcount);
extern void Process_ParamsA(BYTE * pktdata, int pktcount);
extern void Process_ParamsB(BYTE * pktdata, int pktcount);
extern void Process_ParamsC(BYTE * pktdata, int pktcount);

extern void Send_Data_To_Clients(BYTE * msg, int ncount);
extern void shutdown_nw();

extern void intitialize_poll_packet();
extern void send_pkt(int pkt_type);

extern void Handle_exithandler();

extern void reverse_b(BYTE *t_addr,BYTE *s_addr,int bcount);

extern void init_slave_params();

extern int Read_Conf();

extern BYTE gl_buf[BUF_SIZE];
extern MYSQL *conn;

extern char querry_msg[256];

typedef struct
{
    WORD addr_off;
    char * pname;
    WORD nwords;
    float mf;
    float val;
    float offset;
}PARAM_DETAILS;

typedef struct
{
    int active;
    int chk_count;
    int reset_chk_count;
    float param_valueA[MAXPARAMS_A];
    float param_valueB[MAXPARAMS_B];
    float param_valueC[MAXPARAMS_C];
}SLAVE_STAT_LIST;

WORD scondition;
SLAVE_STAT_LIST vlist[MAXSLAVE];
int db_id1,db_id2,db_id3;

typedef struct
{
  int sockfd;
  struct sockaddr_in client_addr;
  char name[1024];
  BYTE inbuf[MAXSIZE];
  int count;
  int send;
}CLIENT_DETAILS;


typedef struct
{
    BYTE *ppkt[3];
}S_DEVICE_LIST;


typedef union
{
    WORD wval;
    BYTE bval[2];

}NVALUE;


S_DEVICE_LIST sdev[MAXSLAVE];

int p_int;
int rd_timeout;
int slave_id;
char *cport;
int random_mode;
int emulator_mode;
int rand_time;
int rand_count;
int current_log_level;

#define DEBUG_LEVEL_DEFAULT 0
#define DEBUG_LEVEL_1 1
#define DEBUG_LEVEL_2 2
#define DEBUG_LEVEL_3 3

volatile int ex_term;
char scommand[256];


#define MAX_CMDS 100

typedef struct
{
  char ename[20];
  int elist[60];
  int no_of_elem;
}HWCONF;

typedef struct
{
  char param_name[1024];
  int devid;
  int start_addr;
  int no_of_reg;
  int value_offset;
  float param_value;
}CMDCONF;


HWCONF dev_config[10];
CMDCONF cmd_config[MAX_CMDS];

int no_of_cmds;

int no_of_clients;
CLIENT_DETAILS dclients[MAXCLIENTS];
fd_set socket_set,temp_set;
BYTE gl_buf[BUF_SIZE];
int gl_count;
int bytes_read;
struct sockaddr_in server_addr,client_addr;

char msg_to_log[256];
