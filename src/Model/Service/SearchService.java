package Model.Service;

import Model.Entity.ShopItem.ShopItem;
import Model.Entity.ShopItem.ShopItem2;

import java.util.List;

public interface SearchService {
    List<ShopItem2> searchAll2();
    List<ShopItem2> searchByName2(String name);
    List<ShopItem2> searchBySize2(int size);
    List<ShopItem> searchAll();
    List<ShopItem> searchByName(String name);
    List<ShopItem> searchBySize(int size);
}
