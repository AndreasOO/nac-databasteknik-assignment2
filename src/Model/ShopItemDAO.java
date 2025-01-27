package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShopItemDAO {
    private final Map<Integer, ShopItem> shopItems;

    public ShopItemDAO() {
        shopItems = initializeMockDatabase();
    }

    public Map<Integer, ShopItem> initializeMockDatabase() {
        return DataLoaderShopItem.loadData().stream().collect(Collectors.toMap(ShopItem::getId, Function.identity()));
    }

    public Map<Integer, ShopItem> getShopItems() {
        return shopItems;
    }

    public List<ShopItem> findAll() {
        return shopItems.values().stream().toList();
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
