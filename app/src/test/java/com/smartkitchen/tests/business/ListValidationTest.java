package com.smartkitchen.tests.business;

import org.junit.Test;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ListValidationTest{

    private IListValidation testListValidation = new ListValidation();

    @Test
    public void testListValidation(){
        System.out.println("\nStarting testListValidation constructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertNotNull(testListValidation);
        System.out.println("Finished testListValidation constructor.");
    }

    @Test
    public void testThresholdStatus(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertFalse(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testThresholdStatusTrue(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 5);

        //Assert
        assertTrue(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testIsEmpty(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testContainsItemInput() throws Exception {
        System.out.println("\nStarting testContainsItemInput.");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        //Act 1 -- getName
        Item testItem = new Item("", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 2 -- getQuantity
        Item testItem1 = new Item(" ", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem1);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 3 -- getLength
        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, -1, 1);
        try {
            testListValidation.containsItemInputs(testItem2);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 4 -- threshold
        Item testItem3 = new Item(" ", 1, " ", 0, -1);
        try {
            testListValidation.containsItemInputs(testItem3);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // new constructors test
        Item testItem4 = new Item("sampleItem", 1, "sampleUnit", 1, 1, testAllergies, -1, 1);
        try {
            testListValidation.containsItemInputs(testItem4);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Item testItem5 = new Item("sampleItem", 1, "sampleUnit", 1, 1, testAllergies, 1, -1);
        try {
            testListValidation.containsItemInputs(testItem5);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Item testItem6 = new Item("sampleItem", 1, "sampleUnit", 1, 1, testAllergies, 1, 1);
        try {
            testListValidation.containsItemInputs(testItem6);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Finished testContainsItemInput.");
    }
}

