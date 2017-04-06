package ru.amerain.JDBC;

import ru.amerain.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 06.04.2017.
 */
public class ToOrderedProductsTable {
    private Connection connection;
    private PreparedStatement statement;
    // private Order order;

    public ToOrderedProductsTable(Connection connection) throws SQLException {
        this.connection = connection;
        //    this.order = order;

    }

    public void INSERT(Order order) throws SQLException {
        statement = connection.prepareStatement
                ("INSERT INTO pizzas_delivery.ordered_products (ID_order, name_products, count) VALUE (?,?,?)");

        for (int i = 0; i < order.getProducts().length; i++) {
            statement.setInt(1, order.getId());
            statement.setString(2, order.getProducts()[i]);
            statement.setInt(3, Integer.parseInt(order.getCountProducts()[i]));
            //statement.setInt(3,2);
            statement.execute();
        }

//        ResultSet temp = new ToClientTable(connection).SELECT(order.getClient());
//        while (temp.next())
//            statement.setInt(1,temp.getInt("ID"));
//        statement.setString(2,order.getAdress());
//        statement.setString(3,order.getNotes());
//
//        statement.execute();
    }
    public ResultSet SELECT(int condition) throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.ordered_products WHERE pizzas_delivery.ordered_products.ID_order="+condition);
        return statement.executeQuery();
    }
}
