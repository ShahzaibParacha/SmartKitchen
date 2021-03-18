package com.smartkitchen.business;

import android.content.Context;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public interface IListActions {

    void editValidation(Item item) throws InvalidInputException;

    Item getDuplicateByName(Item item, ArrayList<Item> items);

    boolean isInList(ArrayList<String> list, String s);

}
