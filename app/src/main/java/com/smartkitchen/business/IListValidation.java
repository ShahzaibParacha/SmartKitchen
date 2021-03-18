package com.smartkitchen.business;

import com.smartkitchen.objects.Item;

public interface IListValidation {
    boolean thresholdStatus(Item item);
    void containsItemInputs(Item item) throws InvalidInputException;
}
