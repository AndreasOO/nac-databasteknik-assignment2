package Entity.ShippingAddress;

public class ShippingAddress {
    private int id;
    private final int zipCode;
    private final String street;

    public ShippingAddress(int zipCode, String street) {
        this.zipCode = zipCode;
        this.street = street;
    }

    public ShippingAddress(int id, String street, int zipCode) {
        this.zipCode = zipCode;
        this.street = street;
        this.id = id;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
                "id=" + id +
                ", zipCode=" + zipCode +
                ", street='" + street + '\'' +
                '}';
    }
}
