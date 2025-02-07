package Entity.Product;

public class ProductDTO {
    private final int id;
    private final String name;
    private final int price;
    private final int brandId;
    private final int productTypeId;

    public ProductDTO(int id, String name, int price, int brandId, int productTypeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brandId = brandId;
        this.productTypeId = productTypeId;
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

    public int getBrandId() {
        return brandId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }
}
