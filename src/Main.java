import Controller.ShopController;
import Model.ShopItemDAO;
import Model.ShopModel;

public class Main {
    public static void main(String[] args) {
        ShopModel model = new ShopModel();

        ShopController controller = new ShopController(model);
        controller.initializeController();


        String username = "shopadmin";
        String password = "test1234";

//        try (Connection con = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/shop_db?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true", username, password);
//
//             PreparedStatement statement =  con.prepareStatement("select id, name, price from products");
//             ResultSet resultSet = statement.executeQuery();
//
//        ) {
//
//
//            while (resultSet.next()) {
//                System.out.print(resultSet.getInt("id"));
//                System.out.print(", ");
//                System.out.print(resultSet.getString("name"));
//                System.out.print(", ");
//                System.out.print(resultSet.getString("price"));
//                System.out.println();
//            }
//
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//        }
 }
}