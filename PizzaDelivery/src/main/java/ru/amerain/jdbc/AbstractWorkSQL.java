package ru.amerain.jdbc;

//import com.sun.org.apache.xpath.internal.operations.String;
import ru.amerain.models.Client;
import ru.amerain.models.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/**
 * Created by User on 10.04.2017.
 */
public abstract class AbstractWorkSQL implements WorkDatabase {
    private Connection connection;
    public AbstractWorkSQL(){
        Settings settings = Settings.getInstance();
        try {

            Class.forName(settings.value("jdbc.driver_class"));

            this.connection = DriverManager.getConnection(settings.value("jdbc.url"), settings.value("jdbc.username"), settings.value("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void CreateClient(Client client){
        ToClientTable clientTable;
        int id;
        try {
            clientTable = new ToClientTable(connection);
                 id = clientTable.INSERT(client);
            client.setID(id);//здесь устанавливать id?
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void CreateOrder(Order order) {
        ToOrderTable orderTable;
        int id;
        try {
            orderTable = new ToOrderTable(connection);
            id = orderTable.INSERT(order);
            order.setID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void CreateOrderedProducts(Order order) {
        ToOrderedProductsTable orderedProductsTable;
        try {
            orderedProductsTable = new ToOrderedProductsTable(connection);
            orderedProductsTable.INSERT(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOrder(Order order,Client client){
        CreateClient(client);
        order.setClient(client);
        CreateOrder(order);
        CreateOrderedProducts(order);
    }

    private Client getClient(int id){
        ToClientTable clientTable;
        ResultSet setClient;
        try {
            clientTable = new ToClientTable(connection);
            setClient = clientTable.SELECTBYID(id);
            setClient.next();
            return new Client(setClient.getInt("ID"),
                    setClient.getString("full_name"),setClient.getString("phone_number"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getProducts(int id, String[] products) throws SQLException {
        ToOrderedProductsTable orderedProductsTable = new ToOrderedProductsTable(connection);
        ResultSet rsdroduct = orderedProductsTable.SELECT(id);
        int size=0;
        while (rsdroduct.next())
            size++;
        products = new String[size];
        size =0;
        rsdroduct.beforeFirst();
        while (rsdroduct.next()){
            products[size]=rsdroduct.getString("name_products");
            size++;
        }
        return products;
    }
    private String[] getProductsCount(int id,String[] count)throws SQLException{
        ToOrderedProductsTable orderedProductsTable = new ToOrderedProductsTable(connection);
        ResultSet rsdroduct = orderedProductsTable.SELECT(id);
        int size=0;
        while (rsdroduct.next())
            size++;
        count = new String[size];
        size =0;
        rsdroduct.beforeFirst();
        while (rsdroduct.next()){
            count[size] = String.valueOf(rsdroduct.getInt("count"));
            size++;
        }
        return count;
    }

     public List<Order> getOrder(){
        List<Order> orders = new ArrayList<Order>();
         try {
             ToOrderTable orderTable = new ToOrderTable(connection);
             ResultSet setOrders = orderTable.GetAllOrders();

             while (setOrders.next()){

                 String[] products = null;
                 String[] count = null;
                 Order order = new Order(getClient(setOrders.getInt("clients_ID")),
                         setOrders.getString("adress"),
                         setOrders.getString("notes"),
                         getProducts(setOrders.getInt("ID"),products),
                         getProductsCount(setOrders.getInt("ID"),count)
                                 );
                 orders.add(order);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return orders;
     }

}
