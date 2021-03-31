package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBInventory;

import java.util.ArrayList;

public class InventoryActions implements IInventoryActions {

    private IDBInventory inventoryDB = DBManager.getInventoryDB();
    private IListValidation validation = new ListValidation();

    public InventoryActions(){}

    public InventoryActions(IDBInventory inventoryDB){
        this.inventoryDB = inventoryDB;
    }

    @Override
    public void addToInventory(Item item) throws InvalidInputException {
        try{
            validation.containsItemInputs(item);
            inventoryDB.addToInventory(item);
        }
        catch(InvalidInputException e){
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
    public Item getItemByName(String name){
        Item item = null;
        for (Item x: getInventoryList()) {
            if(x.getName().equals(name)){
                item = x;
            }
        }
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

}
