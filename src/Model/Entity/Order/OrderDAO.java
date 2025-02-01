package Model.Entity.Order;

import Model.Entity.User.User;

import java.util.Optional;

public interface OrderDAO {

    Optional<OrderDTO> findActiveOrderDTOByUserId(User user);
    void updateActiveOrder(Order order);
    void createNewActiveOrderForUser(User user);
    void removeOrder(Order order);
}
