package com.smartkitchen.persistence;

import com.smartkitchen.application.Initialize;
import com.smartkitchen.application.Services;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBManager {

    //Initializing fake data bases
//    static final IDBInventory inventoryDB = new FakeDBInventory();
//    static final IDBGrocery groceryDB = new FakeDBGrocery();
    static final IDBInventory inventoryDB = Services.getInventoryPersistence();
    static final IDBGrocery groceryDB = Services.getGroceryPersistence();
    static final IDBRecipe recipeDB = new FakeDBRecipe();

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
        ArrayList<String> instructions1 = new ArrayList<>();
        instructions1.add("Boil Water");
        instructions1.add("Add Macaroni, stir for 10 minutes");
        instructions1.add("Add Milk, Butter and Cheese");
        instructions1.add("Yuuuuuuuuummmmmmmmm");
        recipeDB.addToRecipes(new Recipe("Macaroni & Cheese", ingredients1, ingredientQuantities1, instructions1));
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
