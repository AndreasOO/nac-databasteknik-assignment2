package Entity.Specification;

import java.util.Optional;

public interface SpecificationDAO {
    Optional<Specification> findSpecificationByID(int id);

}
