package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;
import java.util.ArrayList;

public interface IDBGrocery {
    // Interface of Grocery (parent class)

    // Signature for methods is set here
    void addToGrocery(Item item);

    Item removeFromGrocery(Item item);

    void updateItem(Item item);

    ArrayList<Item> getGroceryList();

    Item getGroceryItemByName(String name);
}
