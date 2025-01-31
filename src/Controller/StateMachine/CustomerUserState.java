package Controller.StateMachine;

import Controller.ShopController;
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
            model.setCurrentSearchResult(searchService.searchAll());
        }
        else if (view.getRadioButtonItemSize().isSelected()) {
            try {
                int size = Integer.parseInt(searchInput);
                model.setCurrentSearchResult(searchService.searchBySize(size));
            } catch (NumberFormatException e) {
                model.setCurrentSearchResult(new ArrayList<>());
            }
        }
        else if (view.getRadioButtonItemName().isSelected()) {
            model.setCurrentSearchResult(searchService.searchByName(searchInput));
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
        // TODO Add action in model
        System.out.println("Order completed");
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
