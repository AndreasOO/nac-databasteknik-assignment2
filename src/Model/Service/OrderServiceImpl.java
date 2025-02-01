package Model.Service;

import Model.Entity.Order.*;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.ShippingAddress.ShippingAddressDAO;
import Model.Entity.ShippingAddress.ShippingAddressDAOImpl;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.User.User;
import Model.Entity.User.UserDAO;
import Model.Entity.User.UserDAOImpl;

import java.time.LocalDate;

public class OrderServiceImpl implements OrderService {
    ShopItemDAO shopItemDAO;
    OrderDAO orderDAO;
    UserDAO userDAO;
    ShippingAddressDAO shippingAddressDAO;

    public OrderServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
        orderDAO = new OrderDAOImpl();
        userDAO = new UserDAOImpl();
        shippingAddressDAO = new ShippingAddressDAOImpl();
    }


    @Override
    public void completeActiveOrder(Order order, ShippingAddress shippingAddress) {
        if (shippingAddressDAO.findShippingAddressByZipCodeAndStreet(shippingAddress).isEmpty()) {
            shippingAddressDAO.createShippingAddress(shippingAddress);
        }
        order.setShippingAddress(shippingAddressDAO.findShippingAddressByZipCodeAndStreet(shippingAddress).get());
        order.setOrderDate(LocalDate.now());
        order.setActive(false);

        orderDAO.updateActiveOrder(order);
        //TODO FIX SHIP ADR ENTITY AND DAO, CREATE AND SET TO CURRENT ORDER

        //TODO FIX ORDER ITEMS ENTITY AND SAVE THESE WHEN COMPLETING ORDER
    }



    @Override
    public Order setupActiveOrderForUser(User user) {
        OrderDTO orderDTO = orderDAO.findActiveOrderDTOByUserId(user);

        if (orderDTO == null) {
            orderDAO.createNewActiveOrderForUser(user);
            orderDTO = orderDAO.findActiveOrderDTOByUserId(user);
            return createActiveOrderFromDTO(orderDTO);

        } else {
            return createActiveOrderFromDTO(orderDTO);
        }
    }

    private Order createActiveOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCustomer(userDAO.findUserById(orderDTO.getCustomerId()));
        order.setActive(orderDTO.isActive());
        //TODO FIX SHOP ITEM != ORDER ITEM
        order.setOrderItems(shopItemDAO.findByOrderId(orderDTO.getId()));
        return order;
    }
}
