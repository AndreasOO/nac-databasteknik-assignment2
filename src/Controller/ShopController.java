package Controller;

import Controller.StateMachine.*;
import Model.ShopModel;
import View.ShopView;

public class ShopController {
    private final ShopModel model;
    private final ShopView view;


    private ControllerState state;
    private final ControllerState loginState;
    private final ControllerState customerUserState;

    public ShopController(ShopModel model) {
        this.model = model;
        view = new ShopView(model);

        loginState = new LoginState(this, view, model);
        customerUserState = new CustomerUserState(this, view, model);


        state = loginState;

    }

    public void initializeController() {
        view.init();
        state.updateView();
        addEventListeners();
    }

    private void addEventListeners() {

        view.getSearchField().addActionListener(e -> {
            state.performSearch();
        });

        view.getFilterComboBox().addActionListener(e -> {
            state.applyFilter();
        });
        view.getLoginButton().addActionListener(e -> {
            state.loginUser();
            state.setupModelForUser();
            state.updateView();
        });

        view.getLogOutButton().addActionListener(e -> {
            state.logoutUser();
            state.updateView();
        });

        view.getAddToOrderButton().addActionListener(e -> {
            state.addItemToOrder();
        });

        view.getCompleteOrderButton().addActionListener(e -> {
            state.completeOrder();
        });

        view.getRemoveOrderButton().addActionListener(e -> {
            state.removeOrder();
        });
    }




    public void changeToCustomerUserState() {
        state = customerUserState;
    }

    public void changeToLoginState() {
        state = loginState;
    }
}
