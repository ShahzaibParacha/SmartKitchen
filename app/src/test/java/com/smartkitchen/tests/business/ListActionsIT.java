package com.smartkitchen.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ListActionsIT{
    private ListActions listTestTarget;
    private InventoryActions invTestTarget;
    private GroceryActions groceryTestTarget;
    private File tempDB;

    @Before
    public void setUp() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final GroceryPersistenceDB groceryPersistence = new GroceryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final InventoryPersistenceDB inventoryPersistence = new InventoryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.listTestTarget = new ListActions();
        this.invTestTarget = new InventoryActions(inventoryPersistence);
        this.groceryTestTarget = new GroceryActions(groceryPersistence, invTestTarget);
    }

    @Test
    public void addTest(){
        System.out.println("\nStarting addTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
            //testTarget.addToGrocery(testItem); // add existing

        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(groceryTestTarget.getGroceryItem(2).getName(), testItem.getName());
        assertEquals(invTestTarget.getInventoryItem(2).getName(), testItem.getName());


        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try{
            groceryTestTarget.addToGrocery(testItem2);
            invTestTarget.addToInventory(testItem2);
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
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        groceryTestTarget.updateGroceryItem(testItem);
        invTestTarget.updateInventoryItem(testItem);

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
            listTestTarget.editValidation(testItem);
            listTestTarget.editValidation(testItem2);
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
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(groceryTestTarget.getGroceryList().get(2).getName(), testItem.getName());
        assertEquals(invTestTarget.getInventoryList().get(2).getName(), testItem.getName());

        System.out.println("Finished getListTest");
    }

    /*
    @Test
    public void getItemByIdTest(){
        System.out.println("\nStarting getItemByIdTest");

        assertEquals(testTarget.getGroceryItemById(1).getName(),"Sugar");
        assertEquals(testTarget.getInventoryItemById(4).getName(), "Butter");

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
            invTestTarget.addToInventory(testItem);
            groceryTestTarget.buyItem(testItem);
            groceryTestTarget.buyItem(testItem2);
            groceryTestTarget.buyItem(testItem3);
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
            groceryTestTarget.addToGrocery(testItem);
            groceryTestTarget.buyAll();
        }catch(Exception e){System.out.println(e.getMessage());}

        assertEquals(invTestTarget.getInventoryItem(4).getName(), testItem.getName());

        System.out.println("Finished buyAllTest");
    }

    @Test
    public void removeTest(){
        System.out.println("\nStarting removeTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
            groceryTestTarget.removeFromGrocery(testItem);
            invTestTarget.removeFromInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertNotEquals(groceryTestTarget.getGroceryList().get(groceryTestTarget.getGroceryList().size()-1), "testTarget");
        assertNotEquals(invTestTarget.getInventoryList().get(invTestTarget.getInventoryList().size()-1), "testTarget");

        System.out.println("Finished removeTest");
    }

    @Test
    public void isInListTest(){
        System.out.println("\nStarting isInListTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        assertTrue(listTestTarget.isInList(testAllergies, "testAllergy"));

        System.out.println("Finished isInListTest");
    }

    @Test
    public void getGroceryListTotalTest(){
        System.out.println("\nStarting getGroceryListTotal");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 50);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        }catch(Exception e){System.out.println(e.getMessage());}

        assertTrue(groceryTestTarget.getGroceryListTotal() == 61);

        System.out.println("Finished getGroceryListTotal");
    }

    @After
    public void tearDown(){
        //reset db
        this.tempDB.delete();
    }


}