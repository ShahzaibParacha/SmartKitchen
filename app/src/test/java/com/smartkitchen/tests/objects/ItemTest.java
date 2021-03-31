package com.smartkitchen.tests.objects;

import org.junit.Test;

import com.smartkitchen.objects.Item;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ItemTest{

    @Test
    public void testItemConstructor(){
        System.out.println("\nStarting testItemConstructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertNotNull(testItem);
        assertEquals(testItem.getName(), "sampleItem");
        assertEquals(testItem.getQuantity(), 1);
        assertEquals(testItem.getUnits(), "sampleUnit");
        assertEquals(testItem.getQuantityToBuy(), 1);
        
        System.out.println("Finished testItemConstructor.");
    }

    @Test
    public void testItemConstructor2(){

        System.out.println("\nStarting testItemConstructor2.");

        //Act
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1, testAllergies, 1, 1);
        testItem.setId(1);

        //Assert
        assertNotNull(testItem);
        assertEquals(testItem.getName(), "sampleItem");
        assertEquals(testItem.getQuantity(), 1);
        assertEquals(testItem.getUnits(), "sampleUnit");
        assertEquals(testItem.getQuantityToBuy(), 1);
        assertEquals(testItem.getId(), 1);
        assertEquals(testItem.getAllergies(), testAllergies);

        //test setters & getters
        ArrayList<String> testAllergies2 = new ArrayList<String>();
        testAllergies.add("testAllergy2");

        testItem.setAllergies(testAllergies2);
        testItem.setCaloriesPerUnit(2);
        testItem.setPricePerUnit(2);

        assertEquals(testItem.getAllergies(), testAllergies2);
        assertEquals(testItem.getCaloriesPerUnit(), 2);
        assertTrue(testItem.getPricePerUnit() == 2);

        System.out.println("Finished testItemConstructor2.");
    }

    @Test 
    public void testItemSetters(){
        System.out.println("\nStarting testItemSetters.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit", 1, 1);
        testItem.setName("sample2");
        testItem.setQuantity(2);
        testItem.setUnits("sampleUnit2");
        testItem.setQuantityToBuy(2);
        testItem.setThresholdQuantity(2);

        //Assert
        assertEquals(testItem.getName(), "sample2");
        assertEquals(testItem.getQuantity(), 2);
        assertEquals(testItem.getUnits(), "sampleUnit2");
        assertEquals(testItem.getQuantityString(), "2 sampleUnit2");
        assertEquals(testItem.getQuantityToBuyString(), "2 sampleUnit2");
        assertEquals(testItem.getThresholdQuantity(), 2);
        
        System.out.println("Finished testItemSetters.");
    }


    @Test
    public void testCalculateDefaultThreshold(){
        System.out.println("\nStarting testCalculateDefaultThreshold.");

        //Act
        Item testItem = new Item("sample", 10, "sampleUnit", 1, 1);

        //Assert -- we expect 2 because of the 20% threshold const;
        assertEquals(testItem.calculateDefaultThreshold(), 2);



        System.out.println("Finished testCalculateDefaultThreshold.");

    }
}


