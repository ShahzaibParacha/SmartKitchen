package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class ListValidation {

    // validating item entries
    private Item item;

    // item is instantiated
    public ListValidation(Item item) {
        this.item = item;
    }

    // logic for threshold checks
    public boolean thresholdStatus() {
        if (item.getQuantity() < item.getThresholdQuantity())
            return true;
        return false;
    }

    // logic to check if entry for items are valid (name, quantity, units, threshold)
    public void containsItemInputs() throws InvalidInputException {
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
}
