package Controller.StateMachine;

import Controller.ShopController;
import Model.Entity.ShippingAddress.ShippingAddress;
import Model.Service.OrderService;
import Model.Service.OrderServiceImpl;
import Model.Service.SearchService;
import Model.Service.SearchServiceImpl;
import Model.ShopModel;
import View.ShopView;

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
//            searchService.searchAll2().forEach(System.out::println);
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
    public void addItemToCart() {
        int rowIndex = view.getSelectedItemInSearchTable();
        model.addItemToOrder(rowIndex);

    }


    @Override
    public void loginUser() {
        throw new UnsupportedOperationException("Cannot login from logged in state");
    }

    @Override
    public void logoutUser() {
        model.clearSearchHistory();
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

        // TODO Add action in model
        System.out.println("Order completed");
        model.setCurrentOrder(orderService.setupActiveOrderForUser(model.getUserLoggedIn()));
        System.out.println("New order set up");
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
        // TODO Add action in model then let gui reset by observers
         model.clearOrder();
         view.resetOrderSummary();
    }

    @Override
    public void setupModelForUser() {
        view.resetSearchParameters();
        performSearch();
        model.setCurrentOrder(orderService.setupActiveOrderForUser(model.getUserLoggedIn()));
    }
}
