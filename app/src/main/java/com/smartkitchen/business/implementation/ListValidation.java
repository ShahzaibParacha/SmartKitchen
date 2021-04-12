package com.smartkitchen.business.implementation;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

// Methods for validating user input
public class ListValidation implements IListValidation {

    // logic for threshold checks
    public boolean thresholdStatus(Item item) {
        return item.getQuantity() < item.getThresholdQuantity();
    }

    // logic to check if entry for items are valid (name, quantity, units, threshold)
    public void containsItemInputs(Item item) throws InvalidInputException {
        if (item.getName().length() <= 0)
            throw new InvalidInputException("Please enter a valid input for name.");
        if (item.getQuantity() < 0)
            throw new InvalidInputException("Please enter a valid input for quantity.");
        if(item.getQuantityToBuy() < 0)
            throw new InvalidInputException("Please enter a valid input for quantity to buy.");
        if (item.getUnits().length() <= 0)
            throw new InvalidInputException("Please enter a valid input for units.");
        if (item.getThresholdQuantity() < 0)
            throw new InvalidInputException("Please enter a valid input for threshold quantity.");
        if (item.getPricePerUnit() < 0)
            throw new InvalidInputException("Please enter a valid input for price per unit.");
        if (item.getCaloriesPerUnit() < 0)
            throw new InvalidInputException("Please enter a valid input for calories per unit.");
    }

    // logic to check if entry for recipes are valid (name, at least 1 ingredient and instruction)
    public void containsRecipeInputs(Recipe recipe) throws InvalidInputException {
        if (recipe.getName().length() <= 0)
            throw new InvalidInputException("Please enter a valid input for name.");
        if(recipe.getIngredients().size() <= 0)
            throw new InvalidInputException("A recipe must have at least one ingredient.");
        if(recipe.getInstructions().size() <= 0)
            throw new InvalidInputException("A recipe must have at least one instruction.");
    }

    // logic to check if input for ingredients is valid
    public void checkIngredientInputs(String name, String quantity, String units) throws InvalidInputException{
        if(name.length() <= 0)
            throw new InvalidInputException("Please enter a valid input for name.");
        if(quantity.length() <= 0)
            throw new InvalidInputException("Please enter a valid input for quantity.");
        if(Integer.parseInt(quantity) < 0)
            throw new InvalidInputException("Please enter a valid input for quantity.");
        if(units.length() <= 0)
            throw new InvalidInputException("Please enter a valid input for units." );
    }

    // logic to check if input for instructions is valid
    public void checkInstructionInput(String instruction) throws InvalidInputException{
        if(instruction.length() <= 0){
            throw new InvalidInputException("Please enter a valid input for instruction.");
        }
    }
}
