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

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <dirent.h>

#define FDMAX 1024
#define MAXCLIENTS 12
#define MAXSIZE 1024

typedef struct
{
  int sockfd;
  struct sockaddr_in client_addr;
  char name[1024];
  BYTE inbuf[MAXSIZE];
  int count;
  int send;
}CLIENT_DETAILS;

void * nwcom();
void shutdown_nw();


int no_of_clients=MAXCLIENTS;
CLIENT_DETAILS dclients[MAXCLIENTS];
int server_socket=-1,client_socket=-1;
struct sockaddr_in server_addr,client_addr;
struct hostent * client_info;

socklen_t client_len=-1;
fd_set socket_set,temp_set;
struct timeval timeout;
int result=-1,retn=-1,i=-1;
int nw_timeout=0;
char *s_name[]={"sreejith-Lenovo-Z50-70","cirakas.local","cirakas-2","videocon","","","","","","","",""};


/**@brief  This function initializes the Server Side of the Network Communication.

           Function: Initiate_Server_Socket

           Purpose:  Creates,Binds and Make the Server Socket wait for Client
                     Connections.

           Returns:  None
*/

int Initiate_Server_Socket(unsigned short int port,char * ip)
{
    int tstat=1;

	if((server_socket=socket(PF_INET,SOCK_STREAM,IPPROTO_TCP))==-1)
    {
     	perror("\nSocket Creation Error");
		sprintf(msg_to_log,"Server Socket Creation Error");
   		log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
     	return FALSE;
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
			server_addr.sin_addr.s_addr=inet_addr(ip);
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
	return TRUE;
}

/**@brief This function sets up the Client Structures and Calls the function Initiate_Server_Socket

           Function: start_server

           Purpose:  Setup the Client Structures and Calls the Server Socket
                     Create Function

           Returns:  None
*/

void start_server()
{

    for(i=0;i<no_of_clients;i++)
    {
        if(dclients[i].sockfd != -1)
        {
            FD_CLR(dclients[i].sockfd,&socket_set);
            close(dclients[i].sockfd);
        }
    	dclients[i].sockfd=-1;
    	memset(&dclients[i].client_addr,0x0,sizeof(dclients[i].client_addr));
    	memset(&dclients[i].inbuf,0x0,MAXSIZE);
        strncpy(dclients[i].name,s_name[i],strlen(s_name[i]));
    	dclients[i].count=0;
    	dclients[i].send=FALSE;
    }

    if(server_socket!=-1)
    {
        FD_CLR(server_socket,&socket_set);
        close(server_socket);
    }

    server_socket=-1;


	if(!Initiate_Server_Socket(SERVER_PORT,NULL))//A null ip means,it should be bind  to INADDR_ANY.Here "" doesnot work as NULL
	{
		printf("\nCannot start Server : %s\n",strerror(errno));
		sprintf(msg_to_log,"Cannot start Server : %s",strerror(errno));
 		log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
 		server_socket=-1;
	}

  	FD_ZERO(&socket_set);
  	FD_SET(server_socket,&socket_set);

}

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

    pthread_setcancelstate (PTHREAD_CANCEL_ENABLE,NULL);
	pthread_setcanceltype (PTHREAD_CANCEL_ASYNCHRONOUS,NULL);

    atexit(shutdown_nw);

    for(i=0;i<no_of_clients;i++)
    {
    	dclients[i].sockfd=-1;
    	memset(&dclients[i].client_addr,0x0,sizeof(dclients[i].client_addr));
    	memset(&dclients[i].inbuf,0x0,MAXSIZE);
        strncpy(dclients[i].name,s_name[i],strlen(s_name[i]));
    	dclients[i].count=0;
    	dclients[i].send=FALSE;
    }
    server_socket=-1;
    start_server();


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
                    nw_timeout=0;
                    continue;
                }

            }
      		else if(result == 0)
            {
                nw_timeout++;
                if(nw_timeout > NW_RESET_TIME)
                {
                    sprintf(msg_to_log,"NETWORK TIMEOUT,RESETTING");
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                    nw_timeout=0;
                }
                continue;
            }
            else
            {
                nw_timeout=0;
                if(FD_ISSET(server_socket,&temp_set))
	    		{
	    			client_len=sizeof(client_addr);
	    			if((client_socket=accept(server_socket,(struct  sockaddr  *)&client_addr,(socklen_t *)&client_len))==-1)
                    {
                        sprintf(msg_to_log,"Accept Error : %s",strerror(errno));
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
                        continue;
                    }
	    			else
                    {
                        if((client_info=gethostbyaddr((const char *)&(client_addr.sin_addr.s_addr),4,AF_INET))!=NULL)
		    			{
		      				for(i=0;i<no_of_clients;i++)
                            {
                                if(strncasecmp(dclients[i].name,client_info->h_name,strlen(client_info->h_name))==0)
			    				{
			      					if(dclients[i].sockfd != -1)
                                    {
                                        //printf("\nClient %s is Disconnected\n",dclients[i].name);
                                        sprintf(msg_to_log,"Client %s is Disconnected",dclients[i].name);
                                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                        FD_CLR(dclients[i].sockfd,&socket_set);
                                        close(dclients[i].sockfd);
                                        dclients[i].send=FALSE;
                                    }

		      						dclients[i].sockfd=client_socket;
		      						memset(&dclients[i].client_addr,0x0,sizeof(client_addr));
		      						memcpy(&dclients[i].client_addr,&client_addr,sizeof(client_addr));
		      						FD_SET(dclients[i].sockfd,&socket_set);
		      						printf("\nClient %s is Accepted\n",client_info->h_name);
                                    sprintf(msg_to_log,"Client %s is Accepted",client_info->h_name);
                                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
		      						break;
		    					}
                            }
                            if(i==no_of_clients)
                            {
                                //printf("\nClient %s is not allowed to Connect\n",client_info->h_name);
                                sprintf(msg_to_log,"Client %s is not allowed to Connect",client_info->h_name);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
		      					close(client_socket);
                            }

		    			}
                        else
		    			{
		      				printf("\nClient %s Not found in host database,Disconnected\n",(const char *)inet_ntoa(client_addr.sin_addr));
                            sprintf(msg_to_log,"Client %s Not found in host database,Disconnected",(const char *)inet_ntoa(client_addr.sin_addr));
                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
		      				close(client_socket);
		    			}

                    }
	    		}

                for(i=0;i<no_of_clients;i++)
	    		{
	      			if(dclients[i].sockfd != -1)
                    {
                        if(FD_ISSET(dclients[i].sockfd,&temp_set))
		    			{
		      				if((retn=read(dclients[i].sockfd,dclients[i].inbuf,MAXSIZE)) > 0)
                            {
                                //Process Client Data Here
                                //printf("\nRead %d bytes from %s\n",retn,dclients[i].name);
                                sprintf(msg_to_log,"Read %d bytes from %s",retn,dclients[i].name);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
		      				else
                            {
                                //printf("\nClient %s is Disconnected : %s\n",dclients[i].name,strerror(errno));
                                sprintf(msg_to_log,"Client %s is Disconnected : %s",dclients[i].name,strerror(errno));
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                                FD_CLR(dclients[i].sockfd,&socket_set);
                                close(dclients[i].sockfd);
                                dclients[i].sockfd=-1;
                                dclients[i].send=FALSE;
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

