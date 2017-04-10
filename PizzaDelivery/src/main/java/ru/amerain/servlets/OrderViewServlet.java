package ru.amerain.servlets;

/**
 * Created by User on 02.04.2017.
 */


import ru.amerain.jdbc.*;
import ru.amerain.models.Order;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

//http запрос имеет 4 основных метода передачи данных
@WebServlet("/view/order")
public class OrderViewServlet extends HttpServlet {

    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        WorkDatabaseBuilder workDatabaseBuilder = new WorkDatabaseBuilder();
        WorkDatabase database = workDatabaseBuilder.getDatabase();
        List<Order> orders = database.getOrder();


        //Json

        PrintWriter out;
        //   response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out = response.getWriter();
//
        //начинаем строить json tree
        JsonArrayBuilder arrayOfOrder = Json.createArrayBuilder();
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();


        for( Order order :orders){
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
    }


}

