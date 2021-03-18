package com.smartkitchen.business;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;
import java.util.List;

public class ListActions implements IListActions {

    private IListValidation validation;

    public ListActions(){
        validation = new ListValidation();
    } // empty constructor: do nothing

    @Override
    public void editValidation(Item item) throws InvalidInputException {
        try{
            validation.containsItemInputs(item);
        }
        catch(InvalidInputException e){
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
