package com.smartkitchen;

public class Item {
    private final double DEFAULT_THRESHOLD_CONST = 0.2;

    //Basic fields
    private String name;
    private int quantity;               // updates current quantity
    private String units;
    private int quantityToBuy;
    private int thresholdQuantity;

    //Constructor
    public Item(String name, int quantity, String units, int quantityToBuy, int thresholdQuantity) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
        this.quantityToBuy = quantityToBuy;
        this.thresholdQuantity = thresholdQuantity;
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

    public int getThresholdQuantity() { return thresholdQuantity; }

    public void setThresholdQuantityQuantity(int thresholdQuantity) { this.thresholdQuantity = thresholdQuantity; }

    public int calculateDefaultThreshold(){
        return (int)(quantity*DEFAULT_THRESHOLD_CONST);
    }

    public boolean thresholdStatus() {
        if (quantity < thresholdQuantity)
            return true;
        return false;
    }
}
