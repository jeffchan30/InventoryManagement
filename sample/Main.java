package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

/** Java doc is located in the folder name called C482_Javadoc */


/** This class creates an app of the inventory management system. */
public class Main extends Application {

    /** This method loads the Main Menu. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, Color.WHITE));
        primaryStage.show();


    }

/** This is the main method. This is the first method that gets called when the program is ran. */
    public static void main(String[] args) {

        Product product1 = new Product (1, "Subaru", 15.00, 6,1,10);
        Product product2 = new Product(2, "Toyota", 15.00, 6,1,10);
        Product product3 = new Product(3,"Honda",20.0,4,1,5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        Part part1 = new Outsourced(1, "Turbo", 15.00, 6, 1, 10, "Amazon");
        Part part2 = new InHouse(2,"Exhaust",15.00,2,1,14,555);

        Inventory.addPart(part1);
        Inventory.addPart(part2);

        launch(args);
    }
}
