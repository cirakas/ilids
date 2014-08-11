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

/**@brief  This function Initializes the poll packets for each device to be send later.

           Function: intitialize_poll_packet

           Purpose:  Initializes the poll packets for each device to be send later.

           Returns:  None
*/


void intitialize_poll_packet()
{
    int i=0,j=0;

    for(i=0;i<MAXSLAVE;i++)
    {
        {
            sdev[i].ppkt[0]=(BYTE *)calloc(POLL_PKT_SIZE ,1);
            sdev[i].ppkt[1]=(BYTE *)calloc(POLL_PKT_SIZE,1);
            sdev[i].ppkt[2]=(BYTE *)calloc(POLL_PKT_SIZE,1);
            for(j=0;j<3;j++)
            {
                sdev[i].ppkt[j][0]=i+1;
                sdev[i].ppkt[j][1]=Read_Holding_Registers;
            }
            sdev[i].ppkt[0][2]=HiByte(addr_MAXPARAMS_A);
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
