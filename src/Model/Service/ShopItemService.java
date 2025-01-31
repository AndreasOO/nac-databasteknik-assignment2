package Model.Service;

import Model.Entity.ShopItem.ShopItem;

import java.util.List;

public interface ShopItemService {
    List<ShopItem> findAll();
    List<ShopItem> findByName(String searchInput);
    List<ShopItem> findById(int id);
    List<ShopItem> findBySize(int size);
    List<ShopItem> findByOrderId(int orderId);
}
