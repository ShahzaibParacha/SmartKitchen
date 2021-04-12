package com.smartkitchen.objects;

import java.util.ArrayList;

//Object class for food items, used in inventory and grocery list
public class Item {

    //Basic fields
    private String name;
    private int quantity;               // quantity in inventory
    private String units;
    private int quantityToBuy;          // quantity in grocery
    private int thresholdQuantity;      // quantity at which to restock

    //Additional Fields
    private ArrayList<String> allergies;
    private int caloriesPerUnit;
    private double pricePerUnit;
    private int id;

    //Main Constructor
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

    //Simpler Constructor, used for tests
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Returns a string of the quantity and units
    public String getQuantityString() {
        return "" + quantity + " " + units;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public String getQuantityToBuyString() {
        return "" + quantityToBuy + " " + units;
    }

    public int getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(int thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

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

    // method could be used as the default value for when user doesn't input a threshold value
    public int calculateDefaultThreshold() {
        double DEFAULT_THRESHOLD_CONST = 0.2;
        return (int) (quantity * DEFAULT_THRESHOLD_CONST);
    }
}
