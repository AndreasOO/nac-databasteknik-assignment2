package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoaderShopItem {

    protected static List<ShopItem> loadData() {
        List<ShopItem> list = new ArrayList<>();
        list.add(new ShopItem(1, "Lazy Sunday", "Ecco", 38, Arrays.asList(Category.WALKING_SHOES, Category.RUNNING_SHOES), 500, 100));
        list.add(new ShopItem(2, "Crazy Feet", "Nike", 39, Arrays.asList(Category.LADIES_SHOES, Category.RUNNING_SHOES), 800, 100));
        list.add(new ShopItem(3, "Goblin Mode", "Puma", 40, Arrays.asList(Category.MENS_SHOES, Category.SLIM_SHOES), 900, 100));
        list.add(new ShopItem(4, "Trackmaster", "Nike", 41, Arrays.asList(Category.SANDALS), 2700, 100));
        list.add(new ShopItem(5, "Bolt Runner", "Acne", 37, Arrays.asList(Category.LADIES_SHOES, Category.SANDALS), 500, 100));
        list.add(new ShopItem(6, "Little Feet", "Puma", 39, Arrays.asList(Category.RUNNING_SHOES), 860, 100));
        list.add(new ShopItem(7, "Blaster", "Ecco", 42, Arrays.asList(Category.WALKING_SHOES), 1000, 100));
        list.add(new ShopItem(8, "Heavies", "Nike", 41, Arrays.asList(Category.SLIM_SHOES), 350, 100));
        list.add(new ShopItem(9, "Scooter", "Acne", 38, Arrays.asList(Category.MENS_SHOES), 450, 100));

        return list;
    }
}
