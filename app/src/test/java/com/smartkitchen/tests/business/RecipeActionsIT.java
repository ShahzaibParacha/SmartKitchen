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
import com.smartkitchen.business.RecipeActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.RecipePersistenceDB;
import com.smartkitchen.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RecipeActionsIT{
    private ListActions listTestTarget;
    private InventoryActions invTestTarget;
    private GroceryActions groceryTestTarget;
    private RecipeActions recipeTestTarget;
    private File tempDB;

    @Before
    public void setUp() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final GroceryPersistenceDB groceryPersistence = new GroceryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final InventoryPersistenceDB inventoryPersistence = new InventoryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final RecipePersistenceDB recipePersistence = new RecipePersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        this.listTestTarget = new ListActions();
        this.invTestTarget = new InventoryActions(inventoryPersistence);
        this.groceryTestTarget = new GroceryActions(groceryPersistence, invTestTarget);
        this.recipeTestTarget = new RecipeActions(recipePersistence);
    }

}