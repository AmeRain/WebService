package ru.amerain.jdbc;

import ru.amerain.models.Client;

import java.sql.*;

/**
 * Created by User on 05.04.2017.
 */
public class ToClientTable {
    private Connection connection;
    private PreparedStatement statement;


    public ToClientTable(Connection connection) throws SQLException {
        this.connection = connection;

    }
    public int INSERT(Client client) throws SQLException {
        if(SELECT(client)==null) {
            statement = connection.prepareStatement
                    ("INSERT INTO pizzas_delivery.clients ( full_name, phone_number) VALUE (?,?)",
                            Statement.RETURN_GENERATED_KEYS);


            statement.setString(1, client.getFull_name());
            statement.setString(2, client.getPhone_number());

            statement.execute();

            ResultSet Key = statement.getGeneratedKeys();
            Key.next();
            return Key.getInt(1);
        }
        else
            return SELECTID(client);

    }


    public ResultSet SELECT(Client client) throws SQLException {

        ResultSet resultSet;
        statement = connection.prepareStatement
                ("SELECT pizzas_delivery.clients.ID FROM pizzas_delivery.clients WHERE clients.phone_number = ? AND clients.full_name= ?");
        statement.setString(1,client.getPhone_number());
        statement.setString(2,client.getFull_name());

        resultSet = statement.executeQuery();
       int i =0;
        while (resultSet.next())
           i++;

        resultSet.beforeFirst();
        if(i>0)
            return resultSet;
        else return null;
    }

    private int SELECTID(Client client) throws SQLException {

        ResultSet rs = SELECT(client);
        rs.next();
        return rs.getInt(1);//next
    }

    public  ResultSet SELECTBYID(int id) throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.clients WHERE clients.ID=?");
        statement.setInt(1,id);
       return statement.executeQuery();
    }
}
