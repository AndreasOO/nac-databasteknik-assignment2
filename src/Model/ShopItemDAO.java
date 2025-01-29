package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShopItemDAO {

    private final String datasourceURL;
    private final String username;
    private final String password;

    public ShopItemDAO() {
        datasourceURL = "jdbc:mysql://localhost:3306/shop_db?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        username = "shopadmin";
        password = "test1234";

    }

    public List<ShopItem> findAll() {

        List<ShopItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(datasourceURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                                "products.name, " +
                                "brands.name, " +
                                "specifications.size, " +
                                "group_concat(categories.name separator ',') as category_list , " +
                                "products.price,  " +
                                "shop_items.quantity " +
                             "from shop_items " +
                             "inner join products ON products.id = shop_items.product_id " +
                             "inner join specifications ON specifications.id = shop_items.specification_id " +
                             "inner join brands ON brands.id = products.brand_id " +
                             "inner join products_categories ON products_categories.product_id = products.id " +
                             "inner join categories ON categories.id = products_categories.category_id " +
                             "group by shop_items.id");
             ResultSet resultSet = preparedStatement.executeQuery()
             )
        {
            while (resultSet.next()) {
                ShopItem shopItem = createShopItemFromResultRow(resultSet);
                items.add(shopItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ShopItem> findByName(String searchInput) {
        List<ShopItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(datasourceURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                                "products.name, " +
                                "brands.name, " +
                                "specifications.size, " +
                                "group_concat(categories.name separator ',') as category_list , " +
                                "products.price,  " +
                                "shop_items.quantity " +
                             "from shop_items " +
                             "inner join products ON products.id = shop_items.product_id " +
                             "inner join specifications ON specifications.id = shop_items.specification_id " +
                             "inner join brands ON brands.id = products.brand_id " +
                             "inner join products_categories ON products_categories.product_id = products.id " +
                             "inner join categories ON categories.id = products_categories.category_id " +
                             "where products.name like ?" +
                             "group by shop_items.id")
        ) {
            preparedStatement.setString(1, "%"+searchInput+"%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ShopItem shopItem = createShopItemFromResultRow(resultSet);
                    items.add(shopItem);
                }
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ShopItem> findById(int id) {
        List<ShopItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(datasourceURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                                "products.name, " +
                                "brands.name, " +
                                "specifications.size, " +
                                "group_concat(categories.name separator ',') as category_list , " +
                                "products.price,  " +
                                "shop_items.quantity " +
                             "from shop_items " +
                             "inner join products ON products.id = shop_items.product_id " +
                             "inner join specifications ON specifications.id = shop_items.specification_id " +
                             "inner join brands ON brands.id = products.brand_id " +
                             "inner join products_categories ON products_categories.product_id = products.id " +
                             "inner join categories ON categories.id = products_categories.category_id " +
                             "where shop_items.id = ? " +
                             "group by shop_items.id")
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ShopItem shopItem = createShopItemFromResultRow(resultSet);
                    items.add(shopItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ShopItem> findBySize(int size) {
        List<ShopItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(datasourceURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, " +
                             "products.name, " +
                             "brands.name, " +
                             "specifications.size, " +
                             "group_concat(categories.name separator ',') as category_list , " +
                             "products.price,  " +
                             "shop_items.quantity " +
                             "from shop_items " +
                             "inner join products ON products.id = shop_items.product_id " +
                             "inner join specifications ON specifications.id = shop_items.specification_id " +
                             "inner join brands ON brands.id = products.brand_id " +
                             "inner join products_categories ON products_categories.product_id = products.id " +
                             "inner join categories ON categories.id = products_categories.category_id " +
                             "where specifications.size = ? " +
                             "group by shop_items.id")
        ) {
            preparedStatement.setInt(1, size);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ShopItem shopItem = createShopItemFromResultRow(resultSet);
                    items.add(shopItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private ShopItem createShopItemFromResultRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("shop_items.id");
        String productName = resultSet.getString("products.name");
        String brandName = resultSet.getString("brands.name");
        int size = resultSet.getInt("specifications.size");
        String categoryConcatStr = resultSet.getString("category_list");
        int price = resultSet.getInt("products.price");
        int quantity = resultSet.getInt("shop_items.quantity");


        ShopItem shopItem = new ShopItem(id,
                productName,
                brandName,
                size,
                Arrays.stream(categoryConcatStr.split(",")).map(this::categoryStrToEnumMatcher).collect(Collectors.toList()),
                price,
                quantity);
        return shopItem;
    }

    private Category categoryStrToEnumMatcher(String categoryString) {
        return Arrays.stream(Category.values()).filter(cat -> cat.getDisplayName().equalsIgnoreCase(categoryString)).findFirst().orElse(null);
    }
}
