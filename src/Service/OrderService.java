package Service;

import Model.Entity.Order.Order;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.ShopItem.ShopItem;
import Model.Entity.User.User;

public interface OrderService {
    Order setupActiveOrderForUser(User user);
    void completeActiveOrder(Order order, ShippingAddress shippingAddress);
    void addShopItemToOrder(ShopItem shopItem, Order order);
}
