package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;
import java.util.ArrayList;

public class FakeDBInventory implements IDBInventory{

    private ArrayList<Item> inventoryList;
    //private ListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBInventory(){
        inventoryList = new ArrayList<Item>();
    }

    // Overriding the methods provided by the interface (parent class)

    // Method instantiates validation object and checks whether the items contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToInventory(Item item) { inventoryList.add(item); }

    // Method removes specified item from grocery list
    @Override
    public Item removeFromInventory(Item item) {
        inventoryList.remove(item);
        return item;
    }

    // Getter
    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }
}
