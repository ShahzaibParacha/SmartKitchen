package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;

public interface IListActions {

    void addToGrocery(Item item);
    void addToInventory(Item item);

    Item getGroceryItem(int position);
    Item getInventoryItem(int position);

    Item getGroceryItemByName(String name);
    Item getInventoryItemByName(String name);

    boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain);

    void buyItem(Item item);

    void removeFromGrocery(Item item);
    void removeFromInventory(Item item);

    boolean isInInventory(Item item);
    boolean isInGrocery(Item item);
}
