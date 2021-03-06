package ru.amerain.jdbc;

import ru.amerain.models.Order;

import java.sql.*;

/**
 * Created by User on 05.04.2017.
 */
public class ToOrderTable {
    private Connection connection;
    private PreparedStatement statement;
   // private Order order;

    public ToOrderTable(Connection connection) throws SQLException {
        this.connection = connection;
    //    this.order = order;

    }
    public int INSERT(Order order) throws SQLException {
        statement = connection.prepareStatement
                ("INSERT INTO pizzas_delivery.orders (clients_ID, adress, notes) VALUE (?,?,?)", Statement.RETURN_GENERATED_KEYS);


        statement.setInt(1,order.getClient().getID());//можно ли брать id
        statement.setString(2,order.getAdress());
        statement.setString(3,order.getNotes());

        statement.execute();

        ResultSet Key = statement.getGeneratedKeys();
        Key.next();
        return Key.getInt(1);
    }
    public ResultSet GetAllOrders() throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.orders");
        return statement.executeQuery();//список всех заказов(clients_ID, adress, notes)
    }
    public int SELECTID() throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM pizzas_delivery.orders order by orders.ID DESC LIMIT 1");
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);//next
    }
//    public ArrayList<Order> ToOrder(ResultSet rsOrders) throws SQLException {
//        ArrayList<Order> temp = new ArrayList<Order>();
//        while (rsOrders.next()){
//            Client client = null;
//            Order order = new Order(rsOrders.getInt(1),client,rsOrders.getString(3));
//          //  temp.add()
//        }
//    }
//    public int SELECTID(ResultSet resultSet) throws SQLException {
//        resultSet.beforeFirst();
//        resultSet.next();
//        return resultSet.getInt(1);
//    }

}
