package Model.Entity.Order;

import Configuration.DAOConfig.ConnectionConfigManager;
import Model.Entity.User.User;

import java.sql.*;
import java.util.Optional;

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
    public Optional<OrderDTO> findActiveOrderDTOByUserId(User user) {
        Optional<OrderDTO> orderDTOOptional= Optional.empty();
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
                    return Optional.of(getActiveOrderDTOFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDTOOptional;
    }


    @Override
    public void updateActiveOrder(Order order) {
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
    public void insertNewActiveOrderForUser(User user){
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
    //TODO REMOVE ORDER ONLY AND ORDER ITEMS SHOULD BE DELETED ON CASCADE
    public void removeOrder(Order order) {

    }

    @Override
    public void executeStoredProcedureAddToCart(int customerId, int productId, int shopItemId) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             CallableStatement callableStatement = connection.prepareCall(
                     "addItemToCart(?, ?, ?)")
        ) {
            callableStatement.setInt(1, customerId);
            callableStatement.setInt(2, productId);
            callableStatement.setInt(3, shopItemId);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
