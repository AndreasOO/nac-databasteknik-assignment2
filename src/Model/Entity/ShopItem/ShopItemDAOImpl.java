package Model.Entity.ShopItem;

import Configuration.ConnectionConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopItemDAOImpl implements ShopItemDAO {

    private final String datasourceURL;
    private final String datasourceUsername;
    private final String datasourcePassword;

    public ShopItemDAOImpl() {
        datasourceURL = ConnectionConfigManager.getInstance().getDatasourceURL();
        datasourceUsername = ConnectionConfigManager.getInstance().getDatasourceUsername();
        datasourcePassword = ConnectionConfigManager.getInstance().getDatasourcePassword();
    }


    @Override
    public Optional<List<ShopItemDTO>> findAllDTO() {
        Optional<List<ShopItemDTO>> shopItemDTOsOptional= Optional.empty();
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "shop_items.product_id, " +
                             "shop_items.specification_id, " +
                             "shop_items.quantity " +
                             "from shop_items"
             );
             ResultSet resultSet = preparedStatement.executeQuery()
        )
        {
            List<ShopItemDTO> shopItemDTOs = new ArrayList<>();
            while (resultSet.next()) {
                shopItemDTOs.add(getShopItemDTOFromResultRow(resultSet));
            }
            if (shopItemDTOs.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(shopItemDTOs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemDTOsOptional;
    }

    @Override
    public Optional<List<ShopItemDTO>> findByNameDTO(String searchInput) {
        Optional<List<ShopItemDTO>> shopItemDTOsOptional = Optional.empty();

        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "shop_items.product_id, " +
                             "shop_items.specification_id, " +
                             "shop_items.quantity " +
                             "from shop_items " +
                             "inner join products on products.id = shop_items.product_id " +
                             "where products.name like ?")
        ) {
            preparedStatement.setString(1, "%"+searchInput+"%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ShopItemDTO> shopItemDTOs = new ArrayList<>();
                while (resultSet.next()) {
                    shopItemDTOs.add(getShopItemDTOFromResultRow(resultSet));
                }
                if (shopItemDTOs.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(shopItemDTOs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemDTOsOptional;
    }

    @Override
    public Optional<List<ShopItemDTO>> findByIdDTO(int id) {
        Optional<List<ShopItemDTO>> shopItemDTOsOptional = Optional.empty();

        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "shop_items.product_id, " +
                             "shop_items.specification_id, " +
                             "shop_items.quantity " +
                             "from shop_items " +
                             "where shop_items.id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ShopItemDTO> shopItemDTOs = new ArrayList<>();
                while (resultSet.next()) {
                    shopItemDTOs.add(getShopItemDTOFromResultRow(resultSet));
                }
                if (shopItemDTOs.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(shopItemDTOs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemDTOsOptional;
    }

    @Override
    public Optional<List<ShopItemDTO>> findBySizeDTO(int size) {
        Optional<List<ShopItemDTO>> shopItemDTOsOptional = Optional.empty();

        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "shop_items.product_id, " +
                             "shop_items.specification_id, " +
                             "shop_items.quantity " +
                             "from shop_items " +
                             "inner join specifications on specifications.id = shop_items.specification_id " +
                             "where specifications.size = ?")
        ) {
            preparedStatement.setInt(1, size);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ShopItemDTO> shopItemDTOs = new ArrayList<>();
                while (resultSet.next()) {
                    shopItemDTOs.add(getShopItemDTOFromResultRow(resultSet));
                }
                if (shopItemDTOs.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(shopItemDTOs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemDTOsOptional;
    }

    @Override
    public Optional<List<ShopItemDTO>> findByOrderIdAllDTO(int orderId) {
        Optional<List<ShopItemDTO>> shopItemDTOsOptional = Optional.empty();

        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "shop_items.product_id, " +
                             "shop_items.specification_id, " +
                             "shop_items.quantity " +
                             "from shop_items " +
                             "inner join order_items on order_items.shop_item_id = shop_items.id " +
                             "where order_items.order_id = ?")
        ) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ShopItemDTO> shopItemDTOs = new ArrayList<>();
                while (resultSet.next()) {
                    shopItemDTOs.add(getShopItemDTOFromResultRow(resultSet));
                }
                if (shopItemDTOs.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(shopItemDTOs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemDTOsOptional;
    }

    @Override
    public void incrementQuantityOfShopItem(ShopItem shopItem) throws SQLException {
        try (Connection connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update shop_items set quantity = quantity + 1 where id = ? "
             )) {
            preparedStatement.setInt(1, shopItem.getId());
            preparedStatement.executeUpdate();
        }
    }

    private ShopItemDTO getShopItemDTOFromResultRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("shop_items.id");
        int productId = resultSet.getInt("shop_items.product_id");
        int specificationId = resultSet.getInt("shop_items.specification_id");
        int quantity = resultSet.getInt("shop_items.quantity");


        return new ShopItemDTO(id, productId, specificationId, quantity);
    }
}
