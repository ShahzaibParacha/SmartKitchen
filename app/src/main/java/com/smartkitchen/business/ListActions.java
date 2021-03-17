package com.smartkitchen.business;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class ListActions implements IListActions {

    public ListActions(){

    } // empty constructor: do nothing

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
}
