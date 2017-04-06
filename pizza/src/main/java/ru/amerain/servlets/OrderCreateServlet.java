package ru.amerain.servlets;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import ru.amerain.JDBC.JDBCSettings;
import ru.amerain.JDBC.ToClientTable;
import ru.amerain.JDBC.ToOrderTable;
import ru.amerain.JDBC.ToOrderedProductsTable;
import ru.amerain.models.Client;
import ru.amerain.models.Order;
import ru.amerain.store.OrderCache;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 04.04.2017.
 */
@WebServlet("/add/order")
public class OrderCreateServlet extends HttpServlet {
  //  private final AtomicInteger ID = new AtomicInteger();
 //   private final OrderCache orderCache = OrderCache.getInstance();
    private   static final String user="root";
    private   static final String password="rain060896";
    private   static final String url="jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static Connection connection;
    private static Statement stmt;
    private static ResultSet rs;
    public void init(ServletConfig serConf) throws ServletException{
        try{
            // Driver driver = new FabricMySQLDriver();
            //  DriverManager.registerDriver(driver);
            Class.forName("com.mysql.jdbc.Driver");
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            Client client = new Client
                    (request.getParameter("full_name"),request.getParameter("phone_number"));
            ToClientTable clientTable = new ToClientTable(connection);

            if(clientTable.SELECT(client)==null)
                clientTable.INSERT(client);
            client.setID(clientTable.SELECTID(client));

            String[] arrrayProduct = request.getParameterValues("product");
            String[] arrayCountProduct = request.getParameterValues("count");
            Order order = new Order
                    (client,request.getParameter("client_place"),request.getParameter("date"),
                            arrrayProduct,arrayCountProduct);

            ToOrderTable orderTable = new ToOrderTable(connection);
            orderTable.INSERT(order);
            //засовываю заказ в таблицу заказы
            order.setID(orderTable.SELECTID());

            ToOrderedProductsTable toOrderedProductsTable = new ToOrderedProductsTable(connection);
            toOrderedProductsTable.INSERT(order);

    } catch (SQLException e) {
        e.printStackTrace();
    }
//        try {
//            JDBCSettings jdbcSettings = new JDBCSettings();
//            Connection connection = jdbcSettings.getConnection
//                    ("jdbc:mysql://localhost:3306/pizzas_delivery","root","rain060896");
//            Client client = new Client
//                    (request.getParameter("full_name"),request.getParameter("phone_number"));
//            ToClientTable clientTable = new ToClientTable(connection);
//            if(clientTable.SELECT(client)==null)
//                clientTable.INSERT(client);
//
//            Order order = new Order
//                    (client,request.getParameter("client_place"),request.getParameter("date"));
//
//            ToOrderTable orderTable = new ToOrderTable(connection);
//            orderTable.INSERT(order);
//            //засовываю заказ в таблицу заказы
//        }
//        catch (SQLException ex){System.out.println("ups");}
//
//        //проверить есть ли такой клиент в бд,если нет то добавить нового с новым ID если есть то получить данные о нем

    }
}
