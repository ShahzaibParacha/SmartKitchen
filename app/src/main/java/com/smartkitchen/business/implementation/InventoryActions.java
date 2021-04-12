package com.smartkitchen.business.implementation;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBInventory;

import java.util.ArrayList;

//Functions used for managing the inventory
public class InventoryActions implements IInventoryActions {

    //Access to the database, access to validation methods
    private IDBInventory inventoryDB = DBManager.getInventoryDB();
    private final IListValidation validation = new ListValidation();

    //Empty constructor for regular use
    public InventoryActions(){}

    //Constructor used for testing purposes
    public InventoryActions(IDBInventory inventoryDB){
        this.inventoryDB = inventoryDB;
    }

    //Adds an item to the inventory, validates the input
    @Override
    public void addToInventory(Item item) throws InvalidInputException {
        validation.containsItemInputs(item);
        inventoryDB.addToInventory(item);
    }

    //Updates the item in the database
    @Override
    public void updateInventoryItem(Item item) {
        inventoryDB.updateItem(item);
    }

    //Gets an item from the database based on the position
    @Override
    public Item getInventoryItem(int position) {
        return inventoryDB.getInventoryList().get(position);
    }

    //Finds an item with a matching string for name
    @Override
    public Item getItemByName(String name){
        Item item = null;
        for (Item x: getInventoryList()) {
            if(x.getName().equals(name)){
                item = x;
            }
        }
        return item;
    }

    //Returns the inventory db in ArrayList form
    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryDB.getInventoryList();
    }

    //Removes an item from the db
    @Override
    public void removeFromInventory(Item item) {
        inventoryDB.removeFromInventory(item);
    }

}
