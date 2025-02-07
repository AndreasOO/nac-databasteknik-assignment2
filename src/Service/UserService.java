package Service;

import Entity.User.User;

public interface UserService {
    User getAuthenticatedUserByUsername(String username);
}
