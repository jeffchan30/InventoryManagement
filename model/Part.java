package model;


/**
    This is the Part class.
 */
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;


    }

    /**
     This method gets the ID.
     @return returns ID.
     */

    public int getId() {
        return id;
    }

    /**
     This method sets ID.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     This method gets the price.
     @return returns price.
     */

    public double getPrice() {
        return price;
    }

    /**
     This method sets the price.
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     This method gets the name.
     @return returns name.
     */

    public String getName() {
        return name;
    }

    /**
     This method sets the name.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     This method gets stock.
     @return returns stock.
     */

    public int getStock() {
        return stock;
    }

    /**
     This method sets the stock.
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     This method gets the minimum inventory.
     @return returns min.
     */

    public int getMin() {
        return min;
    }

    /**
     This method sets the minimum inventory.
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     This method gets the maximum inventory.
     @return returns max.
     */

    public int getMax() {
        return max;
    }

    /**
     This method sets the maximum inventory.
     */

    public void setMax(int max) {
        this.max = max;
    }

}
