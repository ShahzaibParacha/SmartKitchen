package com.smartkitchen.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Item {
    private final double DEFAULT_THRESHOLD_CONST = 0.2;

    //Basic fields
    private String name;
    private int quantity;               // quantity in inventory
    private String units;
    private int quantityToBuy;          // quantity in grocery
    private int thresholdQuantity;

    //ADDITIONS
    private ArrayList<String> allergies;
    private int caloriesPerUnit;
    private double pricePerUnit;

    //Flags for if the cardview should be expanded in either list
    private boolean invIsExpanded = false;
    private boolean groceryIsExpanded = false;

    //Constructor
    public Item(String name, int quantity, String units, int quantityToBuy, int thresholdQuantity,
                ArrayList<String> allergies, int caloriesPerUnit, double pricePerUnit) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
        this.quantityToBuy = quantityToBuy;
        this.thresholdQuantity = thresholdQuantity;
        this.allergies = allergies;
        this.caloriesPerUnit = caloriesPerUnit;
        this.pricePerUnit = pricePerUnit;
    }

    //Secondary Constructor
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

    public void setQuantity(int quantity){
        this.quantity = quantity;
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

    public void setThresholdQuantity(int thresholdQuantity) { this.thresholdQuantity = thresholdQuantity; }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }

    public int getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(int caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int calculateDefaultThreshold(){
        return (int)(quantity*DEFAULT_THRESHOLD_CONST);
    }

    public boolean invIsExpanded() {
        return invIsExpanded;
    }

    public void setInvIsExpanded(boolean invIsExpanded) {
        this.invIsExpanded = invIsExpanded;
    }

    public boolean groceryIsExpanded() {
        return groceryIsExpanded;
    }

    public void setGroceryIsExpanded(boolean groceryIsExpanded) {
        this.groceryIsExpanded = groceryIsExpanded;
    }
}
