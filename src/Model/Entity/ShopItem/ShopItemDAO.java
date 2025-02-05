package Model.Entity.ShopItem;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ShopItemDAO {
    Optional<List<ShopItemDTO>> findAllDTO();
    Optional<List<ShopItemDTO>> findByNameDTO(String searchInput);
    Optional<List<ShopItemDTO>> findByIdDTO(int id);
    Optional<List<ShopItemDTO>> findBySizeDTO(int size);
    Optional<List<ShopItemDTO>> findByOrderIdAllDTO(int orderId);
    void incrementQuantityOfShopItem(ShopItem shopItem) throws SQLException;
    //TODO ADD METHOD TO REPOPULATE QUANTITY
}
