package Model.Entity.ShopItem;

import java.util.List;

public interface ShopItemDAO {
    List<ShopItem> findAll();
    List<ShopItem> findByName(String searchInput);
    List<ShopItem> findById(int id);
    List<ShopItem> findBySize(int size);
    List<ShopItem> findByOrderId(int orderId);
}
