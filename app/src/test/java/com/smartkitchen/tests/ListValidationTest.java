package com.smartkitchen.tests;

import org.junit.Test;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;

import static org.junit.Assert.*;

public class ListValidationTest{

    @Test
    public void testListValidation(){
        System.out.println("\nStarting testListValidation constructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        ListValidation testListValidation = new ListValidation(testItem);

        //Assert
        assertNotNull(testListValidation);
        System.out.println("Finished testListValidation constructor.");
    }

    @Test
    public void testThresholdStatus(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        ListValidation testListValidation = new ListValidation(testItem);

        //Assert
        assertFalse(testListValidation.thresholdStatus());
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testThresholdStatusTrue(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 5);
        ListValidation testListValidation = new ListValidation(testItem);

        //Assert
        assertTrue(testListValidation.thresholdStatus());
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testIsEmpty(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        ListValidation testListValidation = new ListValidation(testItem);

        //Assert
        System.out.println("Finished testThresholdStatus.");
    }
}

