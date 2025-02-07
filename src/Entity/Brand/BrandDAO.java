package Entity.Brand;

import java.util.Optional;

public interface BrandDAO {
    Optional<Brand> findBrandByID(int id);

}
