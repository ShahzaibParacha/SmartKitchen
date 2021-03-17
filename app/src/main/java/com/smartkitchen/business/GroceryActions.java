package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBGrocery;
import com.smartkitchen.presentation.AlertMessage;

import java.util.ArrayList;

public class GroceryActions implements IGroceryActions{

    private IDBGrocery groceryDB = DBManager.getGroceryDB();
    private IInventoryActions inventoryActions = new InventoryActions();
    private IListActions listActions = new ListActions();

    public GroceryActions(){}

    public GroceryActions(IDBGrocery groceryDB){
        this.groceryDB = groceryDB;
    }

    //Simple adds to either list
    @Override
    public void addToGrocery(Item item) throws Exception {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            groceryDB.addToGrocery(item);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public void updateGroceryItem(Item item) {
        groceryDB.updateItem(item);
    }

    //Simple gets from either list
    @Override
    public Item getGroceryItem(int position) {
        Item item = groceryDB.getGroceryList().get(position);
        return item;
    }

    @Override
    public ArrayList<Item> getGroceryList() {
        return groceryDB.getGroceryList();
    }

    //Simple removes from either list
    @Override
    public void removeFromGrocery(Item item) {
        groceryDB.removeFromGrocery(item);
    }

    @Override
    public double getGroceryListTotal() {
        ArrayList<Item> groceryList = groceryDB.getGroceryList();
        double total = 0.0;
        for (Item item : groceryList) {
            total += item.getPricePerUnit() * (double)item.getQuantityToBuy();
        }
        return total;
    }

    //Deals with adding an item to the grocery list via the threshold method
    @Override
    public boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain) {
        boolean enteredThreshold = false;
        ListValidation validation = new ListValidation(item);
        // Check if quantity<threshold
        if (validation.thresholdStatus()) {
            //Pull up prompt for quantity to buy
            AlertMessage.showDialog(context, item, returnToMain);
            enteredThreshold = true;
        }
        return enteredThreshold;
    }

    //"Buys" an item, i.e. moves it into inventory
    @Override
    public void buyItem(Item item) throws Exception {
        //If the item is already in inventory, sum current quantity and quantity to buy
        Item duplicate = listActions.getDuplicateByName(item, inventoryActions.getInventoryList());
        if(duplicate != null){
            duplicate.setQuantity(duplicate.getQuantity()+item.getQuantityToBuy());
            inventoryActions.updateInventoryItem(duplicate);
        }
        //If not, set the quantity as quantity to buy and add it
        else {
            item.setQuantity(item.getQuantityToBuy());
            try {
                inventoryActions.addToInventory(item);
            } catch (Exception e) {
                throw e;
            }
        }
        //Remove the item from the grocery list
        removeFromGrocery(item);
    }

    @Override
    public void buyAll() throws Exception {
        ArrayList<Item> groceryList = groceryDB.getGroceryList();
        for (Item item : groceryList) {
            buyItem(item);
        }
    }

//    @Override
//    public Item getGroceryItemById(int itemId) {
//        return groceryDB.getGroceryItemById(itemId);
//    }
}
