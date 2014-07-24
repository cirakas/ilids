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


void db_close()
{
   mysql_close(conn);
}


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
