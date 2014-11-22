#include "ids_common.h"


#define ADDR_0 0

extern void process_master_data(BYTE *inbuf,int inlen);



int GetServerInfo()
{
  server_addr.sin_family=AF_INET;
  server_addr.sin_port=htons(strtol(paddr,NULL,0));
  server_addr.sin_addr.s_addr=inet_addr(saddr);
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

void get_slave_idys(char *i_list,int list_len)
{
    int i=0,j=0;
    char *a,*b;
    int dup_id=0;

    //dev_id=atoi(i_list);
    //printf("\nDevice Id is %d , len is %d\n",dev_id,list_len);
    b=i_list;
    while( b==i_list || *a==',' || *a==' ')
    {
        dev_id[i]=strtol(b,&a,10);

        dup_id=FALSE;
        for(j=0;j<no_of_devices;j++)
        {
            if(dev_id[i]==dev_id[j])
            {
                printf("\nDuplicate Slave Address,Ignoring...\n");
                dup_id=TRUE;
                break;
            }
        }


        if(dev_id[i]!=ADDR_0 && dup_id == FALSE)
        {
            i++;
        }
        no_of_devices=i;
        b=a+1;
        if(i>31)
        {
            return;
        }
    }
}

int main(int argc,char * argv[])
{
  struct timeval timeout;
  int result=-1,retn=0,i=0,j=0,k=0,rcount=0;


        servlen=0;
        bufindex=0;
        log_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
        fwritefd=-1;
        fverify=-1;
        paddr="1388";//default server port
        saddr="127.0.0.1";//default server address
        no_of_devices=0;

        for(i=1;i<argc;i++)
        {
            if(argv[i][j]=='-')
            {
                switch(argv[i][j+1])
                {
                    case 's':
                    saddr=(char *)&argv[i][j+3];

                    break;

                    case 'p':
                    paddr=(char *)&argv[i][j+3];

                    break;

                    case 'd':
                    get_slave_idys(((char *)&argv[i][j+3]),strlen(((char *)&argv[i][j+3])));//store slaveids in dev_id[] list
                    break;

                    default:
                    printf("\nInvalid Arguments,Using Default Values\n");
                    break;
                }
            }
        }




    if(no_of_devices==0)
    {
        printf("\nNo Device Addresses Provided for Emulation,Exiting...\n");
        exit(0);
    }

    open_log();
    sprintf(msg_to_log,"Device Emulation Started");
    log_to_file(msg_to_log,strlen(msg_to_log));
    sprintf(msg_to_log,"Server Address is %s",saddr);
    log_to_file(msg_to_log,strlen(msg_to_log));
    sprintf(msg_to_log,"Port Address is %s",paddr);
    log_to_file(msg_to_log,strlen(msg_to_log));

    printf("\nServer Address is %s\n",saddr);
    printf("\nPort Address is %s\n",paddr);
    j=0;
    j+=sprintf(&msg_to_log[j],"Device Addresses to be Emulated are ");
    printf("\nDevice Addresses to be Emulated are  ");

    for(i=0;i<no_of_devices;i++)
    {
        j+=sprintf(&msg_to_log[j]," %X",dev_id[i]);
        printf("%X ",dev_id[i]);
    }
    log_to_file(msg_to_log,j);
    printf("\n");


    //below line is commented,because initial values for params are set while structure is declared
    //init_slave_params();//set initial values for all params from kims database



        clientfd=-1;
        GetServerInfo();
        servlen=sizeof(server_addr);
        MakeClientSocket();
        while(((connect(clientfd,( const struct sockaddr *)&server_addr,servlen))==-1))
        {
            sleep(1);
        }

        sprintf(msg_to_log,"Connected to Server");
        log_to_file(msg_to_log,strlen(msg_to_log));

        printf("\nConnected to Server\n");

        fcntl(clientfd, F_SETFL, fcntl(clientfd, F_GETFL, 0) | O_NONBLOCK);


        while(1)
        {
            timeout.tv_sec=1;
            timeout.tv_usec=0;
            FD_ZERO(&socket_set);
            FD_SET(clientfd,&socket_set);
            temp_set=socket_set;

            result=select(clientfd+1,&temp_set,NULL,NULL,&timeout);
            if(result==0)
            {
                continue;
            }

            if(result<0)
            {
                if(errno == EINTR)
                {
                    continue;
                }
                perror("Select Error");
                FD_CLR(clientfd,&socket_set);
                close(clientfd);
                clientfd=-1;
                exit(EXIT_FAILURE);
            }

            if(result > 0)
            {
                if(FD_ISSET(clientfd,&temp_set))
                {
                    memset(temp_buf,0x0,MAXSIZE);
                    if((retn=read(clientfd,temp_buf,MAXSIZE)) <= 0)
                    {
                        if(errno == EAGAIN || errno == EWOULDBLOCK || errno == EINTR)
                        {
                            continue;
                        }

                        perror("Read");
                        printf("\nClient closing connection\n");
                        FD_CLR(clientfd,&socket_set);
                        close(clientfd);
                        clientfd=-1;
                        exit(EXIT_FAILURE);
                    }
                    else
                    {
                        //printf("\nREAD DATA\n");
                        rcount=sprintf(msg_to_log,"READ DATA ");
                        for(k=0;k<retn;k++)
                        {
                            rcount += sprintf(&msg_to_log[rcount]," %02X",temp_buf[k]);
                        }
                        rcount += sprintf(&msg_to_log[rcount]," FROM MASTER");
                        log_to_file(msg_to_log,rcount);
                        process_master_data(temp_buf,retn);
                    }
                }
            }
        }

}
