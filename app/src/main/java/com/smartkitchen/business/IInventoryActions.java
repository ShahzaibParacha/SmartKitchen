package com.smartkitchen.business;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IInventoryActions {

    void addToInventory(Item item) throws InvalidInputException;

    void updateInventoryItem(Item item);

    Item getInventoryItem(int position);

    Item getItemByName(String name);

    ArrayList<Item> getInventoryList();

    void removeFromInventory(Item item);

    //Item getInventoryItemById(int itemId);
}
