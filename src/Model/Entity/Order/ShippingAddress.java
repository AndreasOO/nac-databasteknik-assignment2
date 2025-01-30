package Model.Entity.Order;

public class ShippingAddress {
    private final int zipCode;
    private final String street;

    public ShippingAddress(int zipCode, String street) {
        this.zipCode = zipCode;
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }
}
