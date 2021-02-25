package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

public class DBManager {

    //Initializing fake data bases
    static final IDBInventory inventoryDB = new FakeDBInventory();
    static final IDBGrocery groceryDB = new FakeDBGrocery();

    //Initial items to test
    public static void initialize(){
        inventoryDB.addToInventory(new Item("Milk", 4, "L", 0, 2));
        inventoryDB.addToInventory(new Item("Sugar", 100, "g", 0, 2));
        inventoryDB.addToInventory(new Item("Pizza", 10, "Boxes", 0, 2));

        groceryDB.addToGrocery(new Item("Sugar", 100, "g", 200, 2));
        groceryDB.addToGrocery(new Item("Pizza", 10, "Boxes", 5, 2));
    }

    //Getters
    public static IDBInventory getInventoryDB(){
        return inventoryDB;
    }

    public static IDBGrocery getGroceryDB(){
        return groceryDB;
    }
}
