package com.smartkitchen;

import android.widget.EditText;

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
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.RecipePersistenceDB;
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
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RecipeSystemTest{
    private GroceryPersistenceDB grocerydb;
    private InventoryPersistenceDB inventorydb;
    private RecipePersistenceDB recipedb;
    List<Recipe> recipeList;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDatabase(){
        recipeList = Services.getRecipePersistence().getRecipeList();
    }

    @Test
    public void addRemoveRecipeTest() throws InterruptedException{
        // navigate to Recipes screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Recipes")).perform(click());
        Thread.sleep(3000);

        // add new Recipe
        // add recipe name
        onView(withId(R.id.btnGoToAddRecipeActivity)).perform(click());
        onView(withId(R.id.addRecipeName)).perform(typeText("testRecipeName"));
        Espresso.closeSoftKeyboard();
        Thread.sleep(3000);

        // add test Ingredient
        onView(withId(R.id.btnAddIngredient)).perform(click());
        onView(withHint("Name")).perform(typeText("testIngredient"));
        onView(withHint("Quantity")).perform(typeText("1"));
        onView(withHint("Units")).perform(typeText("testUnits"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());
        Thread.sleep(3000);

        // add test Instruction
        onView(withId(R.id.btnAddInstruction)).perform(scrollTo(), click());
        onView(withHint("Instruction")).perform(typeText("testInstruction"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.btnSubmitAddRecipe)).perform(scrollTo(), click()); // click submit -- create recipe
        Thread.sleep(3000);

        // remove recipe
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.btnRemoveRecipe)));
    }

    @Test
    public void updateRecipe(){

    }

}