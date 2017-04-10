package ru.amerain.servlets;



import ru.amerain.jdbc.WorkDatabase;
import ru.amerain.jdbc.WorkDatabaseBuilder;
import ru.amerain.models.Client;
import ru.amerain.models.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by User on 04.04.2017.
 */
@WebServlet("/add/order")
public class OrderCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client client = new Client
                (request.getParameter("full_name"), request.getParameter("phone_number"));
        Order order = new Order
                (client, request.getParameter("client_place"), request.getParameter("date"),
                        request.getParameterValues("product"), request.getParameterValues("count"));
        WorkDatabaseBuilder workDatabaseBuilder = new WorkDatabaseBuilder();
        WorkDatabase database = workDatabaseBuilder.getDatabase();
        database.setOrder(order,client);
    }
}
