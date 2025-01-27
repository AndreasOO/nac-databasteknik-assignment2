package Controller.StateMachine;

import Controller.ShopController;
import Model.ShopModel;
import View.ShopView;

public class CustomerUserState implements ControllerState {
    private final ShopController controller;
    private final ShopView view;
    private final ShopModel model;

    public CustomerUserState(ShopController controller, ShopView view, ShopModel model) {
        this.controller = controller;
        this.view = view;
        this.model = model;
    }

    @Override
    public void performSearch() {
        String searchInput = view.getSearchField().getText();
        if (searchInput.isEmpty()) {
            model.searchAll();
        }
        else if (view.getRadioButtonItemSize().isSelected()) {
            model.searchByID(searchInput);
        }
        else if (view.getRadioButtonItemName().isSelected()) {
            model.searchByName(searchInput);
        }
    }

    @Override
    public void applyFilter() {
        String filter = view.getFilterComboBox().getSelectedItem().toString();
        model.filterByCategory(filter);
    }

    @Override
    public void addItemToCart() {
        int shopItemId = Integer.parseInt(view.getSelectedItemInSearchTable());
        model.setShopItemPickedForCart(shopItemId);
    }


    @Override
    public void loginUser() {
        throw new UnsupportedOperationException("Cannot login from logged in state");
    }

    @Override
    public void logoutUser() {
        model.clearSearchHistory();
        controller.changeToLoginState();
    }

    @Override
    public void updateView() {
        view.resetSearchTable();
        view.showCustomerShopView();
    }
}
