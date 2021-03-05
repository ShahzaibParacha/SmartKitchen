package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

public class ListActions implements IListActions {


    @Override
    public void addToGrocery(Item item) {
        DBManager.getGroceryDB().addToGrocery(item);
    }

    @Override
    public void addToInventory(Item item) {
        DBManager.getInventoryDB().addToInventory(item);
        thresholdAddToGrocery(item);
    }

    @Override
    public Item getGroceryItem(int position) {
        Item item = DBManager.getGroceryDB().getGroceryList().get(position);
        return item;
    }

    @Override
    public Item getInventoryItem(int position) {
        Item item = DBManager.getInventoryDB().getInventoryList().get(position);
        return item;
    }

    @Override
    public Item getGroceryItemByName(String name) {
        Item item = DBManager.getGroceryDB().getGroceryItemByName(name);
        return item;
    }

    @Override
    public void thresholdAddToGrocery(Item item) {
        ListValidation validation = new ListValidation(item);
        // get the grocery item and check if current item is already in grocery list
        if (validation.thresholdStatus()) {
            Item groceryItem = DBManager.getGroceryDB().getGroceryItemByName(item.getName());
            //If not already in grocery list, add to the grocery list
            if (groceryItem == null) {
                item.setQuantityToBuy(item.getThresholdQuantity());
                DBManager.getGroceryDB().addToGrocery(item);
            }
        }
    }

    @Override
    public void removeFromGrocery(Item item) {
        DBManager.getGroceryDB().removeFromGrocery(item);
    }

    @Override
    public void removeFromInventory(Item item) {
        DBManager.getInventoryDB().removeFromInventory(item);
    }
}
