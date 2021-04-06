package com.smartkitchen.business.interfaces;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

public interface IListValidation {

    boolean thresholdStatus(Item item);

    void containsItemInputs(Item item) throws InvalidInputException;

    void containsRecipeInputs(Recipe recipe) throws InvalidInputException;

    void checkIngredientInputs(String name, String quantity, String units) throws InvalidInputException;

    void checkInstructionInput(String instruction) throws InvalidInputException;
}
