package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShopItemDAO {
    private final Map<Integer, ShopItem> shopItems;
    private String datasourceURL;
    private String username;
    private String password;

    public ShopItemDAO() {
        datasourceURL = "jdbc:mysql://localhost:3306/shop_db?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        username = "shopadmin";
        password = "test1234";
        shopItems = initializeMockDatabase();
    }

    public Map<Integer, ShopItem> initializeMockDatabase() {
        return DataLoaderShopItem.loadData().stream().collect(Collectors.toMap(ShopItem::getId, Function.identity()));
    }

    public Map<Integer, ShopItem> getShopItems() {
        return shopItems;
    }

    public List<ShopItem> findAll() {

        List<ShopItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(datasourceURL, username, password);

             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select shop_items.id, products.name, brands.name, specifications.size, group_concat(categories.name separator ',') as category_list , products.price,  shop_items.quantity from shop_items " +
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
                int id = resultSet.getInt("shop_items.id");
                String productName = resultSet.getString("products.name");
                String brandName = resultSet.getString("brands.name");
                int size = resultSet.getInt("specifications.size");
                String categoryName = resultSet.getString("category_list");
                int price = resultSet.getInt("products.price");
                int quantity = resultSet.getInt("shop_items.quantity");


                ShopItem shopItem = new ShopItem(id, productName, brandName, size, Arrays.stream(categoryName.split(",")).map(this::categoryMatcher).collect(Collectors.toList()), price, quantity);
                items.add(shopItem);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Category categoryMatcher(String categoryString) {
        return Arrays.stream(Category.values()).filter(cat -> cat.getDisplayName().equalsIgnoreCase(categoryString)).findFirst().orElse(null);
    }

    public List<ShopItem> findByName(String searchInput) {
        return shopItems.values().stream().filter(x -> x.getName().contains(searchInput.trim())).collect(Collectors.toList());
    }

    public List<ShopItem> findById(int id) {
        List<ShopItem> result =  new ArrayList<>();

        if (shopItems.containsKey(id)) {
            result.add(shopItems.get(id));
        }
        return result;
    }
}
