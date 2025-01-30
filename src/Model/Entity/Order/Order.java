package Model.Entity.Order;

import Model.Entity.User.User;

import java.time.LocalDate;

public class Order {
    private int id;
    private User customer;
    private LocalDate orderDate;
    private ShippingAddress shippingAddress;
    private boolean active;

    public Order(int id, User customer, LocalDate orderDate, ShippingAddress shippingAddress, boolean active) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.active = active;
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
}
