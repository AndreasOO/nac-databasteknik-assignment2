package Model.Service;

import Model.Entity.Order.Order;

public interface OrderService {
    Order findActiveOrderByUserId(int userId);
    void completeOrder(Order order);
}
