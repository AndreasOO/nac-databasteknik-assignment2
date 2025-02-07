package Entity.ShopItem;

import Entity.Product.Product;
import Entity.Specification.Specification;

public class ShopItem {
    private final int id;
    private final Product product;
    private final Specification specification;
    private final int quantity;

    public ShopItem(int id, Product product, Specification specification, int quantity) {
        this.id = id;
        this.product = product;
        this.specification = specification;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Specification getSpecification() {
        return specification;
    }

    public int getQuantity() {
        return quantity;
    }


    @Override
    public String toString() {
        return "ShopItem2{" +
                "id=" + id +
                ", product=" + product +
                ", specification=" + specification +
                ", quantity=" + quantity +
                '}';
    }
}
