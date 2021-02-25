package com.smartkitchen.persistence;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IDBInventory {

    public void addToInventory(Item item);

    public Item removeFromInventory(Item item);

    public ArrayList<Item> getInventoryList();

}
