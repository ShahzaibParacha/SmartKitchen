package com.smartkitchen.persistence;

import com.smartkitchen.application.Initialize;
import com.smartkitchen.application.Services;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;

import java.util.ArrayList;

public class DBManager {

    //Initializing fake data bases
//    static final IDBInventory inventoryDB = new FakeDBInventory();
//    static final IDBGrocery groceryDB = new FakeDBGrocery();
    static final IDBInventory inventoryDB = Services.getInventoryPersistence();
    static final IDBGrocery groceryDB = Services.getGroceryPersistence();
    static final IDBRecipe recipeDB = Services.getRecipePersistence();

    //Initial items to test
//    public static void initialize(){
//        inventoryDB.addToInventory(new Item("Milk", 4, "L", 0, 2, null, 0, 0));
//        inventoryDB.addToInventory(new Item("Sugar", 100, "g", 0, 2, null, 0, 0));
//        inventoryDB.addToInventory(new Item("Pizza", 10, "Boxes", 0, 2, null, 0, 0));
//
//        groceryDB.addToGrocery(new Item("Sugar", 100, "g", 200, 2, null, 0, 0));
//        groceryDB.addToGrocery(new Item("Pizza", 10, "Boxes", 5, 2, null, 0, 0));
//    }

    public static void initialize(){
        ArrayList<String> ingredients1 = new ArrayList<>();
        ingredients1.add("Macaroni");
        ingredients1.add("Cheese");
        ArrayList<String> ingredientQuantities1 = new ArrayList<>();
        ingredientQuantities1.add("1");
        ingredientQuantities1.add("2");
        ArrayList<String> ingredientUnits1 = new ArrayList<>();
        ingredientUnits1.add("Pack");
        ingredientUnits1.add("g");
        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("Step 1: add Mac");
        steps1.add("Step 2: add cheese");
        recipeDB.addToRecipes(new Recipe("Mac&Cheese", ingredients1, ingredientQuantities1,
                ingredientUnits1, steps1));
    }

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
