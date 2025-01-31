package Model.Entity.Order;

import Model.Entity.ShopItem.ShopItem;
import Model.Entity.User.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private User customer;
    private LocalDate orderDate;
    private ShippingAddress shippingAddress;
    private boolean active;
    private List<ShopItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public Order(int id, User customer, boolean active, List<ShopItem> orderItems) {
        this.id = id;
        this.customer = customer;
        this.active = active;
        this.orderItems = orderItems;
    }

    public Order(int id, User customer, LocalDate orderDate, ShippingAddress shippingAddress, boolean active) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.active = active;
    }


    public void addOrderItem(ShopItem shopItem) {
        orderItems.add(shopItem);
    }

    public int getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public boolean isActive() {
        return active;
    }

    public List<ShopItem> getOrderItems() {
        return orderItems;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setOrderItems(List<ShopItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", shippingAddress=" + shippingAddress +
                ", active=" + active +
                ", orderItems=" + orderItems +
                '}';
    }
}
