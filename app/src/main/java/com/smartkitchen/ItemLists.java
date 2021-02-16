package com.smartkitchen;
import java.util.ArrayList;

public class ItemLists {
    public static ArrayList<Item> inventoryList = new ArrayList<Item>();
    public static ArrayList<Item> groceryList = new ArrayList<Item>();

    public void addToInventory(Item item) {
        inventoryList.add(item);
    }

    public void addToGrocery(Item item) {
        groceryList.add(item);
    }

    public Item removeFromInventory(Item item) {
        inventoryList.remove(item);
        return item;
    }

    public Item removeFromGrocery(Item item) {
        groceryList.remove(item);
        return item;
    }

    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }

    public ArrayList<Item> getGroceryList() {
        return groceryList;
    }

    public Object testing() {
        return null;
    }
}
