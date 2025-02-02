package Model.Entity.Category;

import Model.Entity.DAOConfig.ConnectionConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public CategoryDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public Optional<List<Category>> findCategoryByProductID(int productId) {
        Optional<List<Category>> categoryOptional = Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select categories.id, " +
                             "categories.name " +
                             "from products_categories " +
                             "inner join categories on categories.id = products_categories.category_id " +
                             "where products_categories.product_id = ?");

        ) {

            preparedStatement.setInt(1, productId);
            List<Category> categories = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(getCategoryFromRow(resultSet));
                }
                if (categories.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(categories);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryOptional;
    }





    private Category getCategoryFromRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("categories.id");
        String name = resultSet.getString("categories.name");

        return new Category(id, name);
    }
}
