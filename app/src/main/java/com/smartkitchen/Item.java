package com.smartkitchen;

public class Item {

    //Basic fields
    private String name;
    private int quantity;
    private String units;

    //Constructor
    public Item(String name, int quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
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
}
