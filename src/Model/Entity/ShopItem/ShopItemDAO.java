package Model.Entity.ShopItem;

import java.util.List;
import java.util.Optional;

public interface ShopItemDAO {
    List<ShopItem> findAll();
    Optional<List<ShopItemDTO>> findAllDTO();
    List<ShopItem> findByName(String searchInput);
    List<ShopItem> findById(int id);
    List<ShopItem> findBySize(int size);
    List<ShopItem> findByOrderId(int orderId);
}
