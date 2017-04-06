package ru.amerain.servlets;

/**
 * Created by User on 02.04.2017.
 */


import com.sun.xml.internal.bind.v2.model.core.ID;
import ru.amerain.JDBC.ToClientTable;
import ru.amerain.JDBC.ToOrderTable;
import ru.amerain.JDBC.ToOrderedProductsTable;
import ru.amerain.models.Client;
import ru.amerain.models.Order;
import ru.amerain.store.OrderCache;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//http запрос имеет 4 основных метода передачи данных
@WebServlet("/view/order")
public class OrderViewServlet extends HttpServlet {
    //private final OrderCache orderCache = OrderCache.getInstance();
    private ArrayList<Order> ArrayOfOrders;
    private   static final String user="root";
    private   static final String password="rain060896";
    private   static final String url="jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static Connection connection;
    private static Statement stmt;
    private static ResultSet rs;
    public void init(ServletConfig serConf) throws ServletException{
        try{

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //get используется когда мы запрашиваем данные в url
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //  response.setCharacterEncoding("UTF-8");
      //  request.setCharacterEncoding("UTF-8");
        ArrayOfOrders = new ArrayList<Order>();

        try {
            ToOrderTable orderTable = new ToOrderTable(connection);
            ResultSet rs = orderTable.SELECT();//(заказы(нмер заказа,id клиента,адрес...))
            ToClientTable clientTable = new ToClientTable(connection);
            ToOrderedProductsTable orderedProductsTable = new ToOrderedProductsTable(connection);
            //ArrayList<Order> temp = new ArrayList<Order>();

                while (rs.next()){
                ResultSet rsclient = clientTable.SELECTBYID(rs.getInt(2));//id клиента
                    rsclient.next();
                    Client client = new Client(rsclient.getInt("ID"),
                            rsclient.getString("full_name"),rsclient.getString("phone_number"));
                    ResultSet rsdroduct = orderedProductsTable.SELECT
                            (rs.getInt("ID"));
                   int size=0;
                    while (rsdroduct.next())
                        size++;
                    String[] product = new String[size];
                    String[] productCount = new String[size];
                    size =0;
                    rsdroduct.beforeFirst();
                    while (rsdroduct.next()){
                        product[size]=rsdroduct.getString("name_products");
                        productCount[size] = String.valueOf(rsdroduct.getInt("count"));
                    size++;
                    }

                    Order order = new Order(client,rs.getString("adress"),
                    rs.getString("notes"),product,productCount);
                    ArrayOfOrders.add(order);

                }

            } catch (SQLException e) {
            e.printStackTrace();
        }


        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
//
        //начинаем строить json tree
        JsonArrayBuilder arrayOfOrder = Json.createArrayBuilder();
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();


        for( Order order :ArrayOfOrders){
            JsonObjectBuilder orderBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayOfproduct = Json.createArrayBuilder();
            JsonObjectBuilder productsBuilder = Json.createObjectBuilder();

            for(int i =0;i<order.getProducts().length;i++) {
                JsonObjectBuilder productBuilder = Json.createObjectBuilder();
                JsonObject productJson = productBuilder.add("name_product",order.getProducts()[i])
                        .add("count_of_product",order.getCountProducts()[i])
                        .build();
                arrayOfproduct.add(productJson);
            }
         //   JsonObject products = productsBuilder.add("Products",arrayOfproduct).build();
            JsonObject orderJson = orderBuilder
                                            .add("fullname",order.getClient().getFull_name())
                                            .add("phone_number",order.getClient().getPhone_number())
                                            .add("adress",order.getAdress())
                                            .add("Products",arrayOfproduct)
                                            .build();
            arrayOfOrder.add(orderJson);
        }
        JsonObject root = rootBuilder
                .add("Orders",arrayOfOrder)
                .build();
        response.setCharacterEncoding("UTF-8");
        out.print(root.toString());//toString()


//        String json = Json.createObjectBuilder()
//                .add("key1", "value1")
//                .add("key2", "value2")
//                .build()
//                .toString();
//        out.print(json);
//
//
//        System.out.println("Object: " + orderJson);
//
//        out.println();
//// Assuming your json object is **jsonObject**, perform the following, it will return your json object
//        String json = new Gson().toJson(someObject);
//        out.print(jsonObject);
        //out.flush();
//        JSONObject json = new JSONObject();
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);
      //  System.out.println("Hello");
    }
}

