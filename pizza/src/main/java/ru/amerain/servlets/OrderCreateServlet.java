package ru.amerain.servlets;

import ru.amerain.models.Order;
import ru.amerain.store.OrderCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 04.04.2017.
 */
@WebServlet("/add/order")
public class OrderCreateServlet extends HttpServlet {
    private final AtomicInteger ID = new AtomicInteger();
    private final OrderCache orderCache = OrderCache.getInstance();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
     this.orderCache.add(new Order
             (ID.incrementAndGet(),
                     request.getParameter("full_name"),
              request.getParameter("client_place"),request.getParameter("date")));
    }
}
