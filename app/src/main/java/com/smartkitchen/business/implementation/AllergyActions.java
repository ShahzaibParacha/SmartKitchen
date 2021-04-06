package com.smartkitchen.business.implementation;

import android.widget.CheckBox;

import com.smartkitchen.business.interfaces.IAllergyActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

//Methods for determining and displaying allergy information
public class AllergyActions implements IAllergyActions {

    private final IListActions listActions = new ListActions();
    private final CheckBox nuts, soy, lactose, gluten, fish, eggs;

    public AllergyActions(CheckBox nuts, CheckBox soy, CheckBox lactose, CheckBox gluten, CheckBox fish, CheckBox eggs) {
        this.nuts = nuts;
        this.soy = soy;
        this.lactose = lactose;
        this.gluten = gluten;
        this.fish = fish;
        this.eggs = eggs;
    }

    //Gets allergy information and sets default state of checkboxes
    @Override
    public void setAllergies(Item item) {
        ArrayList<String> allergies = item.getAllergies();
        if (allergies != null) {
            if (listActions.isInList(allergies, Allergies.NUTS.getText()))
                nuts.setChecked(true);
            if (listActions.isInList(allergies, Allergies.SOY.getText()))
                soy.setChecked(true);
            if (listActions.isInList(allergies, Allergies.LACTOSE.getText()))
                lactose.setChecked(true);
            if (listActions.isInList(allergies, Allergies.GLUTEN.getText()))
                gluten.setChecked(true);
            if (listActions.isInList(allergies, Allergies.FISH.getText()))
                fish.setChecked(true);
            if (listActions.isInList(allergies, Allergies.EGGS.getText()))
                eggs.setChecked(true);
        }
    }

    //Takes current state of checkboxes and returns allergy information
    @Override
    public ArrayList<String> getAllergies() {
        ArrayList<String> allergies = new ArrayList<>();
        if (nuts.isChecked())
            allergies.add(Allergies.NUTS.getText());
        if (soy.isChecked())
            allergies.add(Allergies.SOY.getText());
        if (lactose.isChecked())
            allergies.add(Allergies.LACTOSE.getText());
        if (gluten.isChecked())
            allergies.add(Allergies.GLUTEN.getText());
        if (fish.isChecked())
            allergies.add(Allergies.FISH.getText());
        if (eggs.isChecked())
            allergies.add(Allergies.EGGS.getText());
        return allergies;
    }
}
