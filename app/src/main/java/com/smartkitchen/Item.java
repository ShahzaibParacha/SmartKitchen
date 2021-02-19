package com.smartkitchen;

public class Item {
    private final double THRESHOLD_CONST = 0.2;

    //Basic fields
    private String name;
    private int quantity;               // updates current quantity
    private String units;
    private int quantityToBuy;
    private int initQuantity;           // initializes to store initial quantity

    //Constructor
    public Item(String name, int quantity, String units, int quantityToBuy) {
        this.name = name;
        this.initQuantity = quantity;
        this.quantity = quantity;
        this.units = units;
        this.quantityToBuy = quantityToBuy;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getInitQuantity() { return initQuantity; }

    //Returns a string of the quantity and units
    public String getQuantityString(){
        return "" + quantity + " " + units;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getQuantityToBuy(){
        return quantityToBuy;
    }

    public void setQuantityToBuy(int quantityToBuy){
        this.quantityToBuy = quantityToBuy;
    }

    public String getQuantityToBuyString(){return "" + quantityToBuy + " " + units;}
    public void setInitQuantity(int quantity) { this.initQuantity = quantity; }

    public void updateQuantity(int quantity) { this.quantity = quantity; }

    public boolean thresholdStatus() {
        if (quantity < initQuantity*THRESHOLD_CONST)
            return true;
        return false;
    }
}
