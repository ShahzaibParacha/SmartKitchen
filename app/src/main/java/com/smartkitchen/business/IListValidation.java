package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

public interface IListValidation {

    boolean thresholdStatus(Item item);

    void containsItemInputs(Item item) throws InvalidInputException;

    void containsRecipeInputs(Recipe recipe) throws InvalidInputException;

}
