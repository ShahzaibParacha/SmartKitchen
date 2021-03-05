package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;

public interface IListActions {

    void addToGrocery(Item item);
    boolean addToInventory(Item item, Context context);

    Item getGroceryItem(int position);
    Item getInventoryItem(int position);

    Item getGroceryItemByName(String name);

    boolean thresholdAddToGrocery(Item item, Context context);

    void removeFromGrocery(Item item);
    void removeFromInventory(Item item);
}
