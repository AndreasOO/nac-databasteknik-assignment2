package Model.Service;

import Model.Entity.User;

public interface UserService {
    User findUserByEmail(String email);
}
