package Service;

import Model.Entity.Order.Order;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.User.User;

public interface OrderService {
    Order setupActiveOrderForUser(User user);
    void completeActiveOrder(Order order, ShippingAddress shippingAddress);
}
