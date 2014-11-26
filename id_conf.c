/*
 * id_conf.c
 *
 * This is the source file where the configuration file
 * for idriver is read and parameters are stored.
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"

//In the following 3 lines,each ParamsA,B,C values initialiased(addr,name,nwords,multiplcn,value,offset).Offset maybe adjusted to suit each param.Initially all offset set to 1 other than powerfactor which is set to 0.1
PARAM_DETAILS params1[MAXPARAMS_A]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,0.0,1,1},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,0.0,1,1},{4,"B_Phase_Voltage_4W",2,0.01,0.0,1,1},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,0.0,1,1},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,0.0,1,1},{10,"B_Phase_Current_4W",2,0.001,0.0,1,1},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,0.0,1,1},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,0.0,1,1},{16,"Active_Power_B_Phase_4W",2,0.0001,0.0,1,1},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,0.0,1,1},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,0.0,1,1},{22,"Reactive_Power_B_Phase_4W",2,0.0001,0.0,1,1},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,0.0,1,1},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,0.0,1,1},{28,"Apparent_Power_B_Phase_4W",2,0.0001,0.0,1,1},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.0,0.1,1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.0,0.1,1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.0,0.1,1},{36,"Total_Active_Power",2,0.0001,0.0,1,1},{38,"Total_Reactive_Power",2,0.0001,0.0,1,1},{40,"Total_Apparent_Power",2,0.0001,0.0,1,1},{42,"Total_Power_Factor",2,0.001,0.0,1,1},{44,"Line_Frequency",2,0.01,0.0,1,1},{46,"Phase_Sequence",2,1,0.0,1,1}};
PARAM_DETAILS params2[MAXPARAMS_B]={{512,"Cumulative_energy_forward_kVAh",2,0.01,0.0,1,1},{514,"Cumulative_energy_forward_kWh",2,0.01,0.0,1,1},{516,"Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1,1},{518,"Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1,1},{520,"Cumulative_energy_reverse_kVAh",2,0.01,0.0,1,1},{522,"Cumulative_energy_reverse_kWh",2,0.01,0.0,1,1},{524,"Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1,1},{526,"Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1,1},{528,"Backup_1_Cumulative_energy_forward_kVAh",2,0.01,0.0,1,1},{530,"Backup_1_Cumulative_energy_forward_kWh",2,0.01,0.0,1,1},{532,"Backup_1_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1,1},{534,"Backup_1_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1,1},{536,"Backup_1_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1,1},{538,"Backup_1_Cumulative_energy_reverse_kWh",2,0.01,0.0,1,1},{540,"Backup_1_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1,1},{542,"Backup_1_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1,1},{544,"Backup_2_Cumulative_energy_forward_kVAh",2,0.01,0.0,1,1},{546,"Backup_2_Cumulative_energy_forward_kWh",2,0.01,0.0,1,1},{548,"Backup_2_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1,1},{550,"Backup_2_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1,1},{552,"Backup_2_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1,1},{554,"Backup_2_Cumulative_energy_reverse_kWh",2,0.01,0.0,1,1},{556,"Backup_2_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1,1},{558,"Backup_2_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1,1},{560,"Backup_3_Cumulative_energy_forward_kVAh",2,0.01,0.0,1,1},{562,"Backup_3_Cumulative_energy_forward_kWh",2,0.01,0.0,1,1},{564,"Backup_3_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1,1},{566,"Backup_3_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1,1},{568,"Backup_3_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1,1},{570,"Backup_3_Cumulative_energy_reverse_kWh",2,0.01,0.0,1,1},{572,"Backup_3_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1,1},{574,"Backup_3_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1,1},{576,"Backup_4_Cumulative_energy_forward_kVAh",2,0.01,0.0,1,1},{578,"Backup_4_Cumulative_energy_forward_kWh",2,0.01,0.0,1,1},{580,"Backup_4_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1,1},{582,"Backup_4_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1,1},{584,"Backup_4_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1,1},{586,"Backup_4_Cumulative_energy_reverse_kWh",2,0.01,0.0,1,1},{588,"Backup_4_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1,1},{590,"Backup_4_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1,1}};
PARAM_DETAILS params3[MAXPARAMS_C]={{1280,"Reset_MD1",2,0.01,0.0,1,1},{1282,"Reset_MD2",2,0.01,0.0,1,1},{1284,"Reset_MD3",2,0.01,0.0,1,1},{1286,"Backup_1_MD1",2,0.01,0.0,1,1},{1288,"Backup_1_MD2",2,0.01,0.0,1,1},{1290,"Backup_1_MD3",2,0.01,0.0,1,1},{1292,"Backup_2_MD1",2,0.01,0.0,1,1},{1294,"Backup_2_MD2",2,0.01,0.0,1,1},{1296,"Backup_2_MD3",2,0.01,0.0,1,1},{1298,"Backup_3_MD1",2,0.01,0.0,1,1},{1300,"Backup_3_MD2",2,0.01,0.0,1,1},{1302,"Backup_3_MD3",2,0.01,0.0,1,1},{1304,"Backup_4_MD1",2,0.01,0.0,1,1},{1306,"Backup_4_MD2",2,0.01,0.0,1,1},{1308,"Backup_4_MD3",2,0.01,0.0,1,1}};//,{1536,"Backup_1_reset_date_time_&_type",4,1,0.0,1,1},{1540,"Backup_2_reset_date_time_&_type",4,1,0.0,1,1},{1544,"Backup_3_reset_date_time_&_type",4,1,0.0,1,1},{1548,"Backup_4_reset_date_time_&_type",4,1,0.0,1,1},{1792,"Reset_Cumulative_MD1",2,0.01,0.0,1,1},{1794,"Reset_Cumulative_MD2",2,0.01,0.0,1,1},{1796,"Reset_Cumulative_MD3",2,0.01,0.0,1,1},{1872,"RD1_elapsed_time",3,0.01,0.0,1,1},{1875,"RD2_elapsed_time",3,0.01,0.0,1,1},{1878,"RD3_elapsed_time",3,0.01,0.0,1,1},{1920,"Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode",8,1,0.0,1,1},{1928,"Reset_count_Format:_00_to_99_0x0063",1,1,0.0,1,1},{1929,"Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063",1,1,0.0,1,1},{1930,"CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping",1,1,0.0,1,1},{1931,"Reserved_READ_AND_WRITE_PARAMETERS_Real_time_clock_RTC",1,1,0.0,1,1},{256,"Current_Time_Year_Month_Format:YYMM_BCD",1,1,0.0,1,1},{257,"Current_Time_Date_Day_Format:DTDY_BCD",1,1,0.0,1,1},{258,"Current_Time_Hour_Minute_Format:HHMM_BCD",1,1,0.0,1,1},{259,"Current_Time_econd_Format:SS00_BCD",1,1,0.0,1,1},{2048,"CT_Primary",1,1,0.0,1,1},{2049,"CT_Secondary",1,1,0.0,1,1},{2050,"PT_Primary",1,1,0.0,1,1},{2051,"PT_Secondary",1,1,0.0,1,1},{2128,"Reset_type_days_and_Lockout_days_1st_word_Reset_time_HH_MM_hour_min_2nd_word",2,1,0.0,1,1},{2160,"Setting_for_MD1",2,1,0.0,1,1},{2162,"Setting_for_MD2",2,1,0.0,1,1},{2164,"Setting_for_MD3",2,1,0.0,1,1},{2304,"Method_of_energy_calculation_Lead=Lead_Lead=UPF",1,1,0.0,1,1},{2305,"Meter_direction_Unidirectional_Bidirectional",1,1,0.0,1,1}};
PARAM_DETAILS params1rand[MAXPARAMS_A]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,227.0,5,1},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,236.0,5,1},{4,"B_Phase_Voltage_4W",2,0.01,239.0,5,1},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,119.0,10,1},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,103.0,10,1},{10,"B_Phase_Current_4W",2,0.001,123.0,10,1},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,22.0,5,1},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,21.0,5,1},{16,"Active_Power_B_Phase_4W",2,0.0001,24.0,5,1},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,16.0,5,1},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,14.0,5,1},{22,"Reactive_Power_B_Phase_4W",2,0.0001,19.0,5,1},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,28.0,5,1},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,26.0,5,1},{28,"Apparent_Power_B_Phase_4W",2,0.0001,31.0,5,1},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.5,0.1,1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.5,0.1,1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.5,0.1,1},{36,"Total_Active_Power",2,0.0001,68.0,5,1},{38,"Total_Reactive_Power",2,0.0001,50.0,5,1},{40,"Total_Apparent_Power",2,0.0001,85.0,5,1},{42,"Total_Power_Factor",2,0.001,0.0,5,1},{44,"Line_Frequency",2,0.01,0.0,5,1},{46,"Phase_Sequence",2,1,0.0,5,1}};
//In the above elements one more flag db_update is added and set to 1 initially for all params on 26nov2014.



int no_of_dev;
FILE * fconf=NULL;
char portname[256];
char em_mode[256];
char bd_rate[256];
int  baud=B9600;
char poll_int[256];
char temp_buf[256];


int OpenConfiguration();
int remove_blnk_lines(char *line);
int GetDevIDs();
int GetPortName();
int GetMode();
int GetBaudRate();
int GetPollInterval();
int GetParamsDetails();
int GetValidParams();//values are read,but not used in program
int Read_Conf();


int OpenConfiguration()
{
    if((fconf=fopen("idriver.conf",(const char *)"r"))!=NULL)
    {
        return TRUE;
    }
    return FALSE;

}

int GetPollInterval()
{
        memset(poll_int,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[POLL_INTERVAL]")==0)
                {
                    memset(poll_int,0,256);
                    fgets((char *)&poll_int[0],256,fconf);
                    bd_rate[strlen(poll_int)-1]=0x0;
                    p_int=strtol((char *)&poll_int[0],NULL,10);
                    sprintf(msg_to_log,"POLL INTERVAL is %d",p_int);
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                    return TRUE;
                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetBaudRate()
{
        memset(bd_rate,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[BAUDRATE]")==0)
                {
                    memset(bd_rate,0,256);
                    fgets((char *)&bd_rate[0],256,fconf);
                    bd_rate[strlen(bd_rate)-1]=0x0;
                    baud=strtol((char *)&bd_rate[0],NULL,10);
                    sprintf(msg_to_log,"BAUDRATE is %d",baud);
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                    switch(baud)
                    {
                        case 2400:baud=B2400;break;
                        case 4800:baud=B4800;break;
                        case 9600:baud=B9600;break;
                        case 19200:baud=B19200;break;
                        case 38400:baud=B38400;break;
                        default:baud=B9600;break;
                    }


                    return TRUE;
                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetMode()
{


        memset(em_mode,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[EMULATOR_MODE]")==0)
                {
                    memset(em_mode,0,256);
                    fgets((char *)&em_mode[0],256,fconf);
                    em_mode[strlen(em_mode)-1]=0x0;
                    if(strcasecmp(em_mode,"YES")==0)
                    {
                        emulator_mode=TRUE;
                        return TRUE;
                    }

                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetPortName()
{

  char ch;

  memset(portname,0,256);
  fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[COMPORT]")==0)
                {
                    memset(portname,0,256);
                    while(1)
                    {
                        ch=fgetc(fconf);
                        if((ch=='[')||(ch=='/')||feof(fconf))
                        {
                            if(feof(fconf))
                                clearerr(fconf);
                            break;
                        }
                    }
                    if(ch=='/')
                    {
                        portname[0]='/';
                        fgets((char *)&portname[1],256,fconf);
                        portname[strlen(portname)-1]=0x0;
                        cport=portname;
                        sprintf(msg_to_log,"COMPORT is %s",cport);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                        return TRUE;
                    }
                    else
                    {
                        return FALSE;
                    }
                }
            }
            else if(!feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;
}

int remove_blnk_lines(char *line)
{
  int line_len;

  line_len = strlen((char *)line);
  line[line_len-1]=0x0;
  line_len--;

  if(line_len < 2)
  {
     return 0;
  }
  return line_len;
}

int GetParamsDetails()//values are read,but not used in program
{
  int i=0,j=0,k=0,l=0,m=0;


    fseek(fconf,0,SEEK_SET);

    while(!feof(fconf))
    {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                if(!remove_blnk_lines(temp_buf)) continue;

                if(strcmp(temp_buf,"[PARAM_CONFIG]")==0)
                {
                    while(!feof(fconf))
                    {
                        memset(temp_buf,0,256);
                        if(fgets(temp_buf,256,fconf)!=NULL)
                        {
                            for(j=0;j<strlen(temp_buf);j++)
                            {
                                if(temp_buf[j]=='[')
                                {
                                    no_of_cmds=i;
                                    return TRUE;
                                }
                            }
                            if( ! remove_blnk_lines(temp_buf) ) continue;
                            memset(config_param[i].param_name,0x0,1024);
                            memcpy(config_param[i].param_name,temp_buf,strlen(temp_buf));
                            //printf("\n%s -> ",config_param[i].param_name);
                            m=0;
                            memset(temp_buf,0,256);
                            if(fgets(temp_buf,256,fconf)!=NULL)
                            {
                                temp_buf[strlen(temp_buf)-1]=',';
                                for(j=0,k=0,l=0;j<strlen(temp_buf);j++)
                                {
                                    if(temp_buf[j]==',')
                                    {
                                        m++;
                                        switch(m)
                                        {
                                            case 1:
                                                config_param[i].devid=strtol((char *)&temp_buf[l],NULL,10);
                                                //printf("%d :",config_param[i].devid);
                                                break;
                                            case 2:
                                                config_param[i].start_addr= strtol((char *)&temp_buf[l],NULL,10);
                                                //printf(" %d :",config_param[i].start_addr);
                                                break;
                                            case 3:
                                                config_param[i].no_of_reg= strtol((char *)&temp_buf[l],NULL,10);
                                                //printf(" %d :",config_param[i].no_of_reg);
                                                break;
                                            case 4:
                                                config_param[i].value_offset= strtol((char *)&temp_buf[l],NULL,10);
                                                //printf(" %d\n",config_param[i].value_offset);
                                                break;


                                            default : break;
                                        }
                                        l=j+1;
                                        k++;

                                    }
                                }
                            }
                            i++;
                        }
                    }
                }
            }
            else if(feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetValidParams()//values are read,but not used in program
{
    int i=0,j=0,k=0,l=0;
    int id=0;
    int addr=0;
    float off=0.0;
    char pbuf[32];

    fseek(fconf,0,SEEK_SET);

    while(!feof(fconf))
    {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                if( ! remove_blnk_lines(temp_buf) ) continue;

                if(strcmp(temp_buf,"[VALID_PARAMS]")==0)
                {
                    for(i=0;i<MAXSLAVE;i++)//Reset all db_update flag to 0,for enabling selective db update.
                    {

                        for(j=0;j<MAXPARAMS_A;j++)
                        {
                            vlist[i].param_valueA[j].db_update=0;//Reset to 0 from 1, to only selectively update params to DB
                        }
                        for(j=0;j<MAXPARAMS_B;j++)
                        {
                            vlist[i].param_valueB[j].db_update=0;//Reset to 0 from 1, to only selectively update params to DB
                        }
                        for(j=0;j<MAXPARAMS_C;j++)
                        {
                            vlist[i].param_valueC[j].db_update=0;//Reset to 0 from 1, to only selectively update params to DB
                        }
                    }


                    while(!feof(fconf))
                    {
                        memset(temp_buf,0,256);
                        if(fgets(temp_buf,256,fconf)!=NULL)
                        {
                            if( ! remove_blnk_lines(temp_buf) ) continue;

                            if(temp_buf[0]=='[')
                            {
                                //printf("\n");
                                return TRUE;
                            }
                            else
                            {
                                //printf("\n%s",temp_buf);
                                for(i=0;i<strlen(temp_buf);i++)
                                {
                                    if(temp_buf[i]==':')
                                    {
                                        id=strtol((char *)&temp_buf[0],NULL,10);
                                        j=i+1;
                                        //printf("\nid is %d",id);
                                    }
                                    if(temp_buf[i]=='(')
                                    {
                                        j=i+1;
                                    }
                                    if(temp_buf[i]==')')
                                    {
                                        memset(pbuf,0x0,32);
                                        strncpy(pbuf,&temp_buf[j],i-j);
                                        //printf("\nPBUF : %s",pbuf);
                                        l=0;
                                        for(k=0;k<strlen(pbuf);k++)
                                        {
                                            if(pbuf[k]==',')
                                            {
                                                addr=strtol((char *)&pbuf[l],NULL,10);
                                                //printf("\naddr is %d",addr);
                                                //printf("\n%s",&pbuf[l]);
                                                l=k+1;
                                                off=strtof((char *)&pbuf[l],NULL);
                                                //printf("\noff is %.2f",off);
                                                //printf("\n%s",&pbuf[l]);
                                                for(j=0;j<MAXPARAMS_A;j++)
                                                {
                                                    if(vlist[id].param_valueA[j].addr_off==addr)
                                                    {
                                                        //printf("\nvlist[%d].param_valueA[%d].db_update=%d\n",id,j,vlist[id].param_valueA[j].db_update);
                                                        vlist[id].param_valueA[j].db_update=1;
                                                        vlist[id].param_valueA[j].offset=off;
                                                        //printf("\nvlist[%d].param_valueA[%d].db_update=%d\n",id,j,vlist[id].param_valueA[j].db_update);
                                                        goto skip_rest;
                                                    }

                                                }

                                                for(j=0;j<MAXPARAMS_B;j++)
                                                {
                                                    if(vlist[id].param_valueB[j].addr_off==addr)
                                                    {
                                                        vlist[id].param_valueB[j].db_update=1;
                                                        vlist[id].param_valueB[j].offset=off;
                                                        //printf("\nvlist[%d].param_valueB[%d].db_update=%d\n",id,j,vlist[id].param_valueB[j].db_update);
                                                        goto skip_rest;
                                                    }

                                                }

                                                for(j=0;j<MAXPARAMS_C;j++)
                                                {
                                                    if(vlist[id].param_valueC[j].addr_off==addr)
                                                    {
                                                        vlist[id].param_valueC[j].db_update=1;
                                                        vlist[id].param_valueC[j].offset=off;
                                                        //printf("\nvlist[%d].param_valueC[%d].db_update=%d\n",id,j,vlist[id].param_valueC[j].db_update);
                                                        goto skip_rest;
                                                    }

                                                }

                                                skip_rest:
                                                ;//label need to be followed by a statement.A single semicloumn servers as a blank statement

                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }
                }

            }
            else if(feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;
}



int GetDevIds()//values are read,but not used in program
{
    int i=0,j=0,k=0,l=0;



    fseek(fconf,0,SEEK_SET);

    while(!feof(fconf))
    {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                if(!remove_blnk_lines(temp_buf)) continue;

                if(strcmp(temp_buf,"[DEVICEIDS]")==0)
                {
                    i=0;
                    while(!feof(fconf))
                    {
                        memset(temp_buf,0,256);
                        if(fgets(temp_buf,256,fconf)!=NULL)
                        {
                            for(j=0;j<strlen(temp_buf);j++)
                            {
                                if(temp_buf[j]=='[')
                                {
                                    no_of_dev=i;
                                    return TRUE;
                                }
                            }
                            if( ! remove_blnk_lines(temp_buf) ) continue;

                            memcpy(dev_config[i].ename,temp_buf,strlen(temp_buf));
                            memset(temp_buf,0,256);
                            if(fgets(temp_buf,256,fconf)!=NULL)
                            {
                                temp_buf[strlen(temp_buf)-1]=',';
                                for(j=0,k=0,l=0;j<strlen(temp_buf);j++)
                                {
                                    if(temp_buf[j]==',')
                                    {
                                        dev_config[i].elist[k]= strtol((char *)&temp_buf[l],NULL,16);
                                        l=j+1;
                                        k++;
                                    }
                                }
                            }
                            dev_config[i].no_of_elem=k;
                            i++;
                        }
                    }
                }
            }
            else if(feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;

}

int Read_Conf()
{

  int i=0,j=0;


    if(OpenConfiguration())
    {
            if(!GetPortName())
            {
                sprintf(msg_to_log,"COMPORT is not defined in Config file,Exiting");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                return FALSE;
            }

            if(!GetBaudRate())
            {
                sprintf(msg_to_log,"BAUDRATE is not defined in Config file,Exiting");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                return FALSE;
            }

            if(!GetPollInterval())
            {
                p_int=POLL_INTERVAL;
                sprintf(msg_to_log,"POLL INTERVAL is not defined in Config file,Using Default Value %d milliseconds",p_int);
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
            }

            if(GetMode())
            {
                sprintf(msg_to_log,"EMULATOR MODE STARTING");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
            }

            GetValidParams();

            if(GetDevIds())//optional
            {
                for(i=0;i<no_of_dev;i++)
                {
                    //printf("\nMeter %s Device Ids are : ",dev_config[i].ename);
                    for(j=0;j<dev_config[i].no_of_elem;j++)
                    {
                        //printf(" %X",dev_config[i].elist[j]);
                    }
                    //printf("\n");
                }

            }

            fclose(fconf);

      }
      return TRUE;
}
