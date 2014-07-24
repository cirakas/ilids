/*
 * id_db.c
 *
 * This is the source file where the database
 * handling functions are set up.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */



#include "id_common.h"


MYSQL *conn;

char *server = SERVER_LOCAL_IP;
char *user = "comserver";
char *password = "compass";
char *database = "ilids";

/*--------------------------------db_close -----------------------------------
           Function: db_close

           Purpose:  Closes the connection to the MYSQL database

           Returns:  None
-------------------------------------------------------------------------------*/

void db_close()
{
   mysql_close(conn);
}

/*--------------------------------db_start -----------------------------------
           Function: db_start

           Purpose:  Initiates the connection to the MYSQL database

           Returns:  TRUE or FALSE
-------------------------------------------------------------------------------*/

int db_start()
{
   conn = mysql_init(NULL);

   if (!mysql_real_connect(conn, server,user, password, database, 0, NULL, 0))
   {
      fprintf(stderr, "%s\n", mysql_error(conn));
      return FALSE;
   }

   return TRUE;
}
