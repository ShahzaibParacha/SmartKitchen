package com.smartkitchen.tests;

import org.junit.Test;

import com.smartkitchen.Item;

import static org.junit.Assert.*;

public class ItemTest{

    @Test
    public void testItemConstructor(){
        System.out.println("\nStarting testItemConstructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1);

        //Assert
        assertNotNull(testItem);
        assertEquals(testItem.getName(), "sampleItem");
        assertEquals(testItem.getQuantity(), 1);
        assertEquals(testItem.getUnits(), "sampleUnit");
        assertEquals(testItem.getQuantityToBuy(), 1);
        assertEquals(testItem.getInitQuantity(), 1);
        
        System.out.println("Finished testItemConstructor.");
    }

    @Test 
    public void testItemSetters(){
        System.out.println("\nStarting testItemSetters.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit", 1);
        testItem.setName("sample2");
        testItem.setQuantity(2);
        testItem.setUnits("sampleUnit2");
        testItem.setQuantityToBuy(2);

        //Assert
        assertEquals(testItem.getName(), "sample2");
        assertEquals(testItem.getQuantity(), 2);
        assertEquals(testItem.getUnits(), "sampleUnit2");
        assertEquals(testItem.getQuantityString(), "2 sampleUnit2");
        assertEquals(testItem.getQuantityToBuyString(), "2 sampleUnit2");
        
        System.out.println("Finished testItemSetters.");
    }

    @Test
    public void testItemQuantity(){
        System.out.println("\nStarting testItemQuantity.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit", 1);
        testItem.setInitQuantity(2);

        //Assert
        assertEquals(testItem.getInitQuantity(), 2);

        System.out.println("Finished testItemQuantity.");
    }

    @Test
    public void testThresholdStatus(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit", 1);
        testItem.updateQuantity(0);

        //Assert
        assert(testItem.thresholdStatus());

        //Act
        testItem.updateQuantity(50);

        //Assert
        assertFalse(testItem.thresholdStatus());

        System.out.println("Finished testThresholdStatus.");

    }
}


