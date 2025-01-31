package Model.Service;

import Model.Entity.User.User;

public interface UserService {
    User getAuthenticatedUserByUsername(String username);
}
