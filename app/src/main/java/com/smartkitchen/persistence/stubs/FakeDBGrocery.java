package com.smartkitchen.persistence.stubs;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBGrocery;

import java.util.ArrayList;

//Fake DB implementation of grocery database, used for testing purposes
public class FakeDBGrocery implements IDBGrocery {

    private final ArrayList<Item> groceryList;
    private final IListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBGrocery() {
        this.groceryList = new ArrayList<>();
        this.validation = new ListValidation();
    }

    // Method validates input and checks whether the item contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToGrocery(Item item) {
        try {
            validation.containsItemInputs(item);
            groceryList.add(item);
        } catch (InvalidInputException e) {
            // This is where UI would pop a toast message with a warning for the user
            System.out.println(e.getMessage());
        }
    }

    // Method removes specified item from grocery list
    @Override
    public void removeFromGrocery(Item item) {
        groceryList.remove(item);
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

}
