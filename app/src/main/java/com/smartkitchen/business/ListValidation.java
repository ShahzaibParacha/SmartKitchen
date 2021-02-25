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

    public void containsItemInputs() throws Exception {
        if (item.getName().length() <= 0)
            throw new Exception("Need a valid string input for name.");
        if (item.getQuantity() <= 0)
            throw new Exception("Need a valid quantity input.");
        if (item.getUnits().length() <= 0)
            throw new Exception("Need a string input for units.");
        if (item.getThresholdQuantity() < 0)
            throw new Exception(("Need a valid threshold quantity input"));
    }
}
