package com.smartkitchen.tests.business;

import org.junit.Test;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Item;

import static org.junit.Assert.*;


//just copied over the unit test since this layer does not interact with the persistence layer
//reason: code coverage for Integration Tests automation
public class ListValidationIT {

    private final IListValidation testListValidation = new ListValidation();

    @Test
    public void testListValidation() {
        System.out.println("\nStarting testListValidation constructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertNotNull(testListValidation);
        System.out.println("Finished testListValidation constructor.");
    }

    @Test
    public void testThresholdStatus() {
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertFalse(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testThresholdStatusTrue() {
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 5);

        //Assert
        assertTrue(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testIsEmpty() {
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testContainsItemInput() {
        System.out.println("\nStarting testContainsItemInput.");

        //Act 1 -- getName
        Item testItem = new Item("", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 2 -- getQuantity
        Item testItem1 = new Item(" ", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 3 -- getLength
        Item testItem2 = new Item(" ", 1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 4 -- threshold
        Item testItem3 = new Item(" ", 1, " ", 0, -1);
        try {
            testListValidation.containsItemInputs(testItem3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 5 -- full true
        Item testItem4 = new Item("sampleItem", 1, "sampleItem", 1, 1);
        try {
            testListValidation.containsItemInputs(testItem4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Finished testContainsItemInput.");
    }

}

