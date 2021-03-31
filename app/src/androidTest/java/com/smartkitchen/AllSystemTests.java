package com.smartkitchen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GrocerySystemTest.class,
        InventorySystemTest.class,
        RecipeSystemTest.class,
})
public class AllSystemTests { }
