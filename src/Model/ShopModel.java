package Model;


import Model.Entity.ShopItem.*;
import Model.Entity.Order.Order;
import Model.Entity.User.User;
import View.OrderObserver;
import View.FilterResultObserver;
import View.SearchResultObserver;
import View.UserObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ShopModel {
    private User userLoggedIn;
    private Order currentOrder;
    private List<ShopItem> currentSearchResult;
    private List<ShopItem> filteredSearchResult;

    private final List<OrderObserver> shoppingCartObservers;
    private final List<SearchResultObserver> searchResultObservers;
    private final List<FilterResultObserver> filterResultObservers;
    private final List<UserObserver> userObservers;

    public ShopModel() {
        currentOrder = new Order();
        currentSearchResult = new ArrayList<>();
        filteredSearchResult = new ArrayList<>();
        shoppingCartObservers = new ArrayList<>();
        searchResultObservers = new ArrayList<>();
        filterResultObservers = new ArrayList<>();
        userObservers = new ArrayList<>();
    }

    public void setUserLoggedIn(User user) {
        userLoggedIn = user;
        notifyUserObservers();
    }

    public void setCurrentOrder(Order order) {
        currentOrder = order;
        notifyOrderObservers();
    }

    public ShopItem getSelectedShopItemByRowIndex(int rowIndex) {
        return filteredSearchResult.get(rowIndex);
    }
    //TODO redo with stored procedure
    public void addItemToOrder(int rowIndex) {
        currentOrder.getOrderItems().add(filteredSearchResult.get(rowIndex));
        notifyOrderObservers();
    }

    public void clearOrder() {
        currentOrder.getOrderItems().clear();
        notifyOrderObservers();
    }

    public void setCurrentSearchResult(List<ShopItem> searchResult) {
        currentSearchResult = searchResult;
        filteredSearchResult = currentSearchResult;
        notifySearchResultObservers();

    }


    public void filterByCategory(String filter) {
        if (filter.equals("None")) {
            filteredSearchResult = currentSearchResult;
        } else {

            filteredSearchResult = currentSearchResult.stream().filter(shopItem -> shopItem.getProduct()
                    .getCategories().stream().anyMatch(cat -> cat.getName().equalsIgnoreCase(filter))).collect(Collectors.toList());
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


    public List<ShopItem> getCurrentSearchResult() {
        return currentSearchResult;
    }

    public List<ShopItem> getFilteredSearchResult() {
        return filteredSearchResult;
    }

    public List<ShopItem> getCurrentOrderItemList() {
        return currentOrder.getOrderItems();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }
}
