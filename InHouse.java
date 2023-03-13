package Model;

/**
 * The InHouse class represents a specific type of part that is produced in-house.
 * It extends the Part class and adds a field for machineId.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     *Constructs a new InHouse part with the specified parameters.
     * @param id The unique ID of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The current stock level of the part.
     * @param min The minimum stock level for the part.
     * @param max The maximum stock level for the part.
     * @param machineId The ID of the machine used to produce this part.
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *Returns the ID of the machine used to produce this part.
     * @return The machine ID.
     * */
    public int getMachineId () {
        return machineId;
    }

    /**
     * Sets the ID of the machine used to produce this part.
     * @param machineId The machine ID to set.
     * */
    public void setMachineId ( int machineId){
        this.machineId = machineId;
    }
}