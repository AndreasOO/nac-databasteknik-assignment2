package Model.Entity.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressDAO {
    Optional<ShippingAddress> findShippingAddressByID(int id);
    Optional<ShippingAddress> findShippingAddressByZipCodeAndStreet(ShippingAddress shippingAddress);
    void createShippingAddress(ShippingAddress shippingAddress);
}
