package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;
import java.util.ArrayList;

public class FakeDBInventory implements IDBInventory{

    private ArrayList<Item> inventoryList;


    public FakeDBInventory(){
        inventoryList = new ArrayList<Item>();
    }

    @Override
    public void addToInventory(Item item) {
        inventoryList.add(item);
    }

    @Override
    public Item removeFromInventory(Item item) {
        inventoryList.remove(item);
        return item;
    }

    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }
}
