package Model.Entity.ShopItem;

import java.util.List;

public class ShopItem {
    private final int id;
    private final String name;
    private final String brand;
    private final ItemColor color;
    private final int size;
    private final List<Category> shoeCategoriesList;
    private final int price;
    private final int quantity;

    public ShopItem(int id, String name, String brand, ItemColor color, int size, List<Category> shoeCategories, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.shoeCategoriesList = shoeCategories;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public ItemColor getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public List<Category> getShoeCategoriesList() {
        return shoeCategoriesList;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", color=" + color +
                ", size=" + size +
                ", shoeCategoriesList=" + shoeCategoriesList +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}




