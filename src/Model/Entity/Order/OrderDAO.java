package Model.Entity.Order;

import Model.Entity.User.User;

public interface OrderDAO {

    OrderDTO findActiveOrderDTOByUserId(User user);
    void updateActiveOrder(Order order);
    void createNewActiveOrderForUser(User user);
    void removeOrder(Order order);
}
