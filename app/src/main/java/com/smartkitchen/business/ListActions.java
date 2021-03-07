package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.presentation.AlertMessage;

public class ListActions implements IListActions {

    //Simple adds to either list
    @Override
    public void addToGrocery(Item item) {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            DBManager.getGroceryDB().addToGrocery(item);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addToInventory(Item item) {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            DBManager.getInventoryDB().addToInventory(item);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    //Simple gets from either list
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

    //Get an item via their name string
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

    //"Buys" an item, i.e. moves it into inventory
    @Override
    public void buyItem(Item item) {
        //If the item is already in inventory, sum current quantity and quantity to buy
        if(isInInventory(item)){
            item.setQuantity(item.getQuantity()+item.getQuantityToBuy());
        }
        //If not, set the quantity as quantity to buy and add it
        else {
            item.setQuantity(item.getQuantityToBuy());
            addToInventory(item);
        }
        //Remove the item from the grocery list
        removeFromGrocery(item);
    }

    //Deals with adding an item to the grocery list via the threshold method
    @Override
    public boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain) {
        boolean enteredThreshold = false;
        ListValidation validation = new ListValidation(item);
        // Check if quantity<threshold
        if (validation.thresholdStatus()) {
            //If not already in grocery list, add to the grocery list
            if (!isInGrocery(item)) {
                //Pull up prompt for quantity to buy
                AlertMessage.showDialog(context, item, returnToMain);
                enteredThreshold = true;
            }
        }
        return enteredThreshold;
    }

    //Simple removes from either list
    @Override
    public void removeFromGrocery(Item item) {
        DBManager.getGroceryDB().removeFromGrocery(item);
    }

    @Override
    public void removeFromInventory(Item item) {
        DBManager.getInventoryDB().removeFromInventory(item);
    }

    //Checks for if an item is in the inventory/grocery list
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
