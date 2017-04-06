package ru.amerain.JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.fabric.jdbc.FabricMySQLDriver;

/**
 * Created by User on 05.04.2017.
 */
public class JDBCSettings {
    private Connection connection;
  //  private Driver driver;

    public  JDBCSettings() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    public Connection getConnection(String url,String username,String password) throws SQLException {
        if(connection!=null)
            return connection;
        connection = DriverManager.getConnection(url, username, password);
    return connection;
    }
}
