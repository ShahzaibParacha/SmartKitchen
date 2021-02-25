package com.smartkitchen.tests;

import org.junit.Test;
import com.smartkitchen.objects.Item;

import static org.junit.Assert.*;


//NOTE: Right now, the get values are 3 because of considering the 3 items that are added to the lists in the initializer of the ItemLists (i.e. in initData()).
public class ItemListsTest{

    @Test
    public void itemListConstructorTest(){
        System.out.println("\nStarting ItemLists Constructor test.");

        //Act
        ItemLists testList = ItemLists.getInstance(); //ItemLists has its own private constructor (getInstance())

        //Assert
        assertNotNull(testList);

        System.out.println("Finished ItemLists Constructor test.");
    }

    @Test
    public void addToInventoryTest(){
        System.out.println("\nStarting addToInventoryTest.");

        //Act
        ItemLists testList = ItemLists.getInstance();
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        testList.addToInventory(testItem);

        //Assert
        assertEquals(testList.getInventoryList().get(3), testItem);

        System.out.println("Finished addToInventoryTest.");
    }

    @Test
    public void removeFromInventoryTest(){
        System.out.println("\nStarting removeFromInventoryTest.");

        //Act
        ItemLists testList = ItemLists.getInstance();
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        testList.addToInventory(testItem);

        //Assert
        assertEquals(testList.getInventoryList().get(3), testItem);
        assertEquals(testList.removeFromInventory(testItem), testItem);

        System.out.println("Finished removeFromInventoryTest.");
    }

    @Test
    public void addToGroceryTest(){
        System.out.println("\nStarting addToGroceryTest.");

        //Act
        ItemLists testList = ItemLists.getInstance();
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        testList.addToGrocery(testItem);

        //Assert
        assertEquals(testList.getGroceryList().get(2), testItem);
        testList.removeFromGrocery(testItem);

        System.out.println("Finished addToInventoryTest.");
    }

    @Test
    public void getGroceryItemByNameTest(){
        System.out.println("\nStarting getGroceryItemByNameTest.");

        //Act
        ItemLists testList = ItemLists.getInstance();
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        testList.addToGrocery(testItem);

        //Assert
        assertEquals(testList.getGroceryItemByName("sampleItem"), testItem);


        System.out.println("Finished getGroceryItemByNameTest.");

    }

    @Test
    public void removeFromGroceryTest(){
        System.out.println("\nStarting getGroceryItemByNameTest.");

        //Act
        ItemLists testList = ItemLists.getInstance();
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);
        testList.addToGrocery(testItem);

        //Assert
        assertEquals(testList.getGroceryList().get(3), testItem);
        assertEquals(testList.removeFromGrocery(testItem), testItem);

        System.out.println("Finished removeFromGroceryTest.");
    }
}

