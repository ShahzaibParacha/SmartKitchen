package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IListActions {

    void addToGrocery(Item item) throws Exception;
    void addToInventory(Item item) throws Exception;

    void editValidation(Item item) throws Exception;

    Item getGroceryItem(int position);
    Item getInventoryItem(int position);

    Item getGroceryItemByName(String name);
    Item getInventoryItemByName(String name);

    boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain);

    void buyItem(Item item) throws Exception;

    void removeFromGrocery(Item item);
    void removeFromInventory(Item item);

    boolean isInInventory(Item item);
    boolean isInGrocery(Item item);

    boolean isInList(ArrayList<String> list, String s);
}
