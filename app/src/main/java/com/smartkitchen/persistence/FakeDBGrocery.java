package com.smartkitchen.persistence;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class FakeDBGrocery implements IDBGrocery {

    private ArrayList<Item> groceryList;
    private ListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBGrocery(){
        groceryList = new ArrayList<Item>();
    }

    // Overriding the methods provided by the interface (parent class)

    // Method instantiates validation object and checks whether the items contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToGrocery(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            groceryList.add(item);
        }
        catch (Exception e) {
            // This is where UI can pop a new layer which notifies user of error input
            System.out.println(e.getMessage());
        }
    }

    // Method removes specified item from grocery list
    @Override
    public Item removeFromGrocery(Item item) {
        groceryList.remove(item);
        return item;
    }

    @Override
    public void updateItem(Item item) {
        //Doesn't do anything, only for actual db implementation
    }

    // Getter
    @Override
    public ArrayList<Item> getGroceryList() {
        return groceryList;
    }

//    @Override
//    public Item getGroceryItemById(int itemId) {
//        return null;
//    }
}
