package Model.Entity.Product;

import Model.Entity.Brand.Brand;
import Model.Entity.Category.Category2;
import Model.Entity.ProductType.ProductType;

import java.util.List;

public class Product {
    private final int id;
    private final String name;
    private final int price;
    private final Brand brand;
    private final ProductType productType;
    private final List<Category2> categories;

    public Product(int id, String name, int price, Brand brand, ProductType productType, List<Category2> categories) {
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

    public List<Category2> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", brand=" + brand +
                ", productType=" + productType +
                ", categories=" + categories +
                '}';
    }
}
