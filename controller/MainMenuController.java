package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class initializes the Main Menu window. */

public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField searchParts;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partsPrice;

    @FXML
    private TextField searchProducts;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productsInventoryLevel;

    @FXML
    private TableColumn<Product, Double> productsPrice;

    @FXML
    private Label lblDeleteError;
    @FXML
    private Button idExit;

    /** This method opens the Add Part window.
     @param event Opens Add Part window when the Add button is clicked.
     */

    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method opens the Modify Part window.
     @param event Opens Modify Part window when the Modify button is clicked.
     */

    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {
        try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
                loader.load();

                ModifyPartController MPartController = loader.getController();
                MPartController.sendPart(partsTable.getSelectionModel().getSelectedItem());

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }catch (Exception e){
                Alert alert= new Alert(Alert.AlertType.ERROR,"Please select the item to modify!",ButtonType.OK,ButtonType.CANCEL);
                alert.setHeaderText("Error");
                alert.showAndWait();

         }

    }

    /** This method deletes a part from the Parts Table.
     @param event Deletes a part when the Delete button is clicked.
     */

    @FXML
    public void onActionDeletePart(ActionEvent event) throws IOException {
        try {

            Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }catch(Exception e){

            Alert alert= new Alert(Alert.AlertType.ERROR,"Please select the item to delete!",ButtonType.OK,ButtonType.CANCEL);
            alert.setHeaderText("Error");
            alert.showAndWait();

        }


    }

    /** This method opens the Add Product window.
     @param event Opens Add Product window when the Add button is clicked.
     */

    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /** This method opens the Modify Product window.
     @param event Opens Modify Product window when the Modify button is clicked.
     */

    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException {

        try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
                loader.load();

                ModifyProductController MPController = loader.getController();


                MPController.sendProduct(productsTable.getSelectionModel().getSelectedItem());
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

        }catch (Exception e){
                  Alert alert= new Alert(Alert.AlertType.ERROR,"Please select the product to modify!",ButtonType.OK,ButtonType.CANCEL);
                  alert.setHeaderText("Error");
                  alert.showAndWait();

        }
    }

    /** This method deletes a product from the Products Table.
     @param event Deletes a product when the Delete button is clicked.
     */

    @FXML
    public void onActionDeleteProduct(ActionEvent event) throws IOException {
        try{
            Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
            if ((selectedProduct.getAssociatedParts().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?", ButtonType.OK, ButtonType.CANCEL);
                alert.setTitle("Products");
                alert.setHeaderText("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                     Inventory.deleteProduct(selectedProduct);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?", ButtonType.OK, ButtonType.CANCEL);
                alert.setTitle("Products");
                alert.setHeaderText("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                   // Inventory.deleteProduct(selectedProduct);
                    //selectedProduct.getAssociatedParts().clear();
                    lblDeleteError.setText("This product has parts.");

                }
            }
        }catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR,"Please select the product to delete!",ButtonType.OK,ButtonType.CANCEL);
            alert.setHeaderText("Error");
            alert.showAndWait();
            }
    }

    /** This method searches the product in the Product table.
     @param keyEvent Searches for product when the Enter key is pressed.
     */

    public void searchProductId(KeyEvent keyEvent) {

        String q= searchProducts.getText();
        ObservableList<Product> products = Inventory.lookupProduct(q);
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (products.size() == 0) {
                try {
                    int searchProductId = Integer.parseInt(q);
                    TableView.TableViewSelectionModel selectionModel = productsTable.getSelectionModel();
                    Product pr = Inventory.lookupProduct(searchProductId);
                    if(pr != null) {

                        products.addAll(Inventory.getAllProducts());
                        selectionModel.select(pr);
                }
            }catch (Exception e) {
                    //ignore
                }
            }
            productsTable.setItems(products);


        }
    }

    /** This method searches the part in the Part table.
     @param keyEvent Searches for part when the Enter key is pressed.
     */

    public void searchPartId(KeyEvent keyEvent) {

        String q= searchParts.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (parts.size() == 0) {
                try {
                    int searchPartId = Integer.parseInt(q);
                    TableView.TableViewSelectionModel selectionModel = partsTable.getSelectionModel();
                    Part part = Inventory.lookupPart(searchPartId);
                    if(part != null) {

                        parts.addAll(Inventory.getAllParts());
                        selectionModel.select(part);
                    }
                }catch (Exception e) {
                    //ignore
                }
            }
            partsTable.setItems(parts);


        }
    }

    /** This method exits the program.
     @param actionEvent Exits the program when the Exit button is clicked.
     */

    public void onActionExitMenu(ActionEvent actionEvent) {
        Stage stage = (Stage)idExit.getScene().getWindow();
        // Exit App
        stage.close();
    }

    /** Clears Search Product text box. */
    public void onMouseCLicked(MouseEvent mouseEvent) {
        searchProducts.setText("");
    }

    /** Clears Search Part text box. */
    public void onMouseClickedParts(MouseEvent mouseEvent) {
        searchParts.setText("");
    }

    /** This method initializes the Main Menu. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        

        partsTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("\n" +
                "ame"));
        productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}



