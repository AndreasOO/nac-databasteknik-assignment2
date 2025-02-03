package Model.Entity.Brand;

import Configuration.DAOConfig.ConnectionConfigManager;

import java.sql.*;
import java.util.Optional;

public class BrandDAOImpl implements BrandDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public BrandDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public Optional<Brand> findBrandByID(int id) {
        Optional<Brand> brandOptional = Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select brands.id, " +
                             "brands.name " +
                             "from brands " +
                             "where brands.id = ?");

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getBrandFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandOptional;
    }





    private Brand getBrandFromRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("brands.id");
        String name = resultSet.getString("brands.name");

        return new Brand(id, name);
    }
}
