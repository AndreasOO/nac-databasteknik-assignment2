package Model.Entity.ShopItem;

import java.util.List;
import java.util.Optional;

public interface ShopItemDAO {
    List<ShopItem> findAll();
    Optional<List<ShopItemDTO>> findAllDTO();
    Optional<List<ShopItemDTO>> findByNameDTO(String searchInput);
    Optional<List<ShopItemDTO>> findByIdDTO(int id);
    Optional<List<ShopItemDTO>> findBySizeDTO(int size);
    Optional<List<ShopItemDTO>> findByOrderIdAllDTO(int orderId);
    List<ShopItem> findByName(String searchInput);
    List<ShopItem> findById(int id);
    List<ShopItem> findBySize(int size);
    List<ShopItem> findByOrderId(int orderId);
}
