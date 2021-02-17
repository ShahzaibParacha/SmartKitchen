package com.smartkitchen;
import java.util.ArrayList;

public class ItemLists {

    private static ItemLists instance;

    private static ArrayList<Item> inventoryList;
    private static ArrayList<Item> groceryList;

    private ItemLists(){
        inventoryList = new ArrayList<>();
        groceryList = new ArrayList<>();
        initData();
    }

    private void initData(){
        inventoryList.add(new Item("Milk", 4, "L", 0));
        inventoryList.add(new Item("Sugar", 100, "g", 0));
        inventoryList.add(new Item("Pizza", 10, "Boxes", 0));

        groceryList.add(new Item("Milk", 4, "L", 8));
        groceryList.add(new Item("Sugar", 100, "g", 200));
        groceryList.add(new Item("Pizza", 10, "Boxes", 5));
    }

    public static ItemLists getInstance(){
        if (instance == null) {
            instance = new ItemLists();
        }
        return instance;
    }

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
