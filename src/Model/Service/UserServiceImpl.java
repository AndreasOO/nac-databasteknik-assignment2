package Model.Service;

import Model.Entity.User.User;
import Model.Entity.User.UserDAO;
import Model.Entity.User.UserDAOImpl;

public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public User getAuthenticatedUserByUsername(String username) {
        return userDAO.findUserByEmail(username);
    }
}
