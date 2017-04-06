package ru.amerain.JDBC;

import ru.amerain.models.Client;
import ru.amerain.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 05.04.2017.
 */
public class ToClientTable {
    private Connection connection;
    private PreparedStatement statement;
    // private Order order;

    public ToClientTable(Connection connection) throws SQLException {
        this.connection = connection;
        //    this.order = order;

    }
    public void INSERT(Client client) throws SQLException {
        statement = connection.prepareStatement
                ("INSERT INTO pizzas_delivery.clients ( full_name, phone_number) VALUE (?,?)");


        statement.setString(1,client.getFull_name());
        statement.setString(2,client.getPhone_number());

        statement.execute();

    }


    public ResultSet SELECT(Client client) throws SQLException {

       // String myString = "\'";
        ResultSet resultSet = null;
        String query = "SELECT pizzas_delivery.clients.ID FROM pizzas_delivery.clients WHERE clients.phone_number=";
        statement = connection.prepareStatement
                (query+(client.getPhone_number()).toString());
//        statement = connection.prepareStatement
//               ("SELECT pizzas_delivery.clients.ID FROM pizzas_delivery.clients");
        resultSet = statement.executeQuery();// вернуть id клиента с таким номером и именем
       int i =0;
        while (resultSet.next())
           i++;

        resultSet.beforeFirst();
        if(i>0)
            return resultSet;
        else return null;
    }
//    public int SELECTID() throws SQLException {
//        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.clients order by clients.ID DESC LIMIT 1");
//        ResultSet rs = statement.executeQuery();
//        rs.next();
//        return rs.getInt(1);//next
//
//    }
    public int SELECTID(Client client) throws SQLException {

        ResultSet rs = SELECT(client);
        rs.next();
        return rs.getInt(1);//next
    }

    public  ResultSet SELECTBYID(int id) throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.clients WHERE clients.ID="+id);
       return statement.executeQuery();
    }
}
