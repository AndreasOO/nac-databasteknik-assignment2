package Model.Entity.Product;

import Model.Entity.DAOConfig.ConnectionConfigManager;

import java.sql.*;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public ProductDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public Optional<ProductDTO> findProductDTOByID(int id) {
        Optional<ProductDTO> productDTOOptional = Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select products.id, " +
                             "products.name, " +
                             "products.brand_id, " +
                             "products.product_type_id, " +
                             "products.price " +
                             "from products " +
                             "where products.id = ?");

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getProductDTOFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDTOOptional;
    }





    private ProductDTO getProductDTOFromRow(ResultSet resultSet) throws SQLException {


        int id = resultSet.getInt("products.id");
        String name = resultSet.getString("products.name");
        int price = resultSet.getInt("products.price");
        int brandId = resultSet.getInt("products.brand_id");
        int productTypeId = resultSet.getInt("products.product_type_id");

        return new ProductDTO(id, name, price, brandId, productTypeId);
    }
}
