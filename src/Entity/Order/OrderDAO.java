package Entity.Order;

import Entity.User.User;

import java.sql.SQLException;
import java.util.Optional;

public interface OrderDAO {

    Optional<OrderDTO> findActiveOrderDTOByUserId(User user);
    void updateActiveOrder(Order order);
    void insertNewActiveOrderForUser(User user);
    void removeOrder(Order order) throws SQLException;
    void executeStoredProcedureAddToCart(int customerId, int orderId, int shopItemId) throws SQLException;
}
