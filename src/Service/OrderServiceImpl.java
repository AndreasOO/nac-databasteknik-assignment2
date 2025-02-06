package Service;

import Model.Entity.Brand.BrandDAO;
import Model.Entity.Brand.BrandDAOImpl;
import Model.Entity.Category.CategoryDAO;
import Model.Entity.Category.CategoryDAOImpl;
import Model.Entity.Order.*;
import Model.Entity.Product.Product;
import Model.Entity.Product.ProductDAO;
import Model.Entity.Product.ProductDAOImpl;
import Model.Entity.Product.ProductDTO;
import Model.Entity.ProductType.ProductTypeDAO;
import Model.Entity.ProductType.ProductTypeDAOImpl;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.ShippingAddress.ShippingAddressDAO;
import Model.Entity.ShippingAddress.ShippingAddressDAOImpl;
import Model.Entity.ShopItem.ShopItem;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.ShopItem.ShopItemDTO;
import Model.Entity.Specification.SpecificationDAO;
import Model.Entity.Specification.SpecificationDAOImpl;
import Model.Entity.User.User;
import Model.Entity.User.UserDAO;
import Model.Entity.User.UserDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    ShopItemDAO shopItemDAO;
    OrderDAO orderDAO;
    UserDAO userDAO;
    ShippingAddressDAO shippingAddressDAO;
    SpecificationDAO specificationDAO;
    BrandDAO brandDAO;
    ProductDAO productDAO;
    ProductTypeDAO productTypeDAO;
    CategoryDAO categoryDAO;

    public OrderServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
        orderDAO = new OrderDAOImpl();
        userDAO = new UserDAOImpl();
        shippingAddressDAO = new ShippingAddressDAOImpl();
        specificationDAO = new SpecificationDAOImpl();
        brandDAO = new BrandDAOImpl();
        productDAO = new ProductDAOImpl();
        productTypeDAO = new ProductTypeDAOImpl();
        categoryDAO = new CategoryDAOImpl();
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
    }



    @Override
    public Order setupAndGetActiveOrderForUser(User user) {
        Optional<OrderDTO> orderDTOOptional = orderDAO.findActiveOrderDTOByUserId(user);

        if (orderDTOOptional.isEmpty()) {
            orderDAO.insertNewActiveOrderForUser(user);
            OrderDTO orderDTO = orderDAO.findActiveOrderDTOByUserId(user).orElseThrow();
            return createActiveOrderFromDTO(orderDTO);

        } else {
            return createActiveOrderFromDTO(orderDTOOptional.get());
        }
    }

    @Override
    public void addShopItemToOrder(ShopItem shopItem, Order order) throws SQLException {
            orderDAO.executeStoredProcedureAddToCart(order.getCustomer().getId(), order.getId(), shopItem.getId());
    }

    @Override
    public void removeActiveOrder(Order order) throws SQLException {
        orderDAO.removeOrder(order);
        repopulateShopItemQuantityOfRemovedOrderItems(order);
    }

    private void repopulateShopItemQuantityOfRemovedOrderItems(Order removedOrder) throws SQLException {
        for (ShopItem removedShopItem : removedOrder.getOrderItems()) {
            shopItemDAO.incrementQuantityOfShopItem(removedShopItem);
        }
    }



    private Order createActiveOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCustomer(userDAO.findUserById(orderDTO.getCustomerId()).orElseThrow());
        order.setActive(orderDTO.isActive());
        order.setOrderItems(createShopItemListFromOrderId(orderDTO.getId()));
        return order;
    }


    private List<ShopItem> createShopItemListFromOrderId(int orderId) {
        Optional<List<ShopItemDTO>> shopItemListOptional = shopItemDAO.findByOrderIdAllDTO(orderId);
        if (shopItemListOptional.isEmpty()) {
            return new ArrayList<>();
        } else {
            return shopItemListOptional
                    .get()
                    .stream()
                    .map(shopItemDTO -> new ShopItem(
                                                                shopItemDTO.getId(),
                                                                createProductFromId(shopItemDTO.getProductId()),
                                                                specificationDAO.findSpecificationByID(shopItemDTO.getSpecificationId()).orElseThrow(),
                                                                shopItemDTO.getQuantity()
                    ))
                    .toList();
        }
    }

    private Product createProductFromId(int productId) {
        ProductDTO productDTO = productDAO.findProductDTOByID(productId).orElseThrow();
        return new Product(productDTO.getId(),
                productDTO.getName(),
                productDTO.getPrice(),
                brandDAO.findBrandByID(productDTO.getBrandId()).orElseThrow(),
                productTypeDAO.findProductTypeByID(productDTO.getProductTypeId()).orElseThrow(),
                categoryDAO.findCategoryByProductID(productDTO.getId()).orElseThrow());
    }
}
