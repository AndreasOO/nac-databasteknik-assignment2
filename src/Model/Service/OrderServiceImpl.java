package Model.Service;

import Model.Entity.Order.Order;
import Model.Entity.Order.OrderDAO;
import Model.Entity.Order.OrderDAOImpl;
import Model.Entity.Order.OrderDTO;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.User.User;
import Model.Entity.User.UserDAO;
import Model.Entity.User.UserDAOImpl;

public class OrderServiceImpl implements OrderService {
    ShopItemDAO shopItemDAO;
    OrderDAO orderDAO;
    UserDAO userDAO;

    public OrderServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
        orderDAO = new OrderDAOImpl();
        userDAO = new UserDAOImpl();
    }




    @Override
    public Order setupActiveOrderForUser(User user) {
        OrderDTO orderDTO = orderDAO.findActiveOrderDTOByUserId(user);


        if (orderDTO == null) {
            orderDAO.createNewActiveOrder(user);
            orderDTO = orderDAO.findActiveOrderDTOByUserId(user);
            return createOrderFromDTO(orderDTO);

        } else {
            return createOrderFromDTO(orderDTO);
        }
    }

    private Order createOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCustomer(userDAO.findUserById(orderDTO.getCustomerId()));
        order.setActive(orderDTO.isActive());
        order.setOrderItems(shopItemDAO.findByOrderId(orderDTO.getId()));
        return order;
    }
}
