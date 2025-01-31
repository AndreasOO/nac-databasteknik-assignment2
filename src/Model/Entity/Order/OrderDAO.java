package Model.Entity.Order;

import Model.Entity.User.User;

public interface OrderDAO {
    Order findActiveOrderByUserId(User user);
    void updateActiveOrder(Order order);
    void CreateNewActiveOrder(User user);
    void removeOrder(Order order);
}
