package com.smartkitchen.persistence;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;
import java.util.ArrayList;

public class FakeDBInventory implements IDBInventory{

    private ArrayList<Item> inventoryList;
    private ListValidation validation;

    public FakeDBInventory(){
        inventoryList = new ArrayList<Item>();
    }

    @Override
    public void addToInventory(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            inventoryList.add(item);
        }
        catch (Exception e) {
            // this is where UI can pop a new layer which notifies user of error input
            System.out.println(e.getMessage());
        }
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
