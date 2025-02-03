package Service;

import Model.Entity.ShopItem.ShopItem;

import java.util.List;

public interface SearchService {
    List<ShopItem> searchAll2();
    List<ShopItem> searchByName2(String name);
    List<ShopItem> searchBySize2(int size);

}
