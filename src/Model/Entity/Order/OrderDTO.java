package Model.Entity.Order;

import java.time.LocalDate;

public class OrderDTO {
    private final int id;
    private final int customerId;
    private final int shippingAddressId;
    private final boolean active;

    public OrderDTO(int id, int customerId, int shippingAddressId, boolean active) {
        this.id = id;
        this.customerId = customerId;
        this.shippingAddressId = shippingAddressId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }


    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", shippingAddressId=" + shippingAddressId +
                ", active=" + active +
                '}';
    }
}
