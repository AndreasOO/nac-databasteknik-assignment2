package Service;

import Model.Entity.Order.Order;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.ShopItem.ShopItem;
import Model.Entity.User.User;

import java.sql.SQLException;

public interface OrderService {
    Order setupAndGetActiveOrderForUser(User user);
    void completeActiveOrder(Order order, ShippingAddress shippingAddress);
    void addShopItemToOrder(ShopItem shopItem, Order order) throws SQLException;
    void removeActiveOrder(Order order) throws SQLException;
}
