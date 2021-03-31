package com.smartkitchen;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.smartkitchen.application.MainActivity;
import com.smartkitchen.application.Services;
import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.presentation.ItemRecViewAdapter;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class GroceryTest{
    private final int TEST_ITEM = 2;

    private GroceryPersistenceDB grocerydb;
    private InventoryPersistenceDB inventorydb;
    List<Item> inventoryList;
    private Item testItem;

    @Before
    public void setupDatabase(){
        grocerydb = (GroceryPersistenceDB) Services.getGroceryPersistence();
        inventorydb = (InventoryPersistenceDB) Services.getInventoryPersistence();
        inventoryList = inventorydb.getInventoryList();
    }

    @Test
    public void addToGroceryTest(){ // User Story: Add Items to Grocery List

    }

    @Test
    public void removeFromGroceryTest(){ // User Story: Remove Items from Current Grocery

    }

    @Test
    public void viewGroceryTest(){ // User Story: View Grocery List

    }

    @Test
    public void editGroceryItemTest(){ // User Story: Edit Items in the Grocery List

    }

    @Test
    public void calculateTotalGroceryPriceTest(){ // User Story: Calculate the Total Price of Items in the Grocery List

    }

}