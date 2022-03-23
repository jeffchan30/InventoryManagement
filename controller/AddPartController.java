package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** FUTURE ENHANCEMENTS
 If I create separate methods for text box validation, the code will be cleaner and
 reduce code redundancy for future updates and implementation of the application.
 */

/** RUNTIME ERRORS
 To catch runtime errors when it occurs when you enter wrong data types,
 I implemented exception handling in the OnActionSavePart method.

 */



/** This class initializes the Add Part window */

public class AddPartController implements Initializable {

    public Label machineIDLabel;
    public RadioButton inhouseButton;
    public RadioButton outsourcedButton;
    Stage stage;
    Parent scene;

    @FXML
    private TextField idPartText;

    @FXML
    private TextField namePartText;

    @FXML
    private TextField invPartText;

    @FXML
    private TextField pricePartText;

    @FXML
    private TextField maxPartText;

    @FXML
    private TextField minPartText;

    @FXML
    private TextField machineIDPartText;
    @FXML
    private Label lblDisplay;


    /** Part is in-house.
     @param actionEvent Label becomes Machine ID.
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
    }

    /** Part is outsourced.
     @param actionEvent Label becomes Company Name.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name");
    }

    /** This method saves a part when the Save button is clicked.
     @param event Saves part and returns to Main Menu.
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        String displayString = "",L1 ="",L2="",L3="",L4="",L5="",L6="",L7="",L8="";
       // double price = 0.00;
        int stock=0, max=0,min=0;
        lblDisplay.setText("Exception: ");
        try{

            int id = Integer.parseInt(idPartText.getText());
            String name = namePartText.getText();
            Double price = Double.parseDouble(pricePartText.getText());
            stock = Integer.parseInt(invPartText.getText());
            max = Integer.parseInt(maxPartText.getText());
            min = Integer.parseInt(minPartText.getText());
            if(namePartText.getText().isEmpty()){
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
            if (inhouseButton.isSelected()) {
                int machineID = Integer.parseInt(machineIDPartText.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));

            } else {
                String companyName = machineIDPartText.getText();
                Inventory.addPart((new Outsourced(id, name, price, stock, min, max, companyName)));

            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        catch(NumberFormatException e){
            if(!(namePartText.getText().matches("[a-zA-Z]+")) || namePartText.getText().isEmpty()){
                if(namePartText.getText().isEmpty()){
                    L6 = "No Data in a Name Field.\n";
                }else{
                      L6 ="Name can't be an Integer.\n";
                }
            }
            if(!(invPartText.getText().matches("[0-9]+"))){
                System.out.println("Inventory is not an Integer.");
                displayString ="Inventory is not an Integer.\n";

            }
            if(!(pricePartText.getText().matches("\\d+\\d.\\d+"))){
                L1 =  "Price is not a Double.\n";

            }
            if(!(maxPartText.getText().matches("[0-9]+"))){
                System.out.println("Maximum is not an Integer");
                L2 = "Maximum is not an Integer.\n";

            }
            if(!(minPartText.getText().matches("[0-9]+"))) {
                System.out.println("Minimum is not an Integer");
                L3 = "Minimum is not an Integer.\n";

            }
            if(!(machineIDPartText.getText().matches("[0-9]+"))){
                System.out.println("MachineID is not Integer");
                L4= "MachineID is not an Integer.\n";

            }
            lblDisplay.setVisible(true);
            lblDisplay.setText(lblDisplay.getText()+L6+displayString+L1+L2+L3+L4);
            lblDisplay.setWrapText(true);
            lblDisplay.setTextAlignment(TextAlignment.LEFT);
        } catch (IOException e) {
            lblDisplay.setVisible(true);
            if(namePartText.getText().isEmpty()){
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

    /** This method cancels a part when the Cancel button is clicked.
     @param event Cancels part and returns to Main Menu.
     */
    @FXML
    public void onActionCancelPart(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Allows the user to add parts. Part ID is auto-generated. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDisplay.setVisible(false);
        idPartText.setEditable(false);
        int countProductID = 0;
        for (Part part : Inventory.getAllParts()) {
            countProductID ++;
        }
        idPartText.setText(String.valueOf((countProductID +1 )));
        

    }

}
