package controller;

import javafx.collections.FXCollections;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** FUTURE ENHANCEMENTS
 For future enhancement,
 - I would have reduced duplicated code if I create
 deleteAssociatedPart() method in base class and implement or
 override its features from its subclasses.
- If I create separate methods for text box validation, the code will be cleaner and
 reduce duplicate code for future updates and implementation of the application.*/

/** RUNTIME ERRORS
 To catch runtime errors when it occurs when you enter wrong data types,
 I implemented exception handling in the OnActionSaveProduct method.

 */


/** This class initializes the Modify Product window. */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idProductText;

    @FXML
    private TextField nameProductText;

    @FXML
    private TextField invProductText;

    @FXML
    private TextField priceProductText;

    @FXML
    private TextField maxProductText;

    @FXML
    private TextField minProductText;

    @FXML
    private TextField searchParts;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableView<Part> asscPartsTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partsPrice;

    @FXML
    private TableColumn<Part, Integer> asscPartID;

    @FXML
    private TableColumn<Part, String> asscPartName;

    @FXML
    private TableColumn<Part, Integer> asscPartsInventoryLevel;

    @FXML
    private TableColumn<Part, Double> asscPartsPrice;
    @FXML
    private Label lblDisplay;

    /** Sends data from Add Product Controller to Modify Product Controller.
     @param product Receives data from Add Product table.
     */
    
    public void sendProduct(Product product){

        idProductText.setText(String.valueOf(product.getId()));
        nameProductText.setText(product.getName());
        invProductText.setText(String.valueOf(product.getStock()));
        priceProductText.setText(String.valueOf(product.getPrice()));
        maxProductText.setText(String.valueOf(product.getMax()));
        minProductText.setText(String.valueOf(product.getMin()));
        asscPartsTable.setItems(product.getAssociatedParts());
        asscPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        asscPartsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asscPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /** This method saves an associated part when the Add button is clicked.
     @param event Saves an associated part to the Associated Parts table.
     */

    @FXML
    void onActionAddAssociatedPart(ActionEvent event){

        Part selection = partsTable.getSelectionModel().getSelectedItem();
        asscPartsTable.getItems().add(selection);
        asscPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        asscPartsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asscPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /** This method removes an associated part when the Remove button is clicked.
     @param event Removes an associated part from the Associated Parts table.
     */

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event){
        int id = Integer.parseInt(idProductText.getText());
        String name = nameProductText.getText();
        double price = Double.parseDouble(priceProductText.getText());
        int stock = Integer.parseInt(invProductText.getText());
        int min = Integer.parseInt(minProductText.getText());
        int max = Integer.parseInt(maxProductText.getText());
        Product product = new Product(id,name,price,stock,min,max);

        if(product.deleteAssociatedPart(asscPartsTable.getSelectionModel().getSelectedItem())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to remove this part?",ButtonType.OK,ButtonType.CANCEL);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Remove");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                lblDisplay.setText("");
                asscPartsTable.getItems().remove(asscPartsTable.getSelectionModel().getSelectedItem());
            }else {
                lblDisplay.setText("Didn't Delete.");

            }
        }
    }

    /** This method saves the product when the Save button is clicked.
     @param event Saves a product in the Products table and returns to the Main Menu.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

        String displayString = "",L1 ="",L2="",L3="",L4="",L5="",L6="",L7="",L8="";
        int stock=0, max=0,min=0;
        lblDisplay.setText("Exception: ");
        try{

            int id = Integer.parseInt(idProductText.getText());
            String name = nameProductText.getText();
            double price = Double.parseDouble(priceProductText.getText());
            stock = Integer.parseInt(invProductText.getText());
            min = Integer.parseInt(minProductText.getText());
            max = Integer.parseInt(maxProductText.getText());
            if(nameProductText.getText().isEmpty()){
                throw new IOException();
            }
            if(min > max || (stock > max || stock <min))
            {
                if(min> max){
                    throw new IOException();
                }
                if(stock > max || stock <min){
                    throw new IOException();
                }
            }
        Product product = new Product(id,name,price,stock,min,max);
        if(!asscPartsTable.getItems().isEmpty()) {

            product.getAssociatedParts().clear();
            ObservableList<Part> partObservableList = FXCollections.observableArrayList(asscPartsTable.getItems());
            for(Part selection: partObservableList){

                product.addAssociatedPart(selection);
            }

        }
        Inventory.updateProduct(id, product);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        }catch(NumberFormatException e){
            if(!(nameProductText.getText().matches("[a-zA-Z]+")) || nameProductText.getText().isEmpty()){
                if(nameProductText.getText().isEmpty()){
                    L6 = "No Data in a Name Field.\n";
                }else{
                    L6 ="Name can't be an Integer.\n";
                }
            }
            if(!(invProductText.getText().matches("[0-9]+"))){
                displayString ="Inventory is not an Integer.\n";

            }
            if(!(priceProductText.getText().matches("\\d+\\d.\\d+"))){
                L1 =  "Price is not a Double.\n";

            }
            if(!(maxProductText.getText().matches("[0-9]+"))){
                L2 = "Maximum is not an Integer.\n";

            }
            if(!(minProductText.getText().matches("[0-9]+"))) {
                L3 = "Minimum is not an Integer.\n";

            }
            lblDisplay.setVisible(true);
            lblDisplay.setText(lblDisplay.getText()+L6+displayString+L1+L2+L3);
            lblDisplay.setWrapText(true);
            lblDisplay.setTextAlignment(TextAlignment.LEFT);
        }catch (IOException e) {
            lblDisplay.setVisible(true);
            if(nameProductText.getText().isEmpty()){
                L5 = "No Data in Name Field.\n";
                lblDisplay.setText(lblDisplay.getText()+L5);
            }
            if(min > max || stock >= max || stock <= min)
            {
                if(min> max){
                    L7="Max must be less than Min.\n";
                }
                if(stock >= max || stock <= min){
                    L8 ="Inv must be between Max and Min.\n";
                }
                lblDisplay.setText(lblDisplay.getText()+L7+L8);
            }

        }
    }

    /** This method cancels the product when the Cancel button is clicked.
     @param event Cancels the product in the Add Product window and returns to the Main Menu.
     */
    @FXML
    void onActionCancelProduct(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method searches for the Part in the Add Product window.
     @param keyEvent Highlights or filters out the product when the Enter key is pressed.
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

    /** This method initializes the Modify Product window. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
