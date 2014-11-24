/*
 * id_pkt.c
 *
 * This is the source file where the commands for
 * the slave devices are prepared and send.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"


void send_pkt(int pkt_type);
void intitialize_poll_packet();
void init_slave_params();

void init_slave_params()
{
    int i=0,j=0;


    for(i=0;i<MAXSLAVE;i++)
    {
        vlist[i].active=FALSE;
        vlist[i].chk_count=0;
        vlist[i].reset_chk_count=0;

        for(j=0;j<MAXPARAMS_A;j++)
        {
            vlist[i].param_valueA[j]=params1[j];
            vlist[i].param_valueA[j].val=0.0;//set to 0 initially instead of filling it with sample values from kims db
            /*memset(mquerry_msg,0x0,QUERRY_MAXSIZE);
            sprintf(mquerry_msg,"SELECT data  FROM data  WHERE device_id=%d and address_map=%d ORDER BY id DESC  LIMIT 1",i,k);
            if(!mysql_query(conn,mquerry_msg))
            {
                res = mysql_use_result(conn);
                if((row = mysql_fetch_row(res)) != NULL)
                {
                    vlist[i].param_valueA[j]=strtof(row[0],NULL);//atof(row[0]);
                }
                else
                {
                    vlist[i].param_valueA[j]=0.0;
                }
                mysql_free_result(res);
            }
            else
            {
                vlist[i].param_valueA[j]=0.0;

            }

            //printf("\nval is %.2f : addr is %d ",vlist[i].param_valueA[j],k);
            //k+=2;*/

        }
        for(j=0;j<MAXPARAMS_B;j++)
        {
            vlist[i].param_valueB[j]=params2[j];
            vlist[i].param_valueB[j].val=0.0;//set to 0 initially instead of filling it with sample values from kims db
            /*memset(mquerry_msg,0x0,QUERRY_MAXSIZE);
            sprintf(mquerry_msg,"SELECT data  FROM data  WHERE device_id=%d and address_map=%d ORDER BY id DESC  LIMIT 1",i,k);
            if(!mysql_query(conn,mquerry_msg))
            {
                res = mysql_use_result(conn);
                if((row = mysql_fetch_row(res)) != NULL)
                {
                    vlist[i].param_valueB[j]=strtof(row[0],NULL);//atof(row[0]);
                }
                else
                {
                    vlist[i].param_valueB[j]=0.0;
                }
                mysql_free_result(res);
            }
            else
            {
                vlist[i].param_valueB[j]=0.0;

            }
            //printf("\nval is %.2f : addr is %d ",vlist[i].param_valueB[j],k);
            k+=2;//need to check addr=addr+2 for all maxparamsB*/

        }
        for(j=0;j<MAXPARAMS_C;j++)
        {
            vlist[i].param_valueC[j]=params3[j];
            vlist[i].param_valueC[j].val=0.0;//set to 0 initially instead of filling it with sample values from kims db
            /*memset(mquerry_msg,0x0,QUERRY_MAXSIZE);
            sprintf(mquerry_msg,"SELECT data  FROM data  WHERE device_id=%d and address_map=%d ORDER BY id DESC  LIMIT 1",i,k);
            if(!mysql_query(conn,mquerry_msg))
            {
                res = mysql_use_result(conn);
                if((row = mysql_fetch_row(res)) != NULL)
                {
                    vlist[i].param_valueC[j]=strtof(row[0],NULL);//atof(row[0]);
                }
                else
                {
                    vlist[i].param_valueC[j]=0.0;
                }
                mysql_free_result(res);
            }
            else
            {
                vlist[i].param_valueC[j]=0.0;

            }
            //printf("\nval is %.2f : addr is %d ",vlist[i].param_valueC[j],k);
            k+=2;//need to check addr=addr+2 for all maxparamsC*/

        }
    }


}


/**@brief  This function Initializes the poll packets for each device to be send later.

           Function: intitialize_poll_packet

           Purpose:  Initializes the poll packets for each device to be send later.

           Returns:  None
*/


void intitialize_poll_packet()
{
    int i=0,j=0;

    for(i=0;i<MAXSLAVE;i++)//init poll packets for each slave
    {
        {
            sdev[i].ppkt[0]=(BYTE *)calloc(POLL_PKT_SIZE ,1);//allocate space 3 poll packets for each slave
            sdev[i].ppkt[1]=(BYTE *)calloc(POLL_PKT_SIZE,1);//allocate space 3 poll packets for each slave
            sdev[i].ppkt[2]=(BYTE *)calloc(POLL_PKT_SIZE,1);//allocate space 3 poll packets for each slave
            for(j=0;j<3;j++)
            {
                sdev[i].ppkt[j][0]=i+1;//set slave id for the 3 poll pkts
                sdev[i].ppkt[j][1]=Read_Holding_Registers;//set cmd for the 3 poll pkts
            }
            sdev[i].ppkt[0][2]=HiByte(addr_MAXPARAMS_A);//set start addr and no.of words in each poll pkt
            sdev[i].ppkt[0][3]=LoByte(addr_MAXPARAMS_A);
            sdev[i].ppkt[0][4]=HiByte(nwrds_MAXPARAMS_A);
            sdev[i].ppkt[0][5]=LoByte(nwrds_MAXPARAMS_A);

            sdev[i].ppkt[1][2]=HiByte(addr_MAXPARAMS_B);
            sdev[i].ppkt[1][3]=LoByte(addr_MAXPARAMS_B);
            sdev[i].ppkt[1][4]=HiByte(nwrds_MAXPARAMS_B);
            sdev[i].ppkt[1][5]=LoByte(nwrds_MAXPARAMS_B);

            sdev[i].ppkt[2][2]=HiByte(addr_MAXPARAMS_C);
            sdev[i].ppkt[2][3]=LoByte(addr_MAXPARAMS_C);
            sdev[i].ppkt[2][4]=HiByte(nwrds_MAXPARAMS_C);
            sdev[i].ppkt[2][5]=LoByte(nwrds_MAXPARAMS_C);
        }
    }

}
/**@brief  This function Sends the poll packet to each slave device

           Function: send_pkt

           Purpose:  Sends the poll packet to each slave device in turn.

           Returns:  None
*/

void send_pkt(int pkt_type)
{
static int sid=0,pktid=0;

    switch(pkt_type)
    {
        case POLL_PKT:
        {
            if(sid==MAXSLAVE)
            {
                sid=0;
            }
            if(pktid==3)
            {
                pktid=0;
            }

            for(;sid<MAXSLAVE;sid++)
            {
                if(vlist[sid].chk_count==3)
                {
                    vlist[sid].reset_chk_count++;
                    if(vlist[sid].reset_chk_count==10)
                    {
                        vlist[sid].chk_count=0;
                        vlist[sid].reset_chk_count=0;
                    }

                    continue;
                }


                if(vlist[sid].active==FALSE)
                {
                    vlist[sid].chk_count++;

                }
                writecom(sdev[sid].ppkt[pktid],POLL_PKT_SIZE);
                pktid++;
                if(pktid==3)
                {
                    pktid=0;
                    sid++;
                }
                break;
            }


        }

        case 0x6:
        {
            break;

        }

        case 0x10:
        {
            break;

        }
        default:
        break;

    }

}
