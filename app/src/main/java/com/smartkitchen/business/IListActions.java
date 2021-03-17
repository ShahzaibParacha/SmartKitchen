package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public interface IListActions {

    void addToGrocery(Item item) throws Exception;
    void addToInventory(Item item) throws Exception;

    void updateInventoryItem(Item item);
    void updateGroceryItem(Item item);

    void editValidation(Item item) throws Exception;

    Item getGroceryItem(int position);
    Item getInventoryItem(int position);

    ArrayList<Item> getGroceryList();
    ArrayList<Item> getInventoryList();

//    Item getInventoryItemById(int itemId);
//    Item getGroceryItemById(int itemId);

//    Item getGroceryItemByName(String name);
//    Item getInventoryItemByName(String name);

    boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain);

    void buyItem(Item item) throws Exception;
    void buyAll() throws Exception;

    void removeFromGrocery(Item item);
    void removeFromInventory(Item item);

//    boolean isInInventory(Item item);
//    boolean isInGrocery(Item item);

    Item getDuplicateByName(Item item, ArrayList<Item> items);

    boolean isInList(ArrayList<String> list, String s);

    double getGroceryListTotal();
}
