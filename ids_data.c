#include "ids_common.h"
#include "ids_data.h"



WORD LoWord(unsigned int val)
{
	return ((val<<16)>>16);
}

WORD HiWord(unsigned int val)
{
	return val>>16;
}


WORD Add_CRC(BYTE buf[], int len)
{
	WORD crc = 0xFFFF;
	int i=0,pos=0;


  	for(pos = 0; pos < len; pos++)
	{
    		crc ^= (WORD )buf[pos];

    		for (i = 8; i != 0; i--)
            {
      			if ((crc & 0x0001) != 0)
                {
        			crc >>= 1;
        			crc ^= 0xA001;
      			}
      			else
        			crc >>= 1;
    		}
  	}
  	return ((WORD)(crc<<8)|(WORD)(crc>>8));
}


BYTE LoByte(WORD val)
{
	return ((val<<8)>>8);
}



BYTE HiByte(WORD val)
{
	return val>>8;
}


int Check_CRC(BYTE * pktdata, int pktcount)
{

            if(pktdata[pktcount-2]==HiByte(Add_CRC(pktdata,pktcount-2)))
            {
                if(pktdata[pktcount-1]==LoByte(Add_CRC(pktdata,pktcount-2)))
                {
                    return TRUE;
                }
            }
            return FALSE;
}


void prepare_slave_data(BYTE *inbuf,int inlen)
{

}

void process_master_data(BYTE *inbuf,int inlen)
{
    int i=0;

    if(Check_CRC(inbuf,inlen))
    {
        printf("\nRead Valid Data from Master\n");

        for(i=0;i<inlen;i++)
        {
            printf("%X ",inbuf[i]);
        }
        printf("\n");
        prepare_slave_data(inbuf,inlen);
    }
    else
    {
        printf("\nMaster Data : CRC Failed\n");
        for(i=0;i<inlen;i++)
        {
            printf("%X ",inbuf[i]);
        }
        printf("\n");
    }
}

