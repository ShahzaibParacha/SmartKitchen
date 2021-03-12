package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IDBInventory {
    // Interface of Grocery (parent class)

    // Signature for methods is set here
    void addToInventory(Item item);

    Item removeFromInventory(Item item);

    void updateItem(Item item);

    ArrayList<Item> getInventoryList();

    Item getInventoryItemByName(String name);

}
