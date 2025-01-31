package Model.Service;

import Model.Entity.ShopItem.ShopItem;

import java.util.List;

public interface SearchService {
    List<ShopItem> searchAll();
    List<ShopItem> searchByName(String name);
    List<ShopItem> searchBySize(int size);
}
