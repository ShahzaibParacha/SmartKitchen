package com.smartkitchen.tests.business;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.FakeDBGrocery;
import com.smartkitchen.persistence.FakeDBInventory;

import java.util.ArrayList;

public class ListActionsTest {
    private ListActions listActions;
    private FakeDBGrocery testgrdb = new FakeDBGrocery();
    private FakeDBInventory testindb = new FakeDBInventory();


    @Before
    public void setUp(){
        listActions = new ListActions(testgrdb, testindb);
    }

    @Test
    public void addTest(){
        System.out.println("\nStarting addTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
            listActions.addToGrocery(testItem); // add existing

        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(listActions.getGroceryItem(0), testItem);
        assertEquals(listActions.getInventoryItem(0), testItem);


        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try{
            listActions.addToGrocery(testItem2);
            listActions.addToInventory(testItem2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("Finishing addTest");
    }

    @Test
    public void updateItemTest(){
        System.out.println("\nStarting updateItemTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        listActions.updateGroceryItem(testItem);
        listActions.updateInventoryItem(testItem);

        System.out.println("Finished updateItemTest");
    }

    @Test
    public void editValidationTest(){
        System.out.println("\nStarting editValidationTest");

        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try{
            listActions.editValidation(testItem);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("Finished editValidationTest");
    }

    @Test
    public void getListTest(){
        System.out.println("\nStarting getListTest");

        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(listActions.getGroceryList().get(0), testItem);
        assertEquals(listActions.getInventoryList().get(0), testItem);

        System.out.println("Finished getListTest");
    }

    /*
    @Test
    public void getItemByIdTest(){
        System.out.println("\nStarting getItemByIdTest");

        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        testItem.setId(1);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(listActions.getGroceryItemById(1), null);
        assertEquals(listActions.getInventoryItemById(1), null);

        System.out.println("Finished getItemByIdTest");
    }
     */

    @Test
    public void buyItemTest(){
        ListActions testmp = new ListActions();
        System.out.println("\nStarting buyItemTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 5, 2, testAllergies, 1, 1);
        Item testItem2 = new Item("sampleItem2", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        Item testItem3 = new Item("sampleItem2", 1, "", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToInventory(testItem);
            listActions.buyItem(testItem);
            listActions.buyItem(testItem2);
            listActions.buyItem(testItem3);
        }catch(Exception e){System.out.println(e.getMessage());}

        System.out.println("Starting buyItemTest");
    }

    @Test
    public void buyAllTest(){
        System.out.println("\nStarting buyAllTest");

        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToGrocery(testItem);
            listActions.buyAll();
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(listActions.getInventoryItem(0), testItem);

        System.out.println("Finished buyAllTest");
    }

    @Test
    public void removeTest(){
        System.out.println("\nStarting removeTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
            listActions.removeFromGrocery(testItem);
            listActions.removeFromInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(listActions.getGroceryList().isEmpty());
        assertTrue(listActions.getInventoryList().isEmpty());

        System.out.println("Finished removeTest");
    }
/*
    @Test
    public void isInTest(){
        System.out.println("\nStarting isInTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(listActions.isInGrocery(testItem));
        assertTrue(listActions.isInInventory(testItem));

        System.out.println("Finished isInTest");
    }
 */

    @Test
    public void isInListTest(){
        System.out.println("\nStarting isInListTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        assertTrue(listActions.isInList(testAllergies, "testAllergy"));

        System.out.println("Finished isInListTest");
    }

    @Test
    public void getGroceryListTotalTest(){
        System.out.println("\nStarting getGroceryListTotal");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 50);

        try {
            listActions.addToGrocery(testItem);
            listActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(listActions.getGroceryListTotal() == 50);

        System.out.println("Finished getGroceryListTotal");
    }
}


