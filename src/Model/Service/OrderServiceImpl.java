package Model.Service;

import Model.Entity.Order.Order;
import Model.Entity.Order.OrderDAO;
import Model.Entity.Order.OrderDAOImpl;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.User.User;

public class OrderServiceImpl implements OrderService {
    ShopItemDAO shopItemDAO;
    OrderDAO orderDAO;

    public OrderServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
        orderDAO = new OrderDAOImpl();
    }




    @Override
    public Order setupActiveOrderForUser(User user) {
        Order order = orderDAO.findActiveOrderByUserId(user);
        if (order == null) {
            //TODO create new order method in dao
            orderDAO.createNewActiveOrder(user);
            order = orderDAO.findActiveOrderByUserId(user);
        }
        return order;
    }
}
