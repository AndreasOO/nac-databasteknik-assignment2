package Security;

import java.sql.*;

public enum UserAuthenticator {
    INSTANCE;

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    UserAuthenticator() {
        datasourceURL = "jdbc:mysql://localhost:3306/shop_db?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        datasourceUsername = "shopadmin";
        datasourcePassword = "test1234";
    }

    public static UserAuthenticator getInstance() {
        return INSTANCE;
    }


    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        System.out.println("Authenticating user " + username + " with password " + password);
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select customers.id " +
                             "from customers " +
                             "inner join passwords ON passwords.user_id = customers.id " +
                             "where customers.email = ? and password = ?"
                             )
        ) {
            System.out.println(preparedStatement.toString());
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("test2");
                if (resultSet.next()) {

                    authenticated = true;
                    System.out.println(authenticated);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authenticated;
    }
}
