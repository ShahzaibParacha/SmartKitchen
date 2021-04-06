package com.smartkitchen.business.interfaces;

import android.content.Context;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public interface IGroceryActions {

    void addToGrocery(Item item) throws InvalidInputException;

    void updateGroceryItem(Item item);

    Item getGroceryItem(int position);

    ArrayList<Item> getGroceryList();

    double getGroceryListTotal();

    boolean thresholdAddToGrocery(Item item, Context context, boolean returnToMain);

    void buyItem(Item item) throws InvalidInputException;
    void buyAll() throws InvalidInputException;

    void removeFromGrocery(Item item);

}
