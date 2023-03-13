package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class is responsible for managing the collection of all parts and products.
 * */
public class Inventory {

    private static int productID =0;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *Returns a new unique product ID.
     * @return an integer representing the new product ID
     * */
    public static int getNewProductId() {
        return ++productID;
    }

    /**
     * Adds a new part to the list of all parts.
     * @param newPart the part to be added to the list
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the list of all products.
     * @param newProduct the product to be added to the list
     * */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Finds a part in the list of all parts by ID.
     * @param partId the ID of the part to be found
     * @return the part found with the given ID, or null if no such part exists
     * */
    public Part lookupPart(int partId){
        Part partFound = null;

        for (Part part: allParts){
            if (part.getId() == partId){
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     *Finds a product in the list of all products by ID.
     * @param productId the ID of the product to be found
     * @return the product found with the given ID, or null if no such product exists
     * */
    public Product lookupProduct(int productId){
        Product productFound = null;

        for (Product product: allProducts){
            if (product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * Finds all parts with names matching the provided search string.
     * @param partName the search string to match against part names
     * @return an ObservableList containing all parts with matching names, or an empty list if no matches were found
     * */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().equals(partName)){
                partsFound.add(part);
            }
        }

        if (partsFound.isEmpty()){
            return FXCollections.emptyObservableList();
        }

        return partsFound;
    }

    /**
     *Finds all products with names matching the provided search string.
     * @param productName the search string to match against product names
     * @return an ObservableList containing all products with matching names, or an empty list if no matches were found
     * */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                productsFound.add(product);
            }
        }

        if (productsFound.isEmpty()){
            return FXCollections.emptyObservableList();
        }

        return productsFound;
    }

    /**
     * Updates the part at the given index in the list of all parts with the provided part.
     * @param index the index of the part to be updated
     * @param selectedPart the updated part
     * */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     *Updates a product in inventory by its index.
     * @param index the index of the product to update.
     * @param selectedProduct the updated product.
     * */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     *  Removes the specified part from the allParts list if it exists.
     * @param selectedPart the part to be removed
     * @return true if the part was removed, false otherwise
     */
   public static boolean deletePart(Part selectedPart){
       if (allParts.contains(selectedPart)) {
           return allParts.remove(selectedPart);
       }
       return false;
   }

    /**
     * Removes the specified product from the allProducts list if it exists.
     * @param selectedProduct the product to be removed
     * @return true if the product was removed, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)) {
            return allProducts.remove(selectedProduct);
        }
        return false;
    }

    /**
     * Returns an ObservableList containing all the parts in the inventory.
     * @return an ObservableList containing all the parts in the inventory
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Returns an ObservableList containing all the products in the inventory.
     *
     * @return an ObservableList containing all the products in the inventory
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}