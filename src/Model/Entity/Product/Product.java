package Model.Entity.Product;

import Model.Entity.Brand.Brand;
import Model.Entity.Category.Category;
import Model.Entity.ProductType.ProductType;

import java.util.List;

public class Product {
    private final int id;
    private final String name;
    private final int price;
    private final Brand brand;
    private final ProductType productType;
    private final List<Category> categories;

    public Product(int id, String name, int price, Brand brand, ProductType productType, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.productType = productType;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Brand getBrand() {
        return brand;
    }

    public ProductType getProductType() {
        return productType;
    }

    public List<Category> getCategories() {
        return categories;
    }

    //TODO FINISH WHEN OTHER ENTITY CLASSES FOR BRAND, CATEGORY AND PRODUCT TYPE ARE DONE
}
