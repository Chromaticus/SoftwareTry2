package Model;

/**
 * This class represents an outsourced part that extends the Part class.
 * It contains information about the company that supplies the part.
 * */
public class Outsourced  extends Part {

    /**
     * The name of the company that supplies the part.
     * */
    private String companyName;

    /**
     * Constructs an Outsourced part with the given parameters.
     * @param id the unique identifier for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current inventory level of the part
     * @param min the minimum inventory level for the part
     * @param max the maximum inventory level for the part
     * @param companyName the name of the company that supplies the part
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the name of the company that supplies the part.
     * @return the name of the company that supplies the part
     * */
    public String getCompanyName () {
        return companyName;
    }

    /**
     * Sets the name of the company that supplies the part.
     * @param companyName the name of the company that supplies the part
     * */
    public void setCompanyName ( String companyName){
        this.companyName = companyName;
    }


}
