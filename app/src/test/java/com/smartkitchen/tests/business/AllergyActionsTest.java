package com.smartkitchen.tests.business;

import android.widget.CheckBox;

import com.smartkitchen.business.implementation.AllergyActions;
import com.smartkitchen.business.interfaces.IAllergyActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;

import org.junit.After;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

public class AllergyActionsTest{
    private AllergyActions testTarget;
    //private AllergyActions mockTarget;
    CheckBox nuts, soy, lactose, gluten, fish, eggs;

    @Before
    public void setup(){
        testTarget = new AllergyActions(nuts, soy, lactose, gluten, fish, eggs);
        //mockTarget = mock(AllergyActions.class);
    }

    @Test
    public void testSetAllergies(){
        System.out.println("\nStarting testSetAllergies");

        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("Nuts");
        testAllergies.add("Soy");
        testAllergies.add("Lactose");
        testAllergies.add("Gluten");
        testAllergies.add("Fish");
        testAllergies.add("Eggs");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        testTarget.setAllergies(testItem);

        System.out.println("end testSetAllergies");
    }

    @Test
    public void testGetAllergies(){

    }

}