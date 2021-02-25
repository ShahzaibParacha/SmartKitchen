package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

public class DBManager {

    static final IDBInventory inventoryDB = new FakeDBInventory();
    static final IDBGrocery groceryDB = new FakeDBGrocery();

    public static void initialize(){
        inventoryDB.addToInventory(new Item("Milk", 4, "L", 0, 2));
        inventoryDB.addToInventory(new Item("Sugar", 100, "g", 0, 2));
        inventoryDB.addToInventory(new Item("Pizza", 10, "Boxes", 0, 2));

        //groceryDB.addToGrocery(new Item("Milk", 4, "L", 8, 2));
        groceryDB.addToGrocery(new Item("Sugar", 100, "g", 200, 2));
        groceryDB.addToGrocery(new Item("Pizza", 10, "Boxes", 5, 2));
    }

    public static IDBInventory getInventoryDB(){
        return inventoryDB;
    }

    public static IDBGrocery getGroceryDB(){
        return groceryDB;
    }
}
