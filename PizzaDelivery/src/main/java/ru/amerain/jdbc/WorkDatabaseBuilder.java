package ru.amerain.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by User on 10.04.2017.
 */
public class WorkDatabaseBuilder {
  // private WorkDatabase workDatabase;

   public WorkDatabase getDatabase(){
       Properties properties = new Properties();
       try {
           properties.load
                   (new FileInputStream
                           (getClass().getClassLoader().getResource("choosedatabase.properties").getFile())
                   );
           String name = properties.getProperty("name_database");
           if(name.equals("mysql"))
              return new WorkMySQL();
           if (name.equals("postresql"))
               return new WorkPostgreSQL();

       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
}
