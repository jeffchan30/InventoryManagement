package model;

/** This is the In House class. */
public class InHouse extends Part {

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This method gets the Machine ID.
    @return Returns Machine ID.
    */
    public int getMachineID() {
        return machineID;
    }

    /** This method sets Machine ID. */

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
