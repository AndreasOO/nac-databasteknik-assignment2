package Model.Entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    Optional<List<Category>> findCategoryByProductID(int productId);

}
