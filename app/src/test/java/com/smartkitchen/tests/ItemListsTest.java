package com.smartkitchen.tests;

import org.junit.Test;

import com.smartkitchen.Item;
import com.smartkitchen.ItemLists;
import static org.junit.Assert.*;



public class ItemListsTest{

//
    @Test
    public void itemListConstructorTest(){
        System.out.println("\nStarting ItemLists Constructor test.");

        //Act
        ItemLists testList = new ItemLists();

        //Assert
        assertNotNull(testList);

        System.out.println("Finished ItemLists Constructor test.");
    }

    @Test
    public void addToInventoryTest(){
        System.out.println("\nStarting addToInventoryTest.");

        //Act
        ItemLists testList = new ItemLists();
        Item testItem = new Item("sampleItem", 1, "sampleUnit");
        testList.addToInventory(testItem);

        //Assert
        assertEquals(testList.getInventoryList().get(0), testItem);

        System.out.println("Finished addToInventoryTest.");
    }

    @Test
    public void removeFromInventoryTest(){
        System.out.println("\nStarting removeFromInventoryTest.");

        //Act
        ItemLists testList = new ItemLists();
        Item testItem = new Item("sampleItem", 1, "sampleUnit");
        testList.addToInventory(testItem);

        //Assert
        assertEquals(testList.getInventoryList().get(0), testItem);
        assertEquals(testList.removeFromInventory(testItem), testItem);
        assertTrue(testList.getInventoryList().isEmpty());

        System.out.println("Finished removeFromInventoryTest.");
    }

    @Test
    public void addToGroceryTest(){
        System.out.println("\nStarting addToGroceryTest.");

        //Act
        ItemLists testList = new ItemLists();
        Item testItem = new Item("sampleItem", 1, "sampleUnit");
        testList.addToGrocery(testItem);

        //Assert
        assertEquals(testList.getGroceryList().get(0), testItem);
        testList.removeFromGrocery(testItem);

        System.out.println("Finished addToInventoryTest.");
    }

    @Test
    public void removeFromGroceryTest(){
        System.out.println("\nStarting removeFromGroceryTest.");

        //Act
        ItemLists testList = new ItemLists();
        Item testItem = new Item("sampleItem", 1, "sampleUnit");
        testList.addToGrocery(testItem);

        //Assert
        assertEquals(testList.getGroceryList().get(0), testItem);
        assertEquals(testList.removeFromGrocery(testItem), testItem);
        assertTrue(testList.getGroceryList().isEmpty());

        System.out.println("Finished removeFromGroceryTest.");
    }
}

