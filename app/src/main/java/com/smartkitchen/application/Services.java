package com.smartkitchen.application;

import com.smartkitchen.persistence.IDBGrocery;
import com.smartkitchen.persistence.IDBRecipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.IDBInventory;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.RecipePersistenceDB;

//Class to initialize the database
public class Services {
    private static IDBGrocery groceryPersistence = null;
    private static IDBInventory inventoryPersistence = null;
    private static IDBRecipe recipePersistence = null;

    //Each method returns the database, if it hasn't been created then initialize it first
    public static synchronized IDBGrocery getGroceryPersistence() {
        if (groceryPersistence == null) {
            groceryPersistence = new GroceryPersistenceDB(Initialize.getDBPathName());
        }

        return groceryPersistence;
    }

    public static synchronized IDBInventory getInventoryPersistence() {
        if (inventoryPersistence == null) {
            inventoryPersistence = new InventoryPersistenceDB(Initialize.getDBPathName());
        }

        return inventoryPersistence;
    }

    public static synchronized IDBRecipe getRecipePersistence() {
        if (recipePersistence == null) {
            recipePersistence = new RecipePersistenceDB(Initialize.getDBPathName());
        }

        return recipePersistence;
    }

}
