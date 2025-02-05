import Controller.ShopController;
import Model.ShopModel;

public class Main {
    public static void main(String[] args) {
        ShopModel model = new ShopModel();

        ShopController controller = new ShopController(model);

        controller.initializeController();

 }
}