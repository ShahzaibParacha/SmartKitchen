package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class ListValidation {

    private Item item;
    private ArrayList<Item> inventoryList;
    private ArrayList<Item> groceryList;

    public ListValidation(Item item) {
        this.item = item;
        this.inventoryList = DBManager.getInventoryDB().getInventoryList();
        this.groceryList = DBManager.getGroceryDB().getGroceryList();
    }

    public boolean thresholdStatus() {
        if (item.getQuantity() < item.getThresholdQuantity())
            return true;
        return false;
    }

    // ???
    public boolean isGroceryListEmpty() {
        return groceryList.isEmpty();
    }

    public boolean isInventoryListEmpty() {
        return inventoryList.isEmpty();
    }

    // JUST IN CASE
//    public boolean containsSpecialCharacters(String name) {
//        boolean bool = true;
//        for (int i=0; i<name.length(); i++) {
//            if ((name.charAt(i)>64) && (name.charAt(i)<=90)             // uppercase letters
//                    && (name.charAt(i)>96) && (name.charAt(i)<=122)     // lowercase letters
//                    && (name.charAt(i)==32))                            // space
//            bool = false;
//        }
//        return bool;
//    }


}
