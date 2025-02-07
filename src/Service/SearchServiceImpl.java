package Service;

import Entity.Brand.BrandDAO;
import Entity.Brand.BrandDAOImpl;
import Entity.Category.CategoryDAO;
import Entity.Category.CategoryDAOImpl;
import Entity.Product.Product;
import Entity.Product.ProductDAO;
import Entity.Product.ProductDAOImpl;
import Entity.Product.ProductDTO;
import Entity.ProductType.ProductTypeDAO;
import Entity.ProductType.ProductTypeDAOImpl;
import Entity.ShopItem.ShopItem;
import Entity.ShopItem.ShopItemDAO;
import Entity.ShopItem.ShopItemDAOImpl;
import Entity.ShopItem.ShopItemDTO;
import Entity.Specification.SpecificationDAO;
import Entity.Specification.SpecificationDAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchServiceImpl implements SearchService {
    ShopItemDAO shopItemDAO;
    ProductDAO productDAO;
    BrandDAO brandDAO;
    ProductTypeDAO productTypeDAO;
    SpecificationDAO specificationDAO;
    CategoryDAO categoryDAO;

    public SearchServiceImpl() {
        shopItemDAO = new ShopItemDAOImpl();
        productDAO = new ProductDAOImpl();
        brandDAO = new BrandDAOImpl();
        productTypeDAO = new ProductTypeDAOImpl();
        specificationDAO = new SpecificationDAOImpl();
        categoryDAO = new CategoryDAOImpl();

    }



    @Override
    public List<ShopItem> searchAll() {
        Optional<List<ShopItemDTO>> shopItemDTOs = shopItemDAO.findAllDTO();
        if (shopItemDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return createShopItemListFromDTOs(shopItemDTOs.get());
        }
    }

    @Override
    public List<ShopItem> searchByName(String name) {
        Optional<List<ShopItemDTO>> shopItemDTOs = shopItemDAO.findByNameDTO(name);
        if (shopItemDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return createShopItemListFromDTOs(shopItemDTOs.get());
        }
    }

    @Override
    public List<ShopItem> searchBySize(int size) {
        Optional<List<ShopItemDTO>> shopItemDTOs = shopItemDAO.findBySizeDTO(size);
        if (shopItemDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return createShopItemListFromDTOs(shopItemDTOs.get());
        }
    }

    private List<ShopItem> createShopItemListFromDTOs(List<ShopItemDTO> shopItemDTOs) {
        return shopItemDTOs
                .stream()
                .map(shopItemDTO -> new ShopItem(shopItemDTO.getId(),
                                                             createProductFromId(shopItemDTO.getProductId()),
                                                             specificationDAO.findSpecificationByID(shopItemDTO.getSpecificationId()).orElseThrow(),
                                                             shopItemDTO.getQuantity()
                ))
                .toList();
    }

    private Product createProductFromId(int productId) {
        ProductDTO productDTO = productDAO.findProductDTOByID(productId).orElseThrow();
        return new Product(productDTO.getId(),
                           productDTO.getName(),
                           productDTO.getPrice(),
                           brandDAO.findBrandByID(productDTO.getBrandId()).orElseThrow(),
                           productTypeDAO.findProductTypeByID(productDTO.getProductTypeId()).orElseThrow(),
                           categoryDAO.findCategoryByProductID(productDTO.getId()).orElseThrow());
    }

}
