package ru.amerain.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by User on 10.04.2017.
 */
public class Settings {
    private static Settings Instance = new Settings();
    private Properties properties = new Properties();
    public static Settings getInstance() {
        return Instance;
    }

    private Settings() {
        try {
       properties.load
                (new FileInputStream
                        (getClass().getClassLoader().getResource("choosedatabase.properties").getFile())
                );
       if(properties.getProperty("name_database").equals("mysql"))
            properties.load
                    (new FileInputStream
                            (getClass().getClassLoader().getResource("mysql.properties").getFile())
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String value(String key){
        return properties.getProperty(key);
    }
}
