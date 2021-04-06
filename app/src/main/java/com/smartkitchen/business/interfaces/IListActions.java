package com.smartkitchen.business.interfaces;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

//Interface to generic list methods
public interface IListActions {

    void editValidation(Item item) throws InvalidInputException;

    void editValidation(Recipe recipe) throws InvalidInputException;

    Item getDuplicateByName(Item item, ArrayList<Item> items);

    Recipe getDuplicateByName(Recipe recipe, ArrayList<Recipe> recipes);

    boolean isInList(ArrayList<String> list, String s);

    ArrayList<String> stringToList(String string);

    String listToString(ArrayList<String> list);

}
