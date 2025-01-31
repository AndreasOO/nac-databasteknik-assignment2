package Model.Entity.ShippingAddress;

import Model.Entity.DAOConfig.ConnectionConfigManager;
import Model.Entity.Order.OrderDTO;

import java.sql.*;

public class ShippingAddressDAOImpl implements ShippingAddressDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public ShippingAddressDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public ShippingAddress findShippingAddressByID(int id) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shipping_adresses.id, " +
                             "shipping_adresses.zip_code, " +
                             "shipping_adresses.street " +
                             "from shipping_adresses " +
                             "where shipping_adresses.id = ?");

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getShippingAddressFromRow(resultSet);

                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShippingAddress findShippingAddressByZipCodeAndStreet(ShippingAddress shippingAddress) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shipping_adresses.id, " +
                             "shipping_adresses.zip_code, " +
                             "shipping_adresses.street " +
                             "from shipping_adresses " +
                             "where shipping_adresses.zip_code = ? and shipping_adresses.street = ?");

        ) {
            preparedStatement.setInt(1, shippingAddress.getZipCode());
            preparedStatement.setString(2, shippingAddress.getStreet());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getShippingAddressFromRow(resultSet);

                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void createShippingAddress(ShippingAddress shippingAddress) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into shipping_adresses (zip_code, street) values (?, ?)");
        ) {
            preparedStatement.setInt(1, shippingAddress.getZipCode());
            preparedStatement.setString(2, shippingAddress.getStreet());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("WARNING - Unexpected number of rows affected: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private ShippingAddress getShippingAddressFromRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("shipping_adresses.id");
        int zipCode = resultSet.getInt("shipping_adresses.zip_code");
        String street = resultSet.getString("shipping_adresses.street");

        return new ShippingAddress(id, street, zipCode);
    }
}
