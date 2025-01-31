package Model.Service;

import Model.Entity.ShopItem.ShopItem;
import Model.Entity.ShopItem.ShopItemDAO;
import Model.Entity.ShopItem.ShopItemDAOImpl;

import java.util.List;

public class SearchServiceImpl implements SearchService {
    ShopItemDAO shopItemDAO;

    public SearchServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
    }

    @Override
    public List<ShopItem> searchAll() {
        return shopItemDAO.findAll();
    }

    @Override
    public List<ShopItem> searchByName(String name) {
        return shopItemDAO.findByName(name);
    }

    @Override
    public List<ShopItem> searchBySize(int size) {
        return shopItemDAO.findBySize(size);
    }
}
