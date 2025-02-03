package Model.Entity.ShopItem;

import Model.Entity.Category.Category2;
import Model.Entity.Product.Product;
import Model.Entity.Specification.Specification;

import java.util.List;

public class ShopItem2 {
    private final int id;
    private final Product product;
    private final Specification specification;
    private final int quantity;

    public ShopItem2(int id, Product product, Specification specification, int quantity) {
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
