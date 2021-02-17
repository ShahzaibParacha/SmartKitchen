package com.smartkitchen;

public class Item {

    //Basic fields
    private String name;
    private int quantity;
    private String units;
    private int quantityToBuy;

    //Constructor
    public Item(String name, int quantity, String units, int quantityToBuy) {
        this.name = name;
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

    //Returns a string of the quantity and units
    public String getQuantityString(){
        return "" + quantity + " " + units;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
