package com.example.softwaretry2;

import Model.InHouse;
import Model.Outsourced;
import Model.Part;
import Model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Responsible for the modifying parts screen
 */
public class ModPartController implements Initializable {
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private Label partIdNameLabel;
    private Part selectedPart;
    //@FXML private Label partIdNameLabel;
    @FXML private RadioButton inHouseRadioButton;
    //@FXML private ToggleGroup tgPartType;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partIdNameText;
    @FXML private TextField partNameText;
    @FXML private TextField partInventoryText;
    @FXML private TextField partPriceText;
    @FXML private TextField partMaxText;
    @FXML private TextField partMinText;

    private Part part;

    /**
     * This method is called when the "In-House" radio button is clicked. It updates the "partIdNameLabel" label
     * to display "Machine ID".
     *
     * @param event The action event triggered by the "In-House" radio button being clicked.
     */
    @FXML void inHouseRadioButtonClicked(ActionEvent event) {

        partIdNameLabel.setText("Machine ID");
    }

    /**
     * This method is called when the "Outsourced" radio button is clicked. It updates the "partIdNameLabel" label
     * to display "Company Name".
     *
     * @param event The action event triggered by the "Outsourced" radio button being clicked.
     */
    @FXML void outsourcedRadioButtonClicked(ActionEvent event) {

        partIdNameLabel.setText("Company Name");
    }

    /**
     * Sets the values of the text fields to display the properties of the given part object.
     *
     * @param part The part object whose properties will be displayed.
     */
    public void setPart(Part part) {
        this.part = part;
        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(part.getName());
        inventoryTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));
    }

    /**
     * Initializes the controller class. Sets up a toggle group for the radio buttons and selects the appropriate
     * radio button and label depending on the type of the selected part object. Also sets the values of the text fields
     * to display the properties of the selected part object.
     *
     * @param location The location of the FXML file.
     * @param resources The resources used by the FXML file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(toggleGroup);
        outsourcedRadioButton.setToggleGroup(toggleGroup);

        selectedPart = MainScreenViewController.getPartToMod();

        if (selectedPart instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            partIdNameLabel.setText("Machine ID");
            partIdNameText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            outsourcedRadioButton.setSelected(true);
            partIdNameLabel.setText("Company Name");
            partIdNameText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partNameText.setText(selectedPart.getName());
        partInventoryText.setText(String.valueOf(selectedPart.getStock()));
        partPriceText.setText(String.valueOf(selectedPart.getPrice()));
        partMaxText.setText(String.valueOf(selectedPart.getMax()));
        partMinText.setText(String.valueOf(selectedPart.getMin()));
    }

    /**
     * This method returns the user to the main screen view when the corresponding button is clicked.
     *
     * @param event the event that triggers the method call
     * @throws IOException if an I/O error occurs
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("main-screen-view.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the save button is clicked. It saves the updated part information and returns the user
     * to the main screen view if the operation is successful.
     *
     * @param event the event that triggers the method call
     * @throws IOException if an I/O error occurs
     */
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                if (inHouseRadioButton.isSelected()) {
                    try {
                        machineId = Integer.parseInt(partIdNameText.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddSuccessful = true;
                    } catch (Exception e) {
                        displayAlert(2);
                    }
                }

                if (outsourcedRadioButton.isSelected()) {
                    companyName = partIdNameText.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if (partAddSuccessful) {
                    Inventory.deletePart(selectedPart);
                    returnToMainScreen(event);
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Returns to the main screen when the "Cancel" button is clicked after prompting the user for confirmation.
     * @param event the ActionEvent that triggered this method.
     * @throws IOException if the FXML file for the main screen cannot be found.
     */
    @FXML void onCancelButtonClicked(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * Checks if a given minimum value is valid for a part, which means that it is greater than zero and less than the maximum value.
     *@param min The minimum value to be checked.
     * @param max The maximum value allowed.
     * @return True if the minimum value is valid, false otherwise.
     * */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }

        return isValid;
    }

    /**
     * Checks if a given inventory value is valid for a part, which means that it is between the minimum and maximum values.
     * @param min The minimum inventory allowed.
     * @param max The maximum inventory allowed.
     * @param stock The inventory value to be checked.
     * @return True if the inventory value is valid, false otherwise.
     * */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }

        return isValid;
    }

    /**
     *Displays an alert dialog box with the given alert type and message.
     * @param alertType The type of alert to be displayed.
     * @throws IllegalArgumentException if the alertType is not a valid option.
     * */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
        }
    }




}
