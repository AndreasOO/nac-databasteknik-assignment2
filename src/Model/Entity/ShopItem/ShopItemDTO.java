package Model.Entity.ShopItem;

public class ShopItemDTO {
    private int id;
    private int productId;
    private int specificationId;
    private int quantity;

    public ShopItemDTO(int id, int productId, int specificationId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.specificationId = specificationId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getSpecificationId() {
        return specificationId;
    }

    public int getQuantity() {
        return quantity;
    }
}
