package com.smartkitchen.tests.business;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.FakeDBGrocery;
import com.smartkitchen.persistence.FakeDBInventory;

import java.util.ArrayList;

public class ListActionsTest {
    private ListActions listActions;
    private InventoryActions inventoryActions;
    private GroceryActions groceryActions;
    private FakeDBGrocery testgrdb = new FakeDBGrocery();
    private FakeDBInventory testindb = new FakeDBInventory();


    @Before
    public void setUp(){
        listActions = new ListActions();
        inventoryActions = new InventoryActions(testindb);
        groceryActions = new GroceryActions(testgrdb, inventoryActions);
    }

    @Test
    public void addTest(){
        System.out.println("\nStarting addTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
            groceryActions.addToGrocery(testItem); // add existing

        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(groceryActions.getGroceryItem(0), testItem);
        assertEquals(inventoryActions.getInventoryItem(0), testItem);


        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try{
            groceryActions.addToGrocery(testItem2);
            inventoryActions.addToInventory(testItem2);
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
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        groceryActions.updateGroceryItem(testItem);
        inventoryActions.updateInventoryItem(testItem);

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
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(groceryActions.getGroceryList().get(0), testItem);
        assertEquals(inventoryActions.getInventoryList().get(0), testItem);

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
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(groceryActions.getGroceryItemById(1), null);
        assertEquals(inventoryActions.getInventoryItemById(1), null);

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
            inventoryActions.addToInventory(testItem);
            groceryActions.buyItem(testItem);
            groceryActions.buyItem(testItem2);
            groceryActions.buyItem(testItem3);
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
            groceryActions.addToGrocery(testItem);
            groceryActions.buyAll();
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(inventoryActions.getInventoryItem(0), testItem);

        System.out.println("Finished buyAllTest");
    }

    @Test
    public void removeTest(){
        System.out.println("\nStarting removeTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
            groceryActions.removeFromGrocery(testItem);
            inventoryActions.removeFromInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(groceryActions.getGroceryList().isEmpty());
        assertTrue(inventoryActions.getInventoryList().isEmpty());

        System.out.println("Finished removeTest");
    }

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
            groceryActions.addToGrocery(testItem);
            inventoryActions.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(groceryActions.getGroceryListTotal() == 50);

        System.out.println("Finished getGroceryListTotal");
    }
}


