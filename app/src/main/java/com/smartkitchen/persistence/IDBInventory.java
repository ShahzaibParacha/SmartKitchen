package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IDBInventory {
    // Interface of Grocery (parent class)

    // Signature for methods is set here
    public void addToInventory(Item item);

    public Item removeFromInventory(Item item);

    public ArrayList<Item> getInventoryList();

}
