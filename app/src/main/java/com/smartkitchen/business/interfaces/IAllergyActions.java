package com.smartkitchen.business.interfaces;

import com.smartkitchen.objects.Item;

import java.util.ArrayList;

//Interface to allergy methods
public interface IAllergyActions {

    void setAllergies(Item item);

    ArrayList<String> getAllergies();
}
