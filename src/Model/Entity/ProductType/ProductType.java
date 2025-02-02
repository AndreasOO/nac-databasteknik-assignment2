package Model.Entity.ProductType;

public class ProductType {
    private final int id;
    private final String name;

    public ProductType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
