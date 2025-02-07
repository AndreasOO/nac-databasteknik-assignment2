package Entity.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(int email);
}
