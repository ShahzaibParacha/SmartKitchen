package com.smartkitchen.business.implementation;

import android.content.Context;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBGrocery;
import com.smartkitchen.presentation.AlertMessage;

import java.util.ArrayList;

//Functions used for managing the grocery list
public class GroceryActions implements IGroceryActions {

    //Access to grocery database and required access to other business classes
    private IDBGrocery groceryDB = DBManager.getGroceryDB();
    private IInventoryActions inventoryActions = new InventoryActions();
    private final IListActions listActions = new ListActions();
    private final IListValidation validation = new ListValidation();

    //Empty constructor for regular use
    public GroceryActions(){
    }

    //Constructor used for testing purposes
    public GroceryActions(IDBGrocery groceryDB, IInventoryActions inventoryActions){
        this.groceryDB = groceryDB;
        this.inventoryActions = inventoryActions;
    }

    //Adds an item to the grocery list, validates the input
    @Override
    public void addToGrocery(Item item) throws InvalidInputException {
        validation.containsItemInputs(item);
        groceryDB.addToGrocery(item);
    }

    //Updates the item in the grocery db
    @Override
    public void updateGroceryItem(Item item) {
        groceryDB.updateItem(item);
    }

    //Gets an item from the grocery list based on the position
    @Override
    public Item getGroceryItem(int position) {
        return groceryDB.getGroceryList().get(position);
    }

    //Returns the entire grocery list in ArrayList form
    @Override
    public ArrayList<Item> getGroceryList() {
        return groceryDB.getGroceryList();
    }

    //Removes an item from the grocery db
    @Override
    public void removeFromGrocery(Item item) {
        groceryDB.removeFromGrocery(item);
    }

    //Determines the total cost of the items in the grocery list
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
        // Check if quantity < threshold
        if (validation.thresholdStatus(item)) {
            //Pull up prompt for quantity to buy
            AlertMessage.showDialog(context, item, returnToMain);
            enteredThreshold = true;
        }
        return enteredThreshold;
    }

    //"Buys" an item, i.e. moves it into inventory
    @Override
    public void buyItem(Item item) throws InvalidInputException {
        //If the item is already in inventory, sum current quantity and quantity to buy
        Item duplicate = listActions.getDuplicateByName(item, inventoryActions.getInventoryList());
        if(duplicate != null){
            duplicate.setQuantity(duplicate.getQuantity()+item.getQuantityToBuy());
            inventoryActions.updateInventoryItem(duplicate);
        }
        //If not, set the quantity as quantity to buy and add it
        else {
            item.setQuantity(item.getQuantityToBuy());
            inventoryActions.addToInventory(item);
        }
        //Remove the item from the grocery list
        removeFromGrocery(item);
    }

    //Buys all the items in the grocery list
    @Override
    public void buyAll() throws InvalidInputException {
        ArrayList<Item> groceryList = groceryDB.getGroceryList();
        for (Item item : groceryList) {
            buyItem(item);
        }
    }
}
