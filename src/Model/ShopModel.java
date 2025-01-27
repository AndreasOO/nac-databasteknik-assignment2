package Model;

import View.OrderObserver;
import View.FilterResultObserver;
import View.SearchResultObserver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ShopModel {
    private final ShopItemDAO shopItemDAO;
    private ShopItem shopItemPickedForOrder;
    private List<ShopItem> currentSearchResult;
    private List<ShopItem> filteredSearchResult;

    private final List<OrderObserver> shoppingCartObservers;
    private final List<SearchResultObserver> searchResultObservers;
    private final List<FilterResultObserver> filterResultObservers;


    public ShopModel() {
        shopItemDAO = new ShopItemDAO();
        shopItemDAO.initializeMockDatabase();
        shoppingCartObservers = new ArrayList<>();
        searchResultObservers = new ArrayList<>();
        filterResultObservers = new ArrayList<>();
    }

    public void addItemToOrder(int shopItemId) {
        shopItemPickedForOrder = shopItemDAO.findById(shopItemId).getFirst();
        notifyEmployeeDetailsObservers();
    }

    public void searchAll() {
        currentSearchResult = shopItemDAO.findAll();
        filteredSearchResult = currentSearchResult;
        notifySearchResultObservers();

    }

    public void searchByName(String name) {
        currentSearchResult = shopItemDAO.findByName(name);
        filteredSearchResult = currentSearchResult;
        notifySearchResultObservers();
    }

    public void searchByID(String id) {
        try {
            int idInt = Integer.parseInt(id);
            currentSearchResult = shopItemDAO.findById(idInt);
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
            Category catFilter = Arrays.stream(Category.values()).filter(cat -> cat.getDisplayName().equalsIgnoreCase(filter)).findFirst().get();
            filteredSearchResult = currentSearchResult.stream().filter(shopItem -> shopItem.getShoeCategoriesList().contains(catFilter)).collect(Collectors.toList());
        }
        notifyFilterResultObservers();
    }


    public void notifyEmployeeDetailsObservers() {
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


    public void registerEmployeeDetailsObserver(OrderObserver employeeDetailsObserver) {
        shoppingCartObservers.add(employeeDetailsObserver);
    }

    public void registerSearchResultObserver(SearchResultObserver searchResultObserver) {
        searchResultObservers.add(searchResultObserver);
    }

    public void registerFilterResultObserver(FilterResultObserver filterResultObserver) {
        filterResultObservers.add(filterResultObserver);
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


    public void clearSearchHistory() {
        shopItemPickedForOrder = null;
        currentSearchResult = null;
        filteredSearchResult = null;
    }
}
