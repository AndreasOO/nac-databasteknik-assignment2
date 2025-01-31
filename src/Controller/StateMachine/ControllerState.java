package Controller.StateMachine;

public interface ControllerState {
    void performSearch();
    void applyFilter();
    void addItemToCart();
    void loginUser();
    void logoutUser();
    void updateView();
    void completeOrder();
    void removeOrder();
    void setupModelForUser();
}
