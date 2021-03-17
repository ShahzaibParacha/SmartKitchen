package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBInventory;

import java.util.ArrayList;

public class InventoryActions implements IInventoryActions {

    private IDBInventory inventoryDB = DBManager.getInventoryDB();
    private IListActions listActions = new ListActions();

    public InventoryActions(){}

    public InventoryActions(IDBInventory inventoryDB){
        this.inventoryDB = inventoryDB;
    }

    @Override
    public void addToInventory(Item item) throws Exception {
        try{
            ListValidation validation = new ListValidation(item);
            validation.containsItemInputs();
            inventoryDB.addToInventory(item);
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
    public Item getInventoryItem(int position) {
        Item item = inventoryDB.getInventoryList().get(position);
        return item;
    }

    @Override
    public ArrayList<Item> getInventoryList() {
        return inventoryDB.getInventoryList();
    }

    @Override
    public void removeFromInventory(Item item) {
        inventoryDB.removeFromInventory(item);
    }

//    @Override
//    public Item getInventoryItemById(int itemId) {
//        return inventoryDB.getInventoryItemById(itemId);
//    }
}
