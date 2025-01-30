package Model.DAO;

import Model.Entity.User;
import Model.Service.UserService;

import java.sql.*;

public class UserDAO implements UserService {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public UserDAO() {
        datasourceURL = "jdbc:mysql://localhost:3306/shop_db?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        datasourceUsername = "shopadmin";
        datasourcePassword = "test1234";
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
}
