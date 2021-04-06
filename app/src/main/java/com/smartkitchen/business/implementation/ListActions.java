package com.smartkitchen.business.implementation;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

//Generic functions used for all lists
public class ListActions implements IListActions {

    private final IListValidation validation;

    public ListActions(){
        validation = new ListValidation();
    } // empty constructor: do nothing

    //Validates inputs when an inventory or grocery item is edited
    @Override
    public void editValidation(Item item) throws InvalidInputException {
        validation.containsItemInputs(item);
    }

    //Validates inputs when a recipe is edited
    @Override
    public void editValidation(Recipe recipe) throws InvalidInputException {
        validation.containsRecipeInputs(recipe);
    }

    //Returns an item with the same name if one exists
    @Override
    public Item getDuplicateByName(Item item, ArrayList<Item> items) {
        Item existingItem = null;
        for (Item x:items) {
            if(existingItem == null && x.getName().equals(item.getName()))
                existingItem = x;
        }
        return existingItem;
    }

    //Returns a recipe with the same name if one exists
    @Override
    public Recipe getDuplicateByName(Recipe recipe, ArrayList<Recipe> recipes) {
        Recipe existingRecipe = null;
        for (Recipe x:recipes) {
            if(existingRecipe == null && x.getName().equals(recipe.getName()))
                existingRecipe = x;
        }
        return existingRecipe;
    }

    //Checks if a given string is present in a given list
    @Override
    public boolean isInList(ArrayList<String> list, String s){
        boolean inList = false;
        for (String x:list) {
            if (x.equals(s)) {
                inList = true;
                break;
            }
        }
        return inList;
    }

    //Converts a string to an array list, separates by commas
    @Override
    public ArrayList<String> stringToList(String string) {
        ArrayList<String> list = new ArrayList<>();
        if (!string.equals("")) {
            String[] parsedString = string.split(",");
            list.addAll(Arrays.asList(parsedString));
        }
        return list;
    }

    //Converts an array list to a string
    @Override
    public String listToString(ArrayList<String> list) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1)
                string.append(list.get(i)).append(",");
            else
                string.append(list.get(i));
        }
        return string.toString();
    }
}
