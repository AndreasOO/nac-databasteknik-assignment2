package Model.Entity.ShippingAddress;

import Configuration.ConnectionConfigManager;

import java.sql.*;
import java.util.Optional;

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
    public Optional<ShippingAddress> findShippingAddressByID(int id) {
        Optional<ShippingAddress> shippingAddressOptional = Optional.empty();
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
                    return Optional.of(getShippingAddressFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingAddressOptional;
    }

    @Override
    public Optional<ShippingAddress> findShippingAddressByZipCodeAndStreet(ShippingAddress shippingAddress) {
        Optional<ShippingAddress> shippingAddressOptional = Optional.empty();
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
                    return Optional.of(getShippingAddressFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingAddressOptional;
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
