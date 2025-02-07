package Entity.Product;

import java.util.Optional;

public interface ProductDAO {
    Optional<ProductDTO> findProductDTOByID(int id);

}
