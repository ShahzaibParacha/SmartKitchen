package com.smartkitchen.tests.business;

import android.widget.CheckBox;

import com.smartkitchen.business.implementation.AllergyActions;
import com.smartkitchen.objects.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

//just copied over the unit test since this layer does not interact with the persistence layer
//reason: code coverage for Integration Tests automation
public class AllergyActionsIT{
    private AllergyActions testTarget;
    Object nuts, soy, lactose, gluten, fish, eggs;

    @Before
    public void setup(){
        testTarget = new AllergyActions((CheckBox) nuts, (CheckBox)soy, (CheckBox)lactose, (CheckBox)gluten, (CheckBox)fish, (CheckBox)eggs);
    }

    @Test
    public void testSetAllergies(){
        System.out.println("\nStarting testSetAllergies");

        ArrayList<String> testAllergies = new ArrayList<>();

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        testTarget.setAllergies(testItem);

        System.out.println("end testSetAllergies");
    }
}