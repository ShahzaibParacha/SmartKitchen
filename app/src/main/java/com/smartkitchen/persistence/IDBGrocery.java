package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

//Interface to grocery database
public interface IDBGrocery {

    void addToGrocery(Item item);

    void removeFromGrocery(Item item);

    void updateItem(Item item);

    ArrayList<Item> getGroceryList();

}
