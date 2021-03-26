package com.smartkitchen.objects;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> ingredientQuantities;
    private int totalCalories;
    private ArrayList<String> instructions;
    private boolean haveAllIngredients;
    private boolean isExpanded = false;

    public Recipe(String name, ArrayList<String> ingredients, ArrayList<String> ingredientQuantities, ArrayList<String> instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.ingredientQuantities = ingredientQuantities;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setIngredientQuantities(ArrayList<String> ingredientQuantities) {
        this.ingredientQuantities = ingredientQuantities;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public boolean haveAllIngredients() {
        return haveAllIngredients;
    }

    public void setHaveAllIngredients(boolean haveAllIngredients) {
        this.haveAllIngredients = haveAllIngredients;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
