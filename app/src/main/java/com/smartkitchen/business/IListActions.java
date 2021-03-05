package com.smartkitchen.business;

import com.smartkitchen.objects.Item;

public interface IListActions {

    void addToGrocery(Item item);
    void addToInventory(Item item);

    Item getGroceryItem(int position);
    Item getInventoryItem(int position);

    Item getGroceryItemByName(String name);

    void thresholdAddToGrocery(Item item);

    void removeFromGrocery(Item item);
    void removeFromInventory(Item item);
}
