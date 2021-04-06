package com.smartkitchen.persistence.stubs;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBInventory;

import java.util.ArrayList;

public class FakeDBInventory implements IDBInventory {

    private ArrayList<Item> inventoryList;
    private IListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBInventory(){
        this.inventoryList = new ArrayList<>();
        this.validation = new ListValidation();
    }

    // Overriding the methods provided by the interface (parent class)

    // Method instantiates validation object and checks whether the items contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToInventory(Item item) {
        try {
            validation.containsItemInputs(item);
            inventoryList.add(item);
        }
        catch (InvalidInputException e) {
            // this is where UI can pop a new layer which notifies user of error input
            System.out.println(e.getMessage());
        }
    }

    // Method removes specified item from grocery list
    @Override
    public Item removeFromInventory(Item item) {
        inventoryList.remove(item);
        return item;
    }

    @Override
    public void updateItem(Item item) {
        //Doesn't do anything, only for actual db implementation
    }

    // Getter
    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }

//    @Override
//    public Item getInventoryItemById(int itemId) {
//        return null;
//    }
}
