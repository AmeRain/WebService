package ru.amerain.servlets;

/**
 * Created by User on 02.04.2017.
 */


import ru.amerain.models.Order;
import ru.amerain.store.OrderCache;

import java.io.IOException;
import java.io.PrintWriter;

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
    private final OrderCache orderCache = OrderCache.getInstance();



    //get используется когда мы запрашиваем данные в url
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //  response.setCharacterEncoding("UTF-8");
      //  request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

    //    response.setContentType("text/html");
        PrintWriter out = response.getWriter();
//
        //начинаем строить json tree
        JsonArrayBuilder arrayOfOrder = Json.createArrayBuilder();
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();


        for( Order order :orderCache.values()){
            JsonObjectBuilder orderBuilder = Json.createObjectBuilder();

            JsonObject orderJson = orderBuilder
                                            .add("fullname",order.getFull_name())
                                            .add("adress",order.getAdress())
                                            .add("date",order.getDate())
                                            .build();
            arrayOfOrder.add(orderJson);
        }
        JsonObject root = rootBuilder.add("Orders",arrayOfOrder).build();
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

