package com.smartkitchen.persistence;

import com.smartkitchen.application.Services;

public class DBManager {

    static final IDBInventory inventoryDB = Services.getInventoryPersistence();
    static final IDBGrocery groceryDB = Services.getGroceryPersistence();
    static final IDBRecipe recipeDB = Services.getRecipePersistence();

    //Getters
    public static IDBInventory getInventoryDB(){
        return inventoryDB;
    }
    public static IDBGrocery getGroceryDB(){
        return groceryDB;
    }
    public static IDBRecipe getRecipeDB(){
        return recipeDB;
    }
}
