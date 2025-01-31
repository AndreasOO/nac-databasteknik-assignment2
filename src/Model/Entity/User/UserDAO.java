package Model.Entity.User;

public interface UserDAO {
    User findUserByEmail(String email);
    User findUserById(int email);
}
