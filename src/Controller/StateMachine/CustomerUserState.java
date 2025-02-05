package Controller.StateMachine;

import Controller.ShopController;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Entity.ShopItem.ShopItem;
import Service.OrderService;
import Service.OrderServiceImpl;
import Service.SearchService;
import Service.SearchServiceImpl;
import Model.ShopModel;
import View.ShopView;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerUserState implements ControllerState {
    private final ShopController controller;
    private final ShopView view;
    private final ShopModel model;
    private final SearchService searchService;
    private final OrderService orderService;


    public CustomerUserState(ShopController controller, ShopView view, ShopModel model) {
        this.controller = controller;
        this.view = view;
        this.model = model;
        searchService = new SearchServiceImpl();
        orderService = new OrderServiceImpl();

    }

    @Override
    public void performSearch() {
        String searchInput = view.getSearchField().getText();
        if (searchInput.isEmpty()) {
            model.setCurrentSearchResult(searchService.searchAll2());
        }
        else if (view.getRadioButtonItemSize().isSelected()) {
            try {
                int size = Integer.parseInt(searchInput);
                model.setCurrentSearchResult(searchService.searchBySize2(size));
            } catch (NumberFormatException e) {
                model.setCurrentSearchResult(new ArrayList<>());
            }
        }
        else if (view.getRadioButtonItemName().isSelected()) {
            model.setCurrentSearchResult(searchService.searchByName2(searchInput));
        }
    }

    @Override
    public void applyFilter() {
        String filter = view.getFilterComboBox().getSelectedItem().toString();
        model.filterByCategory(filter);
    }

    @Override
    public void addItemToOrder() {
        int rowIndex = view.getSelectedItemInSearchTable();
        ShopItem selectedItem = model.getSelectedShopItemByRowIndex(rowIndex);
        try {
            orderService.addShopItemToOrder(selectedItem, model.getCurrentOrder());
        } catch (SQLException e) {
            view.showGeneralErrorMessage(e.getMessage().substring(e.getMessage().indexOf(':')+1));
            performSearch();
            return;
        }
        model.setCurrentOrder(orderService.setupAndGetActiveOrderForUser(model.getUserLoggedIn()));
        performSearch();

    }


    @Override
    public void loginUser() {
        throw new UnsupportedOperationException("Cannot login from logged in state");
    }

    @Override
    public void logoutUser() {
        view.resetSearchTable();
        view.resetOrderTable();
        view.resetOrderSummary();
        view.resetSearchParameters();
        controller.changeToLoginState();
    }

    @Override
    public void updateView() {
        view.showCustomerShopView();
    }

    @Override
    public void completeOrder() {
        String street = view.getOrderSummaryShippingAddressTextField().getText();
        String zipCode = view.getOrderSummaryZipCodeTextField().getText();
        try {
            validateShippingInput(zipCode,street );
        } catch (Exception e) {
            view.showGeneralErrorMessage(e.getMessage());
            return;
        }

        orderService.completeActiveOrder(model.getCurrentOrder(), new ShippingAddress(Integer.parseInt(zipCode), street));
        view.showGeneralInformationMessage("Order completed - Your order number is " + model.getCurrentOrder().getId());
        model.setCurrentOrder(orderService.setupAndGetActiveOrderForUser(model.getUserLoggedIn()));

    }

    private void validateShippingInput(String zipCode, String street) throws Exception {
        if (street == null || street.isEmpty() || zipCode == null || zipCode.isEmpty()) {
            throw new IllegalArgumentException("Shipping address fields cannot be empty");
        }
        if (!zipCode.trim().matches("^[0-9]{5}$")) {
            throw new IllegalArgumentException("Zip Code must contain 5 digits");
        }
        if (zipCode.charAt(0) == '0') {
            throw new IllegalArgumentException("Zip Code cannot start with 0");
        }
    }


    @Override
    public void removeOrder() {
        try {
            orderService.removeActiveOrder(model.getCurrentOrder());
        } catch (SQLException e) {
            view.showGeneralErrorMessage(e.getMessage());
        }
        // TODO service to remove current order
        //  -> cascade delete order items
        //  ->  model.setCurrentOrder(orderService.setupActiveOrderForUser(model.getUserLoggedIn()))
        // TODO repopulate quantity in shop items
        model.setCurrentOrder(orderService.setupAndGetActiveOrderForUser(model.getUserLoggedIn()));
         view.resetOrderSummary();
    }

    @Override
    public void setupModelForUser() {
        view.resetSearchParameters();
        performSearch();
        model.setCurrentOrder(orderService.setupAndGetActiveOrderForUser(model.getUserLoggedIn()));
    }
}
