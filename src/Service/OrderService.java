package Service;

import Entity.Order.Order;
import Entity.ShippingAddress.ShippingAddress;
import Entity.ShopItem.ShopItem;
import Entity.User.User;

import java.sql.SQLException;

public interface OrderService {
    Order setupAndGetActiveOrderForUser(User user);
    void completeActiveOrder(Order order, ShippingAddress shippingAddress);
    void addShopItemToOrder(ShopItem shopItem, Order order) throws SQLException;
    void removeActiveOrder(Order order) throws SQLException;
}
