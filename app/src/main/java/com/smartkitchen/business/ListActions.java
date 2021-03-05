package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.presentation.AlertMessage;

public class ListActions implements IListActions {


    @Override
    public void addToGrocery(Item item) {
        DBManager.getGroceryDB().addToGrocery(item);
    }

    @Override
    public void addToInventory(Item item) {
        DBManager.getInventoryDB().addToInventory(item);
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
    public Item getInventoryItemByName(String name) {
        Item item = DBManager.getInventoryDB().getInventoryItemByName(name);
        return item;
    }

    @Override
    public void buyItem(Item item) {
        if(isInInventory(item)){
            item.setQuantity(item.getQuantity()+item.getQuantityToBuy());
        }
        else {
            item.setQuantity(item.getQuantityToBuy());
            addToInventory(item);
        }
        removeFromGrocery(item);
    }

    @Override
    public boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain) {
        boolean enteredThreshold = false;
        ListValidation validation = new ListValidation(item);
        // get the grocery item and check if current item is already in grocery list
        if (validation.thresholdStatus()) {
            //If not already in grocery list, add to the grocery list
            if (!isInGrocery(item)) {
                AlertMessage.showDialog(context, item, returnToMain);
                enteredThreshold = true;
            }
        }
        return enteredThreshold;
    }

    @Override
    public void removeFromGrocery(Item item) {
        DBManager.getGroceryDB().removeFromGrocery(item);
    }

    @Override
    public void removeFromInventory(Item item) {
        DBManager.getInventoryDB().removeFromInventory(item);
    }

    @Override
    public boolean isInInventory(Item item) {
        boolean exists = false;
        for(Item x:DBManager.getInventoryDB().getInventoryList()){
            if(x.equals(item)){
                exists = true;
            }
        }
        return exists;
    }

    @Override
    public boolean isInGrocery(Item item) {
        boolean exists = false;
        for(Item x:DBManager.getGroceryDB().getGroceryList()){
            if(x.equals(item)){
                exists = true;
            }
        }
        return exists;
    }
}
