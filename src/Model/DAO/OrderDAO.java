package Model.DAO;

import Model.Entity.Order.Order;
import Model.Entity.ShopItem.ShopItem;
import Model.Service.OrderService;

import java.sql.*;

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
                             "zip_code.shipping_adresses, " +
                             //TODO add rest of columns and joins
                             "from orders " +
                             "inner join shipping_adresses ON shipping_adresses.id = orders.shipping_adresses_id " +
                             "where customers.id = ? and orders.active = true");
        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    //TODO Replace with factory method
                    int orderId = resultSet.getInt("id");
                } else {
                    //TODO check logic with SP
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
}
