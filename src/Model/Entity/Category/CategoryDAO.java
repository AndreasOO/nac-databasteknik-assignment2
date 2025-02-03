package Model.Entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    Optional<List<Category2>> findCategoryByProductID(int productId);

}
