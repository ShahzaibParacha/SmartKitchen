package com.smartkitchen.tests.business;

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

    @Test
    public void testContainsItemInput() throws Exception {
        System.out.println("\nStarting testContainsItemInput.");

        //Act 1 -- getName
        Item testItem = new Item("", -1, "", -1, -1);
        ListValidation testListValidation = new ListValidation(testItem);
        try {
            testListValidation.containsItemInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 2 -- getQuantity
        Item testItem1 = new Item(" ", -1, "", -1, -1);
        ListValidation testListValidation1 = new ListValidation(testItem1);
        try {
            testListValidation1.containsItemInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 3 -- getLength
        Item testItem2 = new Item(" ", 1, "", -1, -1);
        ListValidation testListValidation2 = new ListValidation(testItem2);
        try {
            testListValidation2.containsItemInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 4 -- threshold
        Item testItem3 = new Item(" ", 1, " ", 0, -1);
        ListValidation testListValidation3 = new ListValidation(testItem3);
        try {
            testListValidation3.containsItemInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 5 -- full true
        Item testItem4 = new Item("sampleItem", 1, "sampleItem", 1, 1);
        ListValidation testListValidation4 = new ListValidation(testItem4);
        try {
            testListValidation3.containsItemInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Finished testContainsItemInput.");
    }

}

