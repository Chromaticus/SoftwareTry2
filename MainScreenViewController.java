package com.example.softwaretry2;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.function.Predicate;

import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;

/**
 * Responsible for the main screen
 */
public class MainScreenViewController implements Initializable {

    public void refresh() {
        // Clear the existing parts list
        allParts.clear();

        // Add all parts to the list
        allParts.addAll(getAllParts());

        // Refresh the parts table
        partsTable.refresh();

        // Clear the existing products list
        allProducts.clear();

        // Add all products to the list
        allProducts.addAll(getAllProducts());

        // Refresh the products table
        productsTable.refresh();
    }


/*    public void refresh() {
        // Refresh the parts table
        partsTable.setItems(getAllParts());

        // Refresh the products table
        productsTable.setItems(getAllProducts());

    }*/


    static MainScreenViewController instance;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Label partsLabel;
    @FXML private Label productsLabel;
    @FXML private TextField searchPartsTextField;
    @FXML private TextField searchProductsTextField;
    private static Part partToMod;
    private static Product productToMod;
    @FXML private Button exitButton;

    /**
     * Create an observable list to store all the parts.
     */
    @FXML private ObservableList<Part> allParts = FXCollections.observableArrayList(/*
            new InHouse(1, "Part A", 10.0, 5, 1, 10, 100),
            new InHouse(2, "Part B", 20.0, 10, 1, 10, 100),
            new Outsourced(3, "Part C", 30.0, 15, 1, 10, "Vendor A"),
            new Outsourced(4, "Part D", 40.0, 20, 1, 10, "Vendor B")*/
    );

    /**
     * Create a filtered list to hold the parts that match the search term.
     */
    private FilteredList<Part> filteredParts = new FilteredList<>(allParts);

    /**
     * Called when the search text field for parts is changed.
     * Filters the list of parts based on the search term and displays the filtered list in the parts table.
     */
    @FXML
    private void onSearchPartsTextFieldChanged() {
        String searchTerm = searchPartsTextField.getText().trim().toLowerCase();

        // If the search term is empty, show all parts
        if (searchTerm.isEmpty()) {
            partsTable.setItems(allParts);
        } else {
            // Filter the list based on the search term
            Predicate<Part> searchPredicate = part -> {
                String name = part.getName().toLowerCase();
                String id = Integer.toString(part.getId());
                return name.contains(searchTerm) || id.contains(searchTerm);
            };
            filteredParts.setPredicate(searchPredicate);
            partsTable.setItems(filteredParts);
        }
    }

    /**
     * Create an observable list to store all the products.
     */
    private ObservableList<Product> allProducts = FXCollections.observableArrayList(/*
            new Product(1, "Product A", 100.0, 5, 1, 10),
            new Product(2, "Product B", 200.0, 10, 1, 10),
            new Product(3, "Product C", 300.0, 15, 1, 10),
            new Product(4, "Product D", 400.0, 20, 1, 10)*/
    );

    // Create a filtered list to hold the products that match the search term
    private FilteredList<Product> filteredProducts = new FilteredList<>(allProducts);

    /**
     * Called when the search text field for products is changed.
     * Filters the list of products based on the search term and displays the filtered list in the products table.
     */
    @FXML
    private void onSearchProductsTextFieldChanged() {
        String searchTerm = searchProductsTextField.getText().trim().toLowerCase();

        // If the search term is empty, show all products
        if (searchTerm.isEmpty()) {
            productsTable.setItems(allProducts);
        } else {
            // Filter the list based on the search term
            Predicate<Product> searchPredicate = product -> {
                String name = product.getName().toLowerCase();
                String id = Integer.toString(product.getId());
                return name.contains(searchTerm) || id.contains(searchTerm);
            };
            filteredProducts.setPredicate(searchPredicate);
            productsTable.setItems(filteredProducts);
        }
    }


    /**
     * Initializes the main screen view controller by setting up the columns
     * in the parts and products tables, initializing the allParts and allProducts
     * lists, and setting data for the tables. This method is called automatically
     * when the FXML file is loaded.
     *
     * @param url The location of the FXML file
     * @param rb  The resource bundle associated with the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        // Set up the columns in the parts table
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // Initialize the allParts list
        allParts.setAll(Inventory.getAllParts());

        // Set data for TableView
        partsTable.setItems(Inventory.getAllParts());
        // Set up the columns in the products table
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        // Initialize the allProducts list
        allProducts.setAll(Inventory.getAllProducts());

        // Set data for TableView
        productsTable.setItems(Inventory.getAllProducts());


    }

    /**
     * Called when the "Add Part" button is clicked. Loads the "Add Part" view,
     * creates a new stage to display the view, and sets the main screen view
     * controller as the controller for the "Add Part" view. Also refreshes the
     * parts table to show any changes made to the parts list.
     *
     * @param event The action event associated with the button click
     * @throws IOException If an I/O error occurs while loading the FXML file
     */
    public void OnAddPartButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-part-view.fxml"));
        Parent root = loader.load();
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Part");
        addPartStage.setScene(new Scene(root));
        addPartStage.show();

        partsTable.refresh();

        AddPartController addPartController = loader.getController();
        addPartController.setMainScreenViewController(this);
    }

    /**
     * Handles the event when the Delete Part button is clicked. Deletes the selected part from the parts table
     * and updates the allParts list and partsTable accordingly.
     */
    @FXML
    private void OnDeletePartButtonClicked() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            // No part is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the selected part from the parts table
            //experiment

            Inventory.deletePart(selectedPart);
            partsTable.setItems(Inventory.getAllParts());


        }
    }

    /**
     * Handles the event when the Add Product button is clicked. Opens the Add Product view in a new stage
     * and refreshes the products table after the addition of the new product.
     */
    @FXML private void OnAddProductButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-product.fxml"));
            Parent root = fxmlLoader.load();
            AddProductController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            productsTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Delete Product" button is clicked.
     * Deletes the selected product from the products table and updates the allProducts list.
     * Displays an error message if the selected product has associated parts.
     */
    @FXML
    private void OnDeleteProductButtonClicked() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            // No product is selected, display an error message
            displayAlert(4);
            return;
        }
        // Check if the product has associated parts
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            // Product has associated parts, display an error message
            displayAlert(5);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the selected product from the products table

            Inventory.deleteProduct(selectedProduct);
            productsTable.setItems(Inventory.getAllProducts());

        }
    }

    /**
     * Returns the part to be modified.
     * @return  The part to be modified.
     */
    public static Part getPartToMod() {
        return partToMod;
    }
    /**
     * Returns the product to be modified.
     * @return  The product to be modified.
     */
    public static Product getProductToMod() {
        return productToMod;
    }

    /**
     * Called when the user clicks the modify part button.
     * Sets the selected part to be modified, and loads the mod-part view.
     * @param event the event that triggered this method
     * @throws IOException if the mod-part view cannot be loaded
     */
    @FXML
    void OnModifyPartButtonClicked(ActionEvent event) throws IOException {
        partToMod = partsTable.getSelectionModel().getSelectedItem();
        if (partToMod == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("mod-part.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            partsTable.refresh();
        }
    }

    /**
     * Called when the user clicks the modify product button.
     * Sets the selected product to be modified, and loads the mod-product view.
     * @param event the event that triggered this method
     * @throws IOException if the mod-product view cannot be loaded
     */
    @FXML
    void OnModifyProductButtonClicked(ActionEvent event) throws IOException {
        productToMod = productsTable.getSelectionModel().getSelectedItem();
        if (productToMod == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("mod-product.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            productsTable.refresh();
        }
    }

    /**
     * Handles the action when the exit button is clicked. Gets the stage and closes it.
     */
    @FXML
    private void OnExitButtonClicked() {
        // Get the stage and close it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert dialog box with the given type.
     *
     * @param alertType the type of alert to display
     *                  1: Part not found
     *                  2: Product not found
     *                  3: Part not selected
     *                  4: Product not selected
     *                  5: Parts Associated
     *                  All parts must be removed from product before deletion.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }


}

