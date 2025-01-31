package Model.Entity.ShippingAddress;

public interface ShippingAddressDAO {
    ShippingAddress findShippingAddressByID(int id);
    ShippingAddress findShippingAddressByZipCodeAndStreet(ShippingAddress shippingAddress);
    void createShippingAddress(ShippingAddress shippingAddress);
}
