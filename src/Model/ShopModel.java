package Model;

import Model.DAO.OrderDAO;
import Model.DAO.ShopItemDAO;
import Model.DAO.UserDAO;
import Model.Entity.Order.Order;
import Model.Entity.ShopItem.Category;
import Model.Entity.ShopItem.ShopItem;
import Model.Entity.User.User;
import Model.Service.OrderService;
import Model.Service.ShopItemService;
import Model.Service.UserService;
import View.OrderObserver;
import View.FilterResultObserver;
import View.SearchResultObserver;
import View.UserObserver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ShopModel {
    private final UserService userService;
    private final ShopItemService shopItemService;
    private final OrderService orderService;
    private User userLoggedIn;
    private ShopItem shopItemPickedForOrder;
    private Order currentOrder;
    private List<ShopItem> currentSearchResult;
    private List<ShopItem> filteredSearchResult;

    private final List<OrderObserver> shoppingCartObservers;
    private final List<SearchResultObserver> searchResultObservers;
    private final List<FilterResultObserver> filterResultObservers;
    private final List<UserObserver> userObservers;

    public ShopModel() {
        userService = new UserDAO();
        shopItemService = new ShopItemDAO();
        orderService = new OrderDAO();
        currentOrder = new Order();
        currentSearchResult = new ArrayList<>();
        filteredSearchResult = new ArrayList<>();
        shoppingCartObservers = new ArrayList<>();
        searchResultObservers = new ArrayList<>();
        filterResultObservers = new ArrayList<>();
        userObservers = new ArrayList<>();
    }

    public void loginAuthenticatedUserByUsername(String username) {
        userLoggedIn = userService.findUserByEmail(username);
        notifyUserObservers();
    }

    public void setupActiveOrder() {
        currentOrder = orderService.findActiveOrderByUserId(userLoggedIn);
        if (currentOrder == null) {
            orderService.startNewActiveOrder(userLoggedIn);
            currentOrder = orderService.findActiveOrderByUserId(userLoggedIn);
            System.out.println(currentOrder + "did not find ac ");
        }
        notifyOrderObservers();
    }

    //TODO redo with stored procedure
    public void addItemToOrder(int rowIndex) {
        currentOrder.getOrderItems().add(filteredSearchResult.get(rowIndex));
        orderService.findActiveOrderByUserId(userLoggedIn);
        System.out.println(orderService.findActiveOrderByUserId(userLoggedIn));
        notifyOrderObservers();
    }

    public void clearOrder() {
        currentOrder.getOrderItems().clear();
        notifyOrderObservers();
    }

    public void searchAll() {
        currentSearchResult = shopItemService.findAll();
        filteredSearchResult = currentSearchResult;
        notifySearchResultObservers();

    }

    public void searchByName(String name) {
        currentSearchResult = shopItemService.findByName(name);
        filteredSearchResult = currentSearchResult;
        notifySearchResultObservers();
    }

    public void searchBySize(String size) {
        try {
            int sizeInt = Integer.parseInt(size);
            currentSearchResult = shopItemService.findBySize(sizeInt);
            filteredSearchResult = currentSearchResult;
            notifySearchResultObservers();
        } catch (NumberFormatException e) {
            System.out.println("Invalid size logged");
            currentSearchResult = new ArrayList<>();
            filteredSearchResult = currentSearchResult;
            notifySearchResultObservers();
        }
    }


    public void searchByID(String id) {
        try {
            int idInt = Integer.parseInt(id);
            currentSearchResult = shopItemService.findById(idInt);
            filteredSearchResult = currentSearchResult;
            notifySearchResultObservers();
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID logged");
            currentSearchResult = new ArrayList<>();
            filteredSearchResult = currentSearchResult;
            notifySearchResultObservers();
        }
    }

    public void filterByCategory(String filter) {
        if (filter.equals("None")) {
            filteredSearchResult = currentSearchResult;
        } else {
            Category catFilter = Arrays.stream(Category.values()).filter(cat -> cat.getDisplayName().equalsIgnoreCase(filter))
                                                                 .findFirst()
                                                                 .get();

            filteredSearchResult = currentSearchResult.stream().filter(shopItem -> shopItem.getShoeCategoriesList().contains(catFilter))
                                                               .collect(Collectors.toList());
        }
        notifyFilterResultObservers();
    }


    public void notifyOrderObservers() {
        for (OrderObserver employeeDetailsObserver : shoppingCartObservers) {
            employeeDetailsObserver.updateOrder();
        }
    }

    public void notifySearchResultObservers() {
        for (SearchResultObserver searchResultObserver : searchResultObservers) {
            searchResultObserver.updateSearchResult();
        }
    }

    public void notifyFilterResultObservers() {
        for (FilterResultObserver filterResultObserver : filterResultObservers) {
            filterResultObserver.updateFilterResult();
        }
    }

    public void notifyUserObservers() {
        for (UserObserver userObserver : userObservers) {
            userObserver.updateLoggedInUser();
        }
    }




    public void registerOrderObserver(OrderObserver orderObserver) {
        shoppingCartObservers.add(orderObserver);
    }

    public void registerSearchResultObserver(SearchResultObserver searchResultObserver) {
        searchResultObservers.add(searchResultObserver);
    }

    public void registerFilterResultObserver(FilterResultObserver filterResultObserver) {
        filterResultObservers.add(filterResultObserver);
    }

    public void registerUserObserver(UserObserver userObserver) {
        userObservers.add(userObserver);
    }



    public ShopItem getShopItemPickedForOrder() {
        return shopItemPickedForOrder;
    }

    public List<ShopItem> getCurrentSearchResult() {
        return currentSearchResult;
    }

    public List<ShopItem> getFilteredSearchResult() {
        return filteredSearchResult;
    }

    public List<ShopItem> getCurrentOrderItemList() {
        return currentOrder.getOrderItems();
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public void clearSearchHistory() {
        shopItemPickedForOrder = null;
        currentSearchResult = new ArrayList<>();
        filteredSearchResult = new ArrayList<>();
        currentOrder.getOrderItems().clear();
    }
}
