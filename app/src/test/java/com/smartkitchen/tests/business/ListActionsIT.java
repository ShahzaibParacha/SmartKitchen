package com.smartkitchen.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ListActionsIT{
    private ListActions testTarget;
    private File tempDB;

    @Before
    public void setUp() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final GroceryPersistenceDB groceryPersistence = new GroceryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final InventoryPersistenceDB inventoryPersistence = new InventoryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.testTarget = new ListActions(groceryPersistence, inventoryPersistence);
    }

    @Test
    public void addTest(){
        System.out.println("\nStarting addTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            testTarget.addToGrocery(testItem);
            testTarget.addToInventory(testItem);
            //testTarget.addToGrocery(testItem); // add existing

        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(testTarget.getGroceryItem(2).getName(), testItem.getName());
        assertEquals(testTarget.getInventoryItem(2).getName(), testItem.getName());


        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try{
            testTarget.addToGrocery(testItem2);
            testTarget.addToInventory(testItem2);
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
            testTarget.addToGrocery(testItem);
            testTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        testTarget.updateGroceryItem(testItem);
        testTarget.updateInventoryItem(testItem);

        System.out.println("Finished updateItemTest");
    }

    @Test
    public void editValidationTest(){
        System.out.println("\nStarting editValidationTest");

        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);
        Item testItem2 = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try{
            testTarget.editValidation(testItem);
            testTarget.editValidation(testItem2);
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
            testTarget.addToGrocery(testItem);
            testTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(testTarget.getGroceryList().get(2).getName(), testItem.getName());
        assertEquals(testTarget.getInventoryList().get(2).getName(), testItem.getName());

        System.out.println("Finished getListTest");
    }

    @Test
    public void getItemByIdTest(){
        System.out.println("\nStarting getItemByIdTest");

        assertEquals(testTarget.getGroceryItemById(1).getName(),"Sugar");
        assertEquals(testTarget.getInventoryItemById(4).getName(), "Butter");

        System.out.println("Finished getItemByIdTest");
    }

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
            testTarget.addToInventory(testItem);
            testTarget.buyItem(testItem);
            testTarget.buyItem(testItem2);
            testTarget.buyItem(testItem3);
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
            testTarget.addToGrocery(testItem);
            testTarget.buyAll();
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(testTarget.getInventoryItem(4).getName(), testItem.getName());

        System.out.println("Finished buyAllTest");
    }

    @Test
    public void removeTest(){
        System.out.println("\nStarting removeTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            testTarget.addToGrocery(testItem);
            testTarget.addToInventory(testItem);
            testTarget.removeFromGrocery(testItem);
            testTarget.removeFromInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertNotEquals(testTarget.getGroceryList().get(testTarget.getGroceryList().size()-1), "testTarget");
        assertNotEquals(testTarget.getInventoryList().get(testTarget.getInventoryList().size()-1), "testTarget");

        System.out.println("Finished removeTest");
    }

    @Test
    public void isInTest(){
        System.out.println("\nStarting isInTest");

        //assertTrue(testTarget.isInGrocery(testTarget.getGroceryItem(2)));
        //assertTrue(testTarget.isInInventory(testTarget.getInventoryItem(4)));

        System.out.println("Finished isInTest");
    }

    @Test
    public void isInListTest(){
        System.out.println("\nStarting isInListTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        assertTrue(testTarget.isInList(testAllergies, "testAllergy"));

        System.out.println("Finished isInListTest");
    }

    @Test
    public void getGroceryListTotalTest(){
        System.out.println("\nStarting getGroceryListTotal");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 50);

        try {
            testTarget.addToGrocery(testItem);
            testTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(testTarget.getGroceryListTotal() == 61);

        System.out.println("Finished getGroceryListTotal");
    }

    @After
    public void tearDown(){
        //reset db
        this.tempDB.delete();
    }


}