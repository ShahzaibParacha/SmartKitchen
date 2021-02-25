package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;
import java.util.ArrayList;

public interface IDBGrocery {

    public void addToGrocery(Item item);

    public Item removeFromGrocery(Item item);

    public ArrayList<Item> getGroceryList();

    public Item getGroceryItemByName(String name);
}
