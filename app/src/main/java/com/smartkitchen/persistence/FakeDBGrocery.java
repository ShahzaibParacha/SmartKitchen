package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class FakeDBGrocery implements IDBGrocery {

    private ArrayList<Item> groceryList;

    public FakeDBGrocery(){
        groceryList = new ArrayList<Item>();
    }

    @Override
    public void addToGrocery(Item item) {
        groceryList.add(item);
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
