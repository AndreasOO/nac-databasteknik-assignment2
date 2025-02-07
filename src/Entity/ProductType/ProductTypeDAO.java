package Entity.ProductType;

import java.util.Optional;

public interface ProductTypeDAO {
    Optional<ProductType> findProductTypeByID(int id);

}
