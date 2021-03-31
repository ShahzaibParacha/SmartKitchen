package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public interface IListActions {

    void editValidation(Item item) throws InvalidInputException;

    void editValidation(Recipe recipe) throws InvalidInputException;

    Item getDuplicateByName(Item item, ArrayList<Item> items);

    Recipe getDuplicateByName(Recipe recipe, ArrayList<Recipe> recipes);

    boolean isInList(ArrayList<String> list, String s);

}
