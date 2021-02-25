package com.smartkitchen.persistence;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class FakeDBGrocery implements IDBGrocery {

    private ArrayList<Item> groceryList;
    private ListValidation validation;

    public FakeDBGrocery(){
        groceryList = new ArrayList<Item>();
    }

    @Override
    public void addToGrocery(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            groceryList.add(item);
        }
        catch (Exception e) {
            // this is where UI can pop a new layer which notifies user of error input
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Item removeFromGrocery(Item item) {
        groceryList.remove(item);
        return item;
    }

    @Override
    public ArrayList<Item> getGroceryList() {
        return groceryList;
    }

    @Override
    public Item getGroceryItemByName(String name) {
        Item item = null;
        for (Item x:groceryList) {
            if(x.getName().equals(name)){
                item = x;
            }
        }
        return item;
    }
}
