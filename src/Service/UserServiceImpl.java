package Service;

import Entity.User.User;
import Entity.User.UserDAO;
import Entity.User.UserDAOImpl;

public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public User getAuthenticatedUserByUsername(String username) {
        return userDAO.findUserByEmail(username).orElseThrow();
    }
}
