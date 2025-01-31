package Model.Service;

import Model.Entity.Order.Order;
import Model.Entity.User.User;

public interface OrderService {
    Order findActiveOrderByUserId(User user);
    void completeOrder(Order order);
    void startNewActiveOrder(User user);
    void removeOrder(Order order);
}
