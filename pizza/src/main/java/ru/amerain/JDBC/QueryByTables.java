package ru.amerain.JDBC;

import ru.amerain.models.Client;
import ru.amerain.models.Order;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by User on 06.04.2017.
 */
public class QueryByTables {
    private ToOrderTable orderTable;
    private ToClientTable clientTable;
    private ToOrderedProductsTable orderedProductsTable;

    public QueryByTables (Connection connection) throws SQLException {
        orderTable = new ToOrderTable(connection);
        clientTable = new ToClientTable(connection);
        orderedProductsTable = new ToOrderedProductsTable(connection);
    }
    public void PushClient(Client client) throws SQLException {
        if(clientTable.SELECT(client)==null)
            clientTable.INSERT(client);
        client.setID(clientTable.SELECTID(client));
    }
    public void PushOrder(Order order) throws SQLException {
        orderTable.INSERT(order);
        //засовываю заказ в таблицу заказы
        order.setID(orderTable.SELECTID());
    }
    public void PushOrderedProducts(Order order) throws SQLException {
        orderedProductsTable.INSERT(order);
    }
}
