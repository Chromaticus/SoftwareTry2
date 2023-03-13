package com.example.softwaretry2;

import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.InHouse;
import Model.Outsourced;
import static Model.Inventory.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Responsible for the adding part screen
 */
public class AddPartController implements Initializable {

    public MainScreenViewController mainScreenViewController;

    // Method to set the MainScreenViewController
    public void setMainScreenViewController(MainScreenViewController mainScreenViewController) {
        this.mainScreenViewController = mainScreenViewController;
    }
    @FXML
    private Label partIdNameLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField labelTextField;

    /**
     * Overrides the initialize method from the initializable interface.
     * Initializes the toggle group for the radio buttons, sets the in-house radio button to be selected,
     * and sets the part ID label to "Machine ID".
     *
     * @param location  The location of the resource.
     * @param resources The resource bundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(toggleGroup);
        outsourcedRadioButton.setToggleGroup(toggleGroup);
        inHouseRadioButton.setSelected(true);
        partIdNameLabel.setText("Machine ID");
    }

    /**
     * Retrieves the next available part ID by finding the highest ID among all existing parts and incrementing it.
     *
     * @return The next available part ID.
     */
    private int getNextPartId() {
        int maxId = 0;
        for (Part part : Model.Inventory.getAllParts()) {
            if (part.getId() > maxId) {
                maxId = part.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Adds the specified part to the list of all parts.
     *
     * @param newPart The part to be added.
     */
    public void addPart(Part newPart) {
        Model.Inventory.getAllParts().add(newPart);
    }

    /**
     * Event handler for the save button. Creates a new part based on user input and adds it to the list of all parts.
     * Closes the form after the part is added.
     *
     * @throws Exception If any of the input fields contain invalid values.
     */
    @FXML
    private void onSaveButtonClicked() {
        try {
            String name = nameTextField.getText();
            int stock = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());

            //checks to make sure min and inventory is valid before saving a part
            if (minValid(min, max) && inventoryValid(min, max, stock)) {

            // Create a new part based on whether the in-house or outsourced radio button is selected
            if (inHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(labelTextField.getText());
                InHouse newPart = new InHouse(getNextPartId(), name, price, stock, min, max, machineId);
                addPart(newPart);
            } else if (outsourcedRadioButton.isSelected()) {
                String companyName = labelTextField.getText();
                Outsourced newPart = new Outsourced(getNextPartId(), name, price, stock, min, max, companyName);
                addPart(newPart);
            }}

            // Close the form
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Event handler for the cancel button. Displays a confirmation alert, and closes the form if the user clicks "OK".
     *
     * @param event The event that triggered this method.
     * @throws IOException If the main screen view FXML file cannot be loaded.
     */
    @FXML
    void onCancelButtonClicked(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the stage and close it
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Checks if the minimum value is valid.
     *
     * @param min the minimum value.
     * @param max the maximum value.
     * @return true if the minimum value is valid, false otherwise.
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }

        return isValid;
    }
    /**
     * Checks if the inventory value is valid.
     *
     * @param min   the minimum value.
     * @param max   the maximum value.
     * @param stock the stock value.
     * @return true if the inventory value is valid, false otherwise.
     */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }

        return isValid;
    }

    /**
     * Changes the part ID label to "Machine ID" when the in-house radio button is clicked.
     *
     * @param event The event that triggered this method.
     */
    @FXML
    private void inHouseRadioButtonClicked(ActionEvent event) {
        partIdNameLabel.setText("Machine ID");
    }

    /**
     * Changes the part ID label to "Company Name" when the outsourced radio button is clicked.
     *
     * @param event The event that triggered this method.
     */
    @FXML
    private void outsourcedRadioButtonClicked(ActionEvent event) {
        partIdNameLabel.setText("Company Name");
    }

    /**
     * Displays an alert message with the specified alert type.
     *
     * @param alertType An integer representing the type of alert to display. 1: Blank or invalid fields, 2: Invalid machine ID value,
     *                  3: Invalid Min value, 4: Invalid Inventory value.
     */
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

