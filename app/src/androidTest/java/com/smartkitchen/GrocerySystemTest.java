package com.smartkitchen;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
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

import com.smartkitchen.TestHelpers.TestViewAction;
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
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class GrocerySystemTest{
    private GroceryPersistenceDB grocerydb;
    private InventoryPersistenceDB inventorydb;
    List<Item> groceryList;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDatabase(){
        groceryList = Services.getGroceryPersistence().getGroceryList();
    }

    @Test
    public void addRemoveGroceryTest() throws InterruptedException{ // User Story: Add Items to Grocery List
        // navigate to Grocery List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(3000);

        // add new item
        onView(withId(R.id.btnGoToAddGroceryActivity)).perform(click());
        onView(withId(R.id.inputGroceryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputGroceryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputGroceryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddGroceryItem)).perform(scrollTo(), click());
        Thread.sleep(3000);

        // remove newly added item
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveGroceryItem)));
        Thread.sleep(1000);
    }

    @Test
    public void editGroceryItemTest() throws InterruptedException{ // User Story: Edit Items in the Grocery List
        // navigate to Grocery List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(3000);

        // add new item
        onView(withId(R.id.btnGoToAddGroceryActivity)).perform(click());
        onView(withId(R.id.inputGroceryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputGroceryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputGroceryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddGroceryItem)).perform(scrollTo(), click());
        Thread.sleep(3000);

        // edit item fields
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnEditGroceryItem)));
        onView(withId(R.id.editGroceryItemName)).perform(clearText());
        onView(withId(R.id.editGroceryItemName)).perform(typeText("editedTestItem"));
        onView(withId(R.id.editGroceryQuantity)).perform(clearText());
        onView(withId(R.id.editGroceryQuantity)).perform(typeText("100"));
        onView(withId(R.id.btnEditGrocerySubmit)).perform(scrollTo(), click());

        // view edited item information
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), click()));
        Thread.sleep(1000);
        pressBack();
        // remove newly added item
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveGroceryItem)));
        Thread.sleep(1000);
    }

    @Test
    public void buyItemTest() throws InterruptedException{
        // navigate to Grocery List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(3000);

        // add new item
        onView(withId(R.id.btnGoToAddGroceryActivity)).perform(click());
        onView(withId(R.id.inputGroceryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputGroceryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputGroceryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddGroceryItem)).perform(scrollTo(), click());
        Thread.sleep(3000);

        // buy the new item
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnBuyItem)));
        Thread.sleep(3000);

        // navigate to Inventory List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Current Inventory")).perform(click());
        Thread.sleep(3000);

        // remove the bought item
        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);
    }

}