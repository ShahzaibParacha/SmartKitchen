package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

//Interface to inventory database
public interface IDBInventory {

    void addToInventory(Item item);

    void removeFromInventory(Item item);

    void updateItem(Item item);

    ArrayList<Item> getInventoryList();

}
