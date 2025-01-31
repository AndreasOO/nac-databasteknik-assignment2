package Controller.StateMachine;

import Controller.ShopController;
import Model.Entity.User.User;
import Model.Service.UserService;
import Model.Service.UserServiceImpl;
import Model.ShopModel;
import Security.UserAuthenticator;
import View.ShopView;

public class LoginState implements ControllerState {
    private final ShopController controller;
    private final ShopView view;
    private final ShopModel model;
    private final UserAuthenticator authenticator;
    private final UserService userService;


    public LoginState(ShopController controller, ShopView view, ShopModel model) {
        this.controller = controller;
        this.view = view;
        this.model = model;
        authenticator = UserAuthenticator.getInstance();
        userService = new UserServiceImpl();
    }

    @Override
    public void performSearch() {
        throw new UnsupportedOperationException("Cannot search from logged out state");
    }

    @Override
    public void applyFilter() {
        throw new UnsupportedOperationException("Cannot filter from logged out state");
    }

    @Override
    public void addItemToCart() {
        throw new UnsupportedOperationException("Cannot show details from logged out state");
    }

    @Override
    public void loginUser() {
        String username = view.getInputUsername();
        String password = view.getInputPassword();
        if (authenticator.authenticate(username, password)) {
            model.setUserLoggedIn(userService.getAuthenticatedUserByUsername(username));
            //TODO call services here to feed model with correct data
            controller.changeToCustomerUserState();
            view.resetLoginForm();
        } else {
            view.showLoginErrorMessage();
        }
    }

    @Override
    public void logoutUser() {
        throw new UnsupportedOperationException("Cannot logout from logged out state");
    }

    @Override
    public void updateView() {
        view.showUserLoginView();
    }

    @Override
    public void completeOrder() {
        throw new UnsupportedOperationException("Cannot complete order from logged out state");
    }

    @Override
    public void removeOrder() {
        throw new UnsupportedOperationException("Cannot remove order from logged out state");
    }

    @Override
    public void setupModelForUser() {
        throw new UnsupportedOperationException("Cannot setup model from logged out state");
    }
}
