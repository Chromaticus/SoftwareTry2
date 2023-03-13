package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * responsible for the creation of Products
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *Constructor for Product.
     *  * @param id The id of the product.
     *  * @param name The name of the product.
     *  * @param price The price of the product.
     *  * @param stock The stock of the product.
     *  * @param min The minimum stock of the product.
     *  * @param max The maximum stock of the product.
     *  */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name =name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the id of the product.
     * @return The id of the product.
     */
    public int getId(){ return id;}

    /**
     * Sets the id of the product.
     * @param id The id to set for the product.
     */
    public void setId(int id){this.id =id;}

    /**
     * Returns the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name The name to set for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product.
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price The price to set for the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the stock of the product.
     * @return The stock of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock of the product.
     * @param stock The stock to set for the product.
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Returns the minimum stock of the product.
     * @return The minimum stock of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum stock of the product.
     * @param min The minimum stock to set for the product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the maximum stock of the product.
     * @return The maximum stock of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum stock of the product.
     * @param max The maximum stock to set for the product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the list of associated parts for the product.
     * @param part The part to add to the list of associated parts.
     */
    public void addAssociatedParts(Part part){
        associatedParts.add(part);
    }

    /**
     * Removes the selected associated part from the product.
     * @param selectedAssociatedPart The part to be removed from the associated parts list.
     * @return true if the removal was successful, false otherwise.
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
       return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns a copy of the list of all associated parts.
     * @return An ObservableList containing all the associated parts of the product.
     * */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    /**
     * Adds a new associated part to the product.
     * @param part The part to be added to the associated parts list.
     * */
    public void  addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

}



