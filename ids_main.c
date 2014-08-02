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


#define TRUE 1
#define FALSE 0
#define FDMAX 1024
#define MAXSIZE 1024


typedef unsigned char BYTE;

int clientfd=-1;
struct sockaddr_in server_addr;
socklen_t servlen=0;
fd_set socket_set,temp_set;
BYTE temp_buf[MAXSIZE];
int bufindex=0;
char cmd_str[256];
BYTE cmd=0x0,prev=0x0,sub_started=FALSE,client_started=FALSE;
int log_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
int fwritefd=-1,fverify=-1;

int GetServerInfo()
{
  server_addr.sin_family=AF_INET;
  server_addr.sin_port=htons(strtol("1388",NULL,0));
  server_addr.sin_addr.s_addr=inet_addr("10.0.1.18");
  return TRUE;
}

int MakeClientSocket()
{
    if((clientfd=socket(PF_INET, SOCK_STREAM, 0))==-1)
    {
      return FALSE;
    }
    return TRUE;
}

int ProcessServerData(BYTE * buffer,int count)
{
  return TRUE;
}

int main(int argc,char * argv[])
{
  struct timeval timeout;
  int result=-1,retn=0,i=0;


    printf("\nDevice Simulation Started\n");


    while(1)
    {
        sleep(1);
        if(GetServerInfo())
        {
            if(clientfd==-1)
            {
                if(MakeClientSocket())
                {
                    servlen=sizeof(server_addr);
                    if((connect(clientfd,( const struct sockaddr *)&server_addr,servlen))==-1)
                    {
                        perror("Connect Failed");
                        close(clientfd);
                        clientfd=-1;
                        continue;
                    }
                    else
                    {
                        printf("\nConnected to Server\n");
                        FD_ZERO(&socket_set);
                        FD_SET(clientfd,&socket_set);
                        while(1)
                        {
                            timeout.tv_sec=1;
                            timeout.tv_usec=0;
                            temp_set=socket_set;
                            if((result=select(FDMAX,&temp_set,NULL,NULL,&timeout))==-1)
                            {
                                perror("Select Error");
                                FD_CLR(clientfd,&socket_set);
                                close(clientfd);
                                clientfd=-1;
                                break;
                            }
                            if(result > 0)
                            {
                                if(FD_ISSET(clientfd,&temp_set))
                                {

                                    memset(temp_buf,0x0,MAXSIZE);
                                    if((retn=read(clientfd,temp_buf,MAXSIZE)) <= 0)
                                    {
                                        perror("Read");
                                        printf("\nClient closing connection\n");
                                        FD_CLR(clientfd,&socket_set);
                                        close(clientfd);
                                        clientfd=-1;
                                        break;
                                    }
                                    else
                                    {
                                        printf("\nRead Data from Server ");
                                        for(i=0;i<retn;i++)
                                        {
                                            printf("%X ",temp_buf[i]);
                                        }

                                    }

                                }


                            }

                        }
                    }
                }
                else
                {
                    return FALSE;
                }

            }

        }
    }
}
