package Model.Service;

import Model.Entity.ShopItem.ShopItem;
import Model.Entity.ShopItem.ShopItem2;

import java.util.List;

public interface SearchService {
    List<ShopItem> searchAll();
    List<ShopItem2> searchAll2();
    List<ShopItem> searchByName(String name);
    List<ShopItem> searchBySize(int size);
}
