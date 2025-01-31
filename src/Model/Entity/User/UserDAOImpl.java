package Model.Entity.User;

import Model.Entity.DAOConfig.ConnectionConfigManager;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public UserDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }
    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select customers.id, customers.email, customers.name " +
                             "from customers " +
                             "where customers.email = ?"
             )
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return  new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"));
                } else {
                    throw new SQLException("User not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select customers.id, customers.email, customers.name " +
                             "from customers " +
                             "where customers.id = ?"
             )
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return  new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"));
                } else {
                    throw new SQLException("User not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
