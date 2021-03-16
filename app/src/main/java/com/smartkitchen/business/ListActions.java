package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.*;
import com.smartkitchen.presentation.AlertMessage;

import java.util.ArrayList;

public class ListActions implements IListActions {
    private IDBGrocery groceryDB = DBManager.getGroceryDB();
    private IDBInventory inventoryDB = DBManager.getInventoryDB();

    public ListActions(){

    } // empty constructor: do nothing

    //constructor for testing
    public ListActions(IDBGrocery groceryinput, IDBInventory inventoryinput){
        //this();
        this.groceryDB = groceryinput;
        this.inventoryDB = inventoryinput;
    }

    //Simple adds to either list
    @Override
    public void addToGrocery(Item item) throws Exception {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            Item existingItem = getDuplicateByName(item, groceryDB.getGroceryList());
            if(existingItem == null)
                groceryDB.addToGrocery(item);
            else
                existingItem.setQuantityToBuy(existingItem.getQuantityToBuy()+item.getQuantityToBuy());
        }
        catch(Exception e){
            throw e;
        }

    }

    @Override
    public void addToInventory(Item item) throws Exception {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            Item existingItem = getDuplicateByName(item, inventoryDB.getInventoryList());
            if(existingItem == null)
                inventoryDB.addToInventory(item);
            else
                existingItem.setQuantity(existingItem.getQuantity()+item.getQuantity());
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public void updateInventoryItem(Item item) {
        inventoryDB.updateItem(item);
    }

    @Override
    public void updateGroceryItem(Item item) {
        groceryDB.updateItem(item);
    }


    @Override
    public void editValidation(Item item) throws Exception {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
        }
        catch(Exception e){
            throw e;
        }
    }

    //Simple gets from either list
    @Override
    public Item getGroceryItem(int position) {
        Item item = groceryDB.getGroceryList().get(position);
        return item;
    }

    @Override
    public Item getInventoryItem(int position) {
        Item item = inventoryDB.getInventoryList().get(position);
        return item;
    }

    @Override
    public ArrayList<Item> getGroceryList() {
        return groceryDB.getGroceryList();
    }

    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryDB.getInventoryList();
    }


//    @Override//   public Item getInventoryItemById(int itemId) {
//        return inventoryDB.getInventoryItemById(itemId);
//    }
//
//    @Override
//    public Item getGroceryItemById(int itemId) {
//        return groceryDB.getGroceryItemById(itemId);
//    }

//    @Override
//    public Item getInventoryItemById(int itemId) {
//        return DBManager.getInventoryDB().getInventoryItemById(itemId);
//    }
//
//    @Override
//    public Item getGroceryItemById(int itemId) {
//        return DBManager.getGroceryDB().getGroceryItemById(itemId);
//    }


//    //Get an item via their name string
//    @Override
//    public Item getGroceryItemByName(String name) {
//        Item item = DBManager.getGroceryDB().getGroceryItemByName(name);
//        return item;
//    }
//
//    @Override
//    public Item getInventoryItemByName(String name) {
//        Item item = DBManager.getInventoryDB().getInventoryItemByName(name);
//        return item;
//    }

    //"Buys" an item, i.e. moves it into inventory
    @Override
    public void buyItem(Item item) throws Exception {
        //If the item is already in inventory, sum current quantity and quantity to buy
        Item duplicate = getDuplicateByName(item, inventoryDB.getInventoryList());
        if(duplicate != null){
            duplicate.setQuantity(duplicate.getQuantity()+item.getQuantityToBuy());
            updateInventoryItem(duplicate);
        }
        //If not, set the quantity as quantity to buy and add it
        else {
            item.setQuantity(item.getQuantityToBuy());
            try {
                addToInventory(item);
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

    //Deals with adding an item to the grocery list via the threshold method
    @Override
    public boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain) {
        boolean enteredThreshold = false;
        ListValidation validation = new ListValidation(item);
        // Check if quantity<threshold
        if (validation.thresholdStatus()) {
            //If not already in grocery list, add to the grocery list
            if (getDuplicateByName(item, groceryDB.getGroceryList()) == null) {
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
        groceryDB.removeFromGrocery(item);
    }

    @Override
    public void removeFromInventory(Item item) {
        inventoryDB.removeFromInventory(item);
    }


//    //Checks for if an item is in the inventory/grocery list
//    @Override
//    public boolean isInInventory(Item item) {
//        boolean exists = false;
//        for(Item x:DBManager.getInventoryDB().getInventoryList()){
//            if(x.equals(item)){
//                exists = true;
//            }
//        }
//        return exists;
//    }
//
//    @Override
//    public boolean isInGrocery(Item item) {
//        boolean exists = false;
//        for(Item x:DBManager.getGroceryDB().getGroceryList()){
//            if(x.equals(item)){
//                exists = true;
//            }
//        }
//        return exists;
//    }


    @Override
    public Item getDuplicateByName(Item item, ArrayList<Item> items) {
        Item existingItem = null;
        for (Item x:items) {
            if(existingItem == null && x.getName().equals(item.getName()))
                existingItem = x;
        }
        return existingItem;
    }

    @Override
    public boolean isInList(ArrayList<String> list, String s){
        boolean inList = false;
        for (String x:list) {
            if(x.equals(s))
                inList = true;
        }
        return inList;
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
}
