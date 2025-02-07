package Entity.Specification;

import Configuration.ConnectionConfigManager;

import java.sql.*;
import java.util.Optional;

public class SpecificationDAOImpl implements SpecificationDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public SpecificationDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }



    @Override
    public Optional<Specification> findSpecificationByID(int id) {
        Optional<Specification> specificationOptional = Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select specifications.id, " +
                             "specifications.color, " +
                             "specifications.size " +
                             "from specifications " +
                             "where specifications.id = ?");

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getSpecificationFromRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificationOptional;
    }





    private Specification getSpecificationFromRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("specifications.id");
        String color = resultSet.getString("specifications.color");
        int size = resultSet.getInt("specifications.size");

        return new Specification(id, color, size);
    }
}
