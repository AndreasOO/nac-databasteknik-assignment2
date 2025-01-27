package Controller.StateMachine;

import Controller.ShopController;
import Model.ShopModel;
import View.ShopView;

public class LoginState implements ControllerState {
    private final ShopController controller;
    private final ShopView view;
    private final ShopModel model;

    public LoginState(ShopController controller, ShopView view, ShopModel model) {
        this.controller = controller;
        this.view = view;
        this.model = model;
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
            //TODO: check user credentials before login
            //TODO: Get user id and att to model
            controller.changeToCustomerUserState();


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
}
