package com.example.softwaretry2;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Responsible for the modifying products screen.
 * RUNTIME ERROR: the Associated Parts Table would lie and show parts that didn't exist, and wouldn't update the table
 * until you pressed 'Add' twice. I fixed this by removing my example data i initialized to run from startup.
 */

public class ModProductController implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableView<Part> partTableView;
    private Product selectedProduct;
    @FXML private TextField partSearchText;
    @FXML private TextField idTextField;
    @FXML private TextField productIdText;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;

    @FXML private TextField productMaxText;
    @FXML private TextField productMinText;
    @FXML private TextField productInventoryText;
    @FXML private TextField productPriceText;
    @FXML private TextField productNameText;
    @FXML private Button saveProductButton;
    @FXML private Button removeProductButton;
    @FXML private Button addProductButton;
    @FXML private Button cancelProductButton;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    private Product product;

    /**
     * Sets the product to display in the product form.
     * Updates the text fields with the product's information.
     * @param product the product to display
     */
    public void setProduct(Product product) {
        this.product = product;
        idTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        inventoryTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        maxTextField.setText(String.valueOf(product.getMax()));
        minTextField.setText(String.valueOf(product.getMin()));
    }

    /**
     * Handles the save button click event.
     * Attempts to create a new product with the information entered in the form.
     * Adds the product to the inventory and returns to the main screen if successful.
     * @param event the ActionEvent representing the save button click event
     * @throws IOException if an I/O error occurs while attempting to return to the main screen
     */
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(productIdText.getText());
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            boolean productAddSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(7);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {
                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    if (minValid(min, max) && inventoryValid(min, max, stock)) {
                        Inventory.addProduct(newProduct);
                        productAddSuccessful = true;
                    }

                    if (productAddSuccessful) {
                        Inventory.deleteProduct(selectedProduct);
                        returnToMainScreen(event);
                    }

                }
            }

        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
     * Handles the remove button click event.
     * Removes the selected associated part from the product and updates the associated part table view.
     * Displays an alert if no part is selected.
     * @param event the ActionEvent representing the remove button click event
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
     * This method handles the action event that occurs when the user clicks on the "Add" button.
     * If a part is selected from the part table view, the selected part is added to the associated parts list
     * and displayed in the associated parts table view.
     * If no part is selected, an alert message is displayed to the user.
     * @param event  the event that triggered this method
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
     * This method is called during the initialization of the "Modify Product" screen.
     * It sets up the screen to display the current details of the selected product,
     * including its ID, name, stock, price, min and max values, and the list of associated parts.
     *
     * @param location The URL location of the FXML file.
     * @param resources The resources used by the FXML file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedProduct = MainScreenViewController.getProductToMod();
        associatedParts = selectedProduct.getAllAssociatedParts();

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

        productIdText.setText(String.valueOf(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        productInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));

    }

    /**
     * Handles the cancel button click event by displaying an alert to confirm the cancellation and returns to the main screen if confirmed.
     *
     * @param event The action event triggered by the cancel button click
     * @throws IOException if an I/O error occurs
     */
    @FXML
    void onCancelButtonClicked(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);

        }
    }

    /**
     * Returns to the main screen by loading the Main-Screen-View.fxml file and displaying it.
     *
     * @param event The action event triggered by the cancel button click
     * @throws IOException if an I/O error occurs
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("Main-Screen-View.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  Handles the search parts text field change event by searching all the parts
     *  containing the specified search string in their ID or name,
     *  and displays the search result in the part table view.
     * @param event The action event triggered by the search parts text field
     */
    @FXML void onSearchPartsTextFieldChanged(ActionEvent event) {

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
     * Validates if the given minimum value is valid.
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the minimum value is valid, false otherwise
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
     * Validates if the given inventory is valid.
     * @param min the minimum value
     * @param max the maximum value
     * @param stock the current stock value
     * @return true if the inventory is valid, false otherwise
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
     *Displays an alert dialog with the given type.
     * @param alertType the type of alert to display
     * */
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
}
