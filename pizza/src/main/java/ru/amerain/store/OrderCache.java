package ru.amerain.store;

import ru.amerain.models.Order;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by User on 04.04.2017.
 */
public class OrderCache {
    private  static final OrderCache INSTANCE = new OrderCache();

    private final ConcurrentHashMap<Integer,Order> orders = new ConcurrentHashMap<Integer, Order>();

    public static OrderCache getInstance()
    {
        return INSTANCE;
    }
    public Collection<Order> values(){
        return orders.values();
    }
    public void add(final Order order){
        orders.put(order.getId(),order);
    }
    public Order get(int id){
        return orders.get(id);
    }
}
