package Model.DAO;

import Model.Entity.Order.Order;
import Model.Entity.Order.ShippingAddress;
import Model.Entity.ShopItem.ShopItem;
import Model.Entity.User.User;
import Model.Service.OrderService;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO implements OrderService {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public OrderDAO() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }


    @Override
    public Order findActiveOrderByUserId(int userId) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select orders.id, " +
                                "orders.customer_id, " +
                                "orders.order_active, " +
                                "shipping_adresses.zip_code, " +
                                "shipping_adresses.street " +
                         "from orders " +
                         "inner join shipping_adresses ON shipping_adresses.id = orders.shipping_adress_id " +
                         "where orders.customer_id = ? and orders.order_active = true");

        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createActiveOrderFromRow(resultSet);

                } else {
                    //TODO check logic with SP
                    System.out.println("No active order found");
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void completeOrder(Order order) {

    }

    @Override
    public void startNewActiveOrder(User user){
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into orders (customer_id, order_active) values (?, true)");
        ) {
            preparedStatement.setInt(1, user.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 0) {
                System.out.println("WARNING - Unexpected number of rows affected: " + affectedRows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeOrder(Order order) {

    }

    private Order createActiveOrderFromRow(ResultSet resultSet) throws SQLException {

        UserDAO userDAO = new UserDAO();
        ShopItemDAO shopItemDAO = new ShopItemDAO();

        int orderId = resultSet.getInt("id");
        int customerId = resultSet.getInt("customer_id");
        boolean active = resultSet.getBoolean("order_active");

        return new Order(orderId,
                         userDAO.findUserById(customerId),
                         active,
                         shopItemDAO.findByOrderId(orderId));
    }




}
