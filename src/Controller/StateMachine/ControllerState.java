package Controller.StateMachine;

public interface ControllerState {
    void performSearch();
    void applyFilter();
    void addItemToCart();
    void loginUser();
    void logoutUser();
    void updateView();
    //TODO ADD METHOD FOR COMPLETING ORDER
    void completeOrder();
    //TODO ADD METHOD FOR REMOVING ORDER
    void removeOrder();
}
