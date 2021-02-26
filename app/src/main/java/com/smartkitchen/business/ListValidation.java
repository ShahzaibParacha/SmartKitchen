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
    public void containsItemInputs() throws Exception {
        if (item.getName().length() <= 0)
            throw new Exception("Need a valid string input for name.");
        if (item.getQuantity() <= 0)
            throw new Exception("Need a valid quantity input.");
        if (item.getUnits().length() <= 0)
            throw new Exception("Need a string input for units.");
        if (item.getThresholdQuantity() < 0)
            throw new Exception("Need a valid threshold quantity input.");
    }
}
