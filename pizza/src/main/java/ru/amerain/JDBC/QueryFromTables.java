package ru.amerain.JDBC;

import ru.amerain.models.Client;
import ru.amerain.models.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by User on 06.04.2017.
 */
public class QueryFromTables {
    private ToOrderTable orderTable;
    private ToClientTable clientTable;
    private ToOrderedProductsTable orderedProductsTable;
    private ArrayList<Order> ArrayOfOrders;
    private String[] product;
    private String[] productCount;

    public  QueryFromTables (Connection connection) throws SQLException {
        orderTable = new ToOrderTable(connection);
        clientTable = new ToClientTable(connection);
        orderedProductsTable = new ToOrderedProductsTable(connection);
        ArrayOfOrders = new ArrayList<Order>();
    }

    private Client CreateClient(int id) throws SQLException {
        ResultSet rsclient = clientTable.SELECTBYID(id);
        rsclient.next();
        return new Client(rsclient.getInt("ID"),
                rsclient.getString("full_name"),rsclient.getString("phone_number"));

    }
    private void CreateProductList(int id) throws SQLException {
        ResultSet rsdroduct = orderedProductsTable.SELECT(id);
        int size=0;
        while (rsdroduct.next())
            size++;
        product = new String[size];
        productCount = new String[size];
        size =0;
        rsdroduct.beforeFirst();
        while (rsdroduct.next()){
            product[size]=rsdroduct.getString("name_products");
            productCount[size] = String.valueOf(rsdroduct.getInt("count"));
            size++;
        }
    }

    public ArrayList<Order> ParseToQuery() throws SQLException {

        ResultSet rs = orderTable.SELECT();//(заказы(нмер заказа,id клиента,адрес...))

        while (rs.next()){
            Client client = CreateClient(rs.getInt(2));//id клиента
            CreateProductList(rs.getInt("ID"));

            Order order = new Order(client,rs.getString("adress"),
                    rs.getString("notes"),product,productCount);
            ArrayOfOrders.add(order);

        }
    return ArrayOfOrders;
    }
}
