package com.smartkitchen.persistence.stubs;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBInventory;

import java.util.ArrayList;

//Fake DB implementation of the inventory Database, used for testing purposes
public class FakeDBInventory implements IDBInventory {

    private final ArrayList<Item> inventoryList;
    private final IListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBInventory() {
        this.inventoryList = new ArrayList<>();
        this.validation = new ListValidation();
    }

    // Method validates input and checks whether the item contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToInventory(Item item) {
        try {
            validation.containsItemInputs(item);
            inventoryList.add(item);
        } catch (InvalidInputException e) {
            // this is where UI can display a toast message with a warning for the user
            System.out.println(e.getMessage());
        }
    }

    // Method removes specified item from inventory
    @Override
    public void removeFromInventory(Item item) {
        inventoryList.remove(item);
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

}
