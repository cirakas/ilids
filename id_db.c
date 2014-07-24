#include "id_common.h"


MYSQL *conn;
//MYSQL_RES *res;
//MYSQL_ROW row;

char *server = SERVER_LOCAL_IP;
char *user = "comserver";
char *password = "compass";
char *database = "ilids";


void db_close()
{
    /* close connection */
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

   /* send SQL query
   if (mysql_query(conn, "show tables"))
   {
      fprintf(stderr, "%s\n", mysql_error(conn));
      return FALSE;
   }*/

    /*output table name
   res = mysql_use_result(conn);
   printf("\nMySQL Tables in mysql database:\n");
   while ((row = mysql_fetch_row(res)) != NULL)
   {
      printf("%s \n", row[0]);
   }
   mysql_free_result(res);
   */

   /*if (mysql_query(con, "CREATE TABLE Cars(Id INT, Name TEXT, Price INT)"))
   {
      finish_with_error(con);
   }
    if (mysql_query(conn, "CREATE TABLE Cars(Id INT, Name TEXT, Price INT)"))
    {
      printf("\nError entering database values : %S\n",strerror(errno));
    }

    if (mysql_query(conn, "INSERT INTO Cars VALUES(1,'Audi',52642)"))
    {
      printf("\nError entering database values : %S\n",strerror(errno));
    }

   if(mysql_query(conn, "INSERT INTO user VALUES('sree777@gmail.com',1,'mypass','sree','sreejith')"))
   {
       printf("\nError entering database values : %S\n",strerror(errno));

   }*/
}
