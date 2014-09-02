/*
 * id_net.c
 *
 * This is the source file where the network
 * communication functions for idriver are implemented
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"

void * nwcom();
void shutdown_nw();
int check_existing(struct hostent * client_params);


extern int no_of_clients;
int server_socket=-1,client_socket=-1;
struct hostent * client_info;

socklen_t client_len=-1;
extern fd_set socket_set,temp_set;
struct timeval timeout;
int result=-1,retn=-1,i=-1;
int nw_timeout=0;
char *s_name[]={"sreejith-Lenovo-Z50-70","cirakas.local","cirakas-2","videocon","","","","","","","",""};


/**@brief This function shuts down the Client-Server Communication

           Function: shutdown_nw

           Purpose:  Shuts Down the Client-Server Communication

           Returns:  None
*/

void shutdown_nw()
{
    int i=0;

    for(i=0;i<no_of_clients;i++)
    {
        if(dclients[i].sockfd!=-1)
        {
                 FD_CLR(dclients[i].sockfd,&socket_set);
                 close(dclients[i].sockfd);
                 dclients[i].sockfd=-1;
                 dclients[i].send=FALSE;
        }
    }

    FD_CLR(server_socket,&socket_set);
    close(server_socket);
}

int check_existing(struct hostent * client_params)
{
    int i=0;

    for(i=0;i<no_of_clients;i++)
    {
    	if(strcmp(dclients[i].name,client_params->h_name)==0)
    	{
    	    return TRUE;
    	}
    }

    return FALSE;
}

/**@brief  This function implements the network communication thread.

           Function: nwcom

           Purpose:  Accept Client Connections and reads data from the
                     connected clients.This is implemented as a seperate
                     thread.

           Returns:  None
*/

void * nwcom()
{
    int i=0;
    char *ip=NULL;
    int tstat=1;
    unsigned short int port=SERVER_PORT;
    int bytes_read_nw=0;

    pthread_setcancelstate (PTHREAD_CANCEL_ENABLE,NULL);
	pthread_setcanceltype (PTHREAD_CANCEL_ASYNCHRONOUS,NULL);
    atexit(shutdown_nw);


    if((server_socket=socket(PF_INET,SOCK_STREAM,IPPROTO_TCP))==-1)
    {
     	perror("\nSocket Creation Error");
		sprintf(msg_to_log,"Server Socket Creation Error");
   		log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
     	return NULL;
    }
	else
    {
        setsockopt(server_socket,SOL_SOCKET,SO_REUSEADDR,&tstat,sizeof(int));
		server_addr.sin_family=AF_INET;
		server_addr.sin_port=htons(port);
		if(ip==NULL)
		{
			server_addr.sin_addr.s_addr=INADDR_ANY;
		}
		else
		{
			server_addr.sin_addr.s_addr=inet_addr(SERVER_IP);
		}

		while(bind(server_socket,(struct sockaddr *)&server_addr,sizeof(server_addr))==-1)
		{
			if(errno != EADDRINUSE)
			{
				sprintf(msg_to_log,"Bind Error %s",strerror(errno));
   				log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
				perror("\nBind Error ");
				close(server_socket);
				return FALSE;
			}
			da_wait(1,0);
		}

		if(listen(server_socket,10)==-1)
		{
			perror("\nListen Error ");
			close(server_socket);
			sprintf(msg_to_log,"Server Listen Error");
   			log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
			return FALSE;
		}
    }

    for(i=0;i<no_of_clients;i++)
    {
    	dclients[i].sockfd=-1;
    	memset(&dclients[i].client_addr,0x0,sizeof(dclients[i].client_addr));
    	memset(&dclients[i].inbuf,0x0,MAXSIZE);
    	memset(&dclients[i].name,0x0,MAXSIZE);
    	dclients[i].count=0;
    	dclients[i].send=FALSE;
    }

    FD_ZERO(&socket_set);
  	FD_SET(server_socket,&socket_set);


	//while(1)
	while(!ex_term)
    {
    		timeout.tv_sec=1;
    		timeout.tv_usec=0;
    		temp_set=socket_set;
      		if((result=select(FDMAX,&temp_set,NULL,NULL,&timeout))<0)
            {
                if(errno == EINTR)
                {
                    continue;
                }
                else
                {
                    sprintf(msg_to_log,"NW Select Error %s",strerror(errno));
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                    continue;
                }

            }
      		else if(result == 0)
            {
                continue;
            }
            else
            {

                    if(FD_ISSET(server_socket,&temp_set))
                    {
                        client_len=sizeof(client_addr);
                        if((client_socket=accept(server_socket,(struct  sockaddr  *)&client_addr,(socklen_t *)&client_len))==-1)
                        {
                            sprintf(msg_to_log,"Accept Error : %s",strerror(errno));
                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
                            continue;
                        }
                        else if((client_info=gethostbyaddr((const char *)&(client_addr.sin_addr.s_addr),4,AF_INET))==NULL)
                        {

                                printf("\nClient %s Not found in host database,Disconnected\n",(const char *)inet_ntoa(client_addr.sin_addr));
                                sprintf(msg_to_log,"Client %s Not found in host database,Disconnected",(const char *)inet_ntoa(client_addr.sin_addr));
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                close(client_socket);
                        }
                        else if(check_existing(client_info))
                        {
                                printf("\nClient %s Already Connected\n",client_info->h_name);
                                sprintf(msg_to_log,"Client %s Already Connected",client_info->h_name);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                close(client_socket);
                        }
                        else
                        {

                                for(i=0;i<no_of_clients;i++)
                                {
                                        if(dclients[i].sockfd == -1)
                                        {
                                            dclients[i].sockfd=client_socket;
                                            memset(&dclients[i].client_addr,0x0,sizeof(client_addr));
                                            memset(&dclients[i].inbuf,0x0,MAXSIZE);
                                            memset(&dclients[i].name,0x0,MAXSIZE);
                                            memcpy(&dclients[i].client_addr,&client_addr,sizeof(client_addr));
                                            memcpy(&dclients[i].name,client_info->h_name,strlen(client_info->h_name));
                                            FD_SET(dclients[i].sockfd,&socket_set);
                                            printf("\nClient %s is Accepted\n",dclients[i].name);
                                            sprintf(msg_to_log,"Client %s is Accepted",dclients[i].name);
                                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                            break;
                                        }

                                }
                        }
                    }
                    else
                    {
                        for(i=0;i<no_of_clients;i++)
                        {
                            if(dclients[i].sockfd != -1)
                            {
                                if(FD_ISSET(dclients[i].sockfd,&temp_set))
                                {
                                    bytes_read_nw=read(dclients[i].sockfd,dclients[i].inbuf,MAXSIZE);

                                    if(bytes_read_nw > 0)
                                    {
                                        memcpy(&gl_buf[gl_count],(void *)&dclients[i].inbuf[0],bytes_read_nw);
                                        gl_count+=bytes_read_nw;
                                        sprintf(msg_to_log,"Read %d Bytes from NW Device",bytes_read_nw);
                                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                    }
                                    else
                                    {
                                        printf("\nClient %s is Disconnected : %s\n",dclients[i].name,strerror(errno));
                                        sprintf(msg_to_log,"Client %s is Disconnected : %s",dclients[i].name,strerror(errno));
                                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                                        FD_CLR(dclients[i].sockfd,&socket_set);
                                        close(dclients[i].sockfd);
                                        memset(&dclients[i].client_addr,0x0,sizeof(client_addr));
                                        memset(&dclients[i].inbuf,0x0,MAXSIZE);
                                        memset(&dclients[i].name,0x0,MAXSIZE);
                                        dclients[i].sockfd=-1;
                                        dclients[i].send=FALSE;
                                    }
                                    continue;

                                }

                            }

                        }

                    }

            }
        }
        return FALSE;

}

/**@brief  This function sends data to the Clients

           Function: Send_Data_To_Clients

           Purpose:  Sends Data to the connected Clients.

           Returns:  None
*/

void Send_Data_To_Clients(BYTE * msg, int ncount)
{
int i=0,j=0;
int retn=0;
int rcount=0;

	for(i=0;i<no_of_clients;i++)
  	{
	    	if(dclients[i].sockfd != -1)
    		{
      			if((retn=write(dclients[i].sockfd,msg,ncount)) != ncount)
                {
                    FD_CLR(dclients[i].sockfd,&socket_set);
                    close(dclients[i].sockfd);
                    sprintf(msg_to_log,"Write Error : Client %s is Disconnected : %s",dclients[i].name,strerror(errno));
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                    dclients[i].sockfd=-1;
                    dclients[i].send=FALSE;
                }
                else
                {
                    rcount=sprintf(msg_to_log,"WRITTEN NWDATA ");
                    for(j=0;j<ncount;j++)
                    {
                        rcount+=sprintf(&msg_to_log[rcount]," %02X",msg[j]);
                    }
                    rcount=rcount+sprintf(&msg_to_log[rcount]," To Client %s",dclients[i].name);
                    log_to_file(msg_to_log,rcount,DEBUG_LEVEL_3);

                }
            }

	}
}

