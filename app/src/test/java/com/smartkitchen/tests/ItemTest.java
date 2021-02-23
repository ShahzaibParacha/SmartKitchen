package com.smartkitchen.tests;

import org.junit.Test;
import com.smartkitchen.objects.Item;
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
    public void testThresholdStatus(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sample", 1, "sampleUnit", 1, 1);
        testItem.setQuantity(0);

        //Assert
        assert(testItem.thresholdStatus());

        //Act 2 -- update the quantity so it triggers false in the thresholdStatus method
        testItem.setQuantity(50);

        //Assert
        assertFalse(testItem.thresholdStatus());

        System.out.println("Finished testThresholdStatus.");
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


