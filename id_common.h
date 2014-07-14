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


#define POLL_INTERVAL 500//Here 500ms is given to give read port method,  sufficient time to read all response before sending the next poll.
//240//300 milliseconds.Should be > 240ms(Page-30 in manual) Might be increased upto < 1 second

#define RTIMEOUT 100
#define DEFAULT_SLID 17//hex 11
#define DEFAULT_PORT "/dev/ttyUSB0"

#define POLL_PKT_SIZE 6
#define POLL_PKT 0x3

#define MAXSLAVE 255

#define MSG_SIZE 256

#define MAXCMD 9

#define MAX_REG 90

#define MAXPARAMS_A 24
#define MAXPARAMS_B 40
#define MAXPARAMS_C 30

#define addr_MAXPARAMS_A 0
#define addr_MAXPARAMS_B 512
#define addr_MAXPARAMS_C 1280

#define nwrds_MAXPARAMS_A 48
#define nwrds_MAXPARAMS_B 80
#define nwrds_MAXPARAMS_C 72

#define QUERRY_MAXSIZE 256
#define F_PRECISION 0.1
#define RANGEVAL 10

#define Read_Holding_Registers 0x3
#define Preset_Single_Register 0x6
#define Preset_Multiple_Registers 0x10

#define ECLOSE_WAIT 1000




extern void open_log();
extern void log_to_file(char * log_msg,int log_count);
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
extern void ShowBits(BYTE y,int no_of_bytes);

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

extern char msg_to_log[256];
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
}PARAM_DETAILS;

WORD scondition;
int db_id1,db_id2,db_id3;


typedef struct
{
    BYTE dev_id;
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
int rand_time;
int rand_count;

volatile int ex_term;