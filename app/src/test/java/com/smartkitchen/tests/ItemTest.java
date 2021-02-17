package com.smartkitchen.tests;

import org.junit.Test;

import com.smartkitchen.Item;

import static org.junit.Assert.*;

public class ItemTest{

    @Test
    public void testItemConstructor(){
        System.out.println("\nStarting testItemConstructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit");

        //Assert
        assertNotNull(testItem);
        assertEquals(testItem.getName(), "sampleItem");
        assertEquals(testItem.getQuantity(), 1);
        assertEquals(testItem.getUnits(), "sampleUnit");
        
        System.out.println("Finished testItemConstructor.");
    }

    @Test 
    public void testItemSetters(){
        System.out.println("\nStarting testItemSetters.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit");
        testItem.setName("sample2");
        testItem.setQuantity(2);
        testItem.setUnits("sampleUnit2");

        //Assert
        
        assertEquals(testItem.getName(), "sample2");
        assertEquals(testItem.getQuantity(), 2);
        assertEquals(testItem.getUnits(), "sampleUnit2");
        assertEquals(testItem.getQuantityString(), "2 sampleUnit2");
        
        System.out.println("Finished testItemSetters.");
    }
}


