package Model.Entity.Order;

import Model.Entity.DAOConfig.ConnectionConfigManager;
import Model.Entity.ShopItem.ShopItemDAOImpl;
import Model.Entity.User.UserDAOImpl;
import Model.Entity.User.User;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public OrderDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }

    @Override
    public OrderDTO findActiveOrderDTOByUserId(User user) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select orders.id, " +
                             "orders.customer_id, " +
                             "orders.shipping_adress_id, " +
                             "orders.order_active " +
                             "from orders " +
                             "where orders.customer_id = ? and orders.order_active = 1");

        ) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getActiveOrderDTOFromRow(resultSet);

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
    public void updateActiveOrder(Order order) {
        System.out.println("Updating active order" + order);
        System.out.println("Shipping id: " + order.getShippingAddress().getId());
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update orders set order_date = ?, shipping_adress_id = ?, order_active = ? " +
                             "where id = ?");
        ) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
            preparedStatement.setInt(2,order.getShippingAddress().getId());
            preparedStatement.setBoolean(3,order.isActive());
            preparedStatement.setInt(4,order.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 1) {
                System.out.println("WARNING - Unexpected number of rows affected: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewActiveOrderForUser(User user){
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into orders (customer_id, order_active) values (?, true)");
        ) {
            preparedStatement.setInt(1, user.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("WARNING - Unexpected number of rows affected: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeOrder(Order order) {

    }


    private OrderDTO getActiveOrderDTOFromRow(ResultSet resultSet) throws SQLException {

        int orderId = resultSet.getInt("id");
        int customerId = resultSet.getInt("customer_id");
        int shippingAdressId = resultSet.getInt("shipping_adress_id");
        boolean active = resultSet.getBoolean("order_active");

        return new OrderDTO(orderId,
                            customerId,
                            shippingAdressId,
                            active
        );
    }






}
