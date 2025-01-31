package Model.Entity.Order;

import Model.Entity.User.User;

public interface OrderDAO {
    Order findActiveOrderByUserId(User user);
    OrderDTO findActiveOrderDTOByUserId(User user);
    void updateActiveOrder(Order order);
    void createNewActiveOrder(User user);
    void removeOrder(Order order);
}
