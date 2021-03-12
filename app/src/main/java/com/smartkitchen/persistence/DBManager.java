package com.smartkitchen.persistence;

import com.smartkitchen.application.Initialize;
import com.smartkitchen.application.Services;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;

public class DBManager {

    //Initializing fake data bases
//    static final IDBInventory inventoryDB = new FakeDBInventory();
//    static final IDBGrocery groceryDB = new FakeDBGrocery();
    static final IDBInventory inventoryDB = Services.getInventoryPersistence();
    static final IDBGrocery groceryDB = Services.getGroceryPersistence();

    //Initial items to test
//    public static void initialize(){
//        inventoryDB.addToInventory(new Item("Milk", 4, "L", 0, 2, null, 0, 0));
//        inventoryDB.addToInventory(new Item("Sugar", 100, "g", 0, 2, null, 0, 0));
//        inventoryDB.addToInventory(new Item("Pizza", 10, "Boxes", 0, 2, null, 0, 0));
//
//        groceryDB.addToGrocery(new Item("Sugar", 100, "g", 200, 2, null, 0, 0));
//        groceryDB.addToGrocery(new Item("Pizza", 10, "Boxes", 5, 2, null, 0, 0));
//    }

    //Getters
    public static IDBInventory getInventoryDB(){
        return inventoryDB;
    }

    public static IDBGrocery getGroceryDB(){
        return groceryDB;
    }
}
