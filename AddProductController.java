package com.example.softwaretry2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;

/**
 * Responsible for the adding product screen
 */
public class AddProductController implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML private Button saveProductButton;
    @FXML private Button removeProductButton;
    @FXML private Button addProductButton;
    @FXML private Button cancelProductButton;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField partSearchText;
    @FXML private TextField productIdText;
    @FXML private TextField productNameText;
    @FXML private TextField productInventoryText;
    @FXML private TextField productPriceText;
    @FXML private TextField productMaxText;
    @FXML private TextField productMinText;
    /**
     * Returns an observable list of all parts in the inventory.
     *
     * @return An observable list of all parts in the inventory.
     */
    public static ObservableList<Part> getAllParts(){
        return getAllParts();
    }


    /**
     * Returns the next available product ID by iterating over all existing products in the inventory and
     * finding the maximum ID.
     *
     * @return The next available product ID.
     */
    private int getNextProductId() {
        int maxId = 0;
        for (Product product : Model.Inventory.getAllProducts()) {
            if (product.getId() > maxId) {
                maxId = product.getId();
            }
        }
        return maxId + 1;
    }
    /**
     * Adds the selected part to the associated parts list for the current product.
     *
     * @param event The event triggered by clicking the Add button.
     */
    @FXML void onAddButtonClicked(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();


        if (selectedPart == null) {
            displayAlert(5);
        } else {
            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);
        }
    }
    /**
     * Cancels changes and returns to the main screen when the cancel button is clicked.
     * Displays a confirmation alert before closing the current window.
     *
     * @param event the event object representing the button click
     * @throws IOException if an I/O error occurs
     */
    @FXML
    void onCancelButtonClicked(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the stage and close it
            Stage stage = (Stage) cancelProductButton.getScene().getWindow();
            stage.close();
            //returnToMainScreen(event);
        }
    }
    /**
     * Updates the part table view based on the search string entered in the search text field.
     * Displays an alert if no parts match the search string.
     *
     * @param event the event object representing the text field change
     */
    @FXML
    void onSearchPartsTextFieldChanged(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(2);
        }
    }
    /**
     * Handles the key pressed event of the part search text field. Resets the part table view to show
     * all parts if the search text field is empty.
     *
     * @param event The key event triggered by a key press in the search text field.
     */
    @FXML
    void onPartSearchKeyClicked(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }
    /**
     * Handles the remove button click event. Displays a confirmation alert to ask the user if they
     * want to remove the selected part from the associated parts list. Removes the part if the user
     * confirms.
     *
     * @param event The action event triggered by the remove button click.
     */
    @FXML
    void onRemoveButtonClicked(ActionEvent event) {

        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartTableView.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves the product with the user-entered information and adds it to the inventory.
     * @param event The ActionEvent triggered by clicking the "Save" button.
     * @throws IOException If an I/O exception occurs while saving the Product.
     */

    @FXML
     void onSaveButtonClicked(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (name.isEmpty()) {
                displayAlert(7);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    Product newProduct = new Product(getNextProductId(), name, price, stock, min, max);

                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    //returnToMainScreen(event);
                    Stage stage = (Stage) saveProductButton.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception e){
            displayAlert(1);
            //e.printStackTrace();
        }
    }

    /**
     * Returns to the Main Screen.
     * @param event The ActionEvent triggered by clicking the "Cancel" button.
     * @throws IOException If an I/O exception occurs while loading the Main-Screen-View.fxml file.
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("Main-Screen-View.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
     * Displays an alert dialog box with the specified message.
     *
     * @param alertType the type of the alert.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
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
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }
    /**
     * Initializes the partTableView and associatedPartTableView tables.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());


        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTableView.setItems(associatedParts);

    }
}

