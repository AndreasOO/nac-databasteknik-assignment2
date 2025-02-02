package Model.Entity.ProductType;

import Configuration.DAOConfig.ConnectionConfigManager;

import java.sql.*;
import java.util.Optional;

public class ProductTypeDAOImpl implements ProductTypeDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public ProductTypeDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public Optional<ProductType> findProductTypeByID(int id) {
        Optional<ProductType> productTypeOptional = Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select product_types.id, " +
                             "product_types.name " +
                             "from product_types " +
                             "where product_types.id = ?");

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getProductTypeFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypeOptional;
    }





    private ProductType getProductTypeFromRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("product_types.id");
        String name = resultSet.getString("product_types.name");

        return new ProductType(id, name);
    }
}
