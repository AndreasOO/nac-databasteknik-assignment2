package Model.Service;

import Model.Entity.Order.Order;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.User.User;

public interface OrderService {
    Order setupActiveOrderForUser(User user);
}
