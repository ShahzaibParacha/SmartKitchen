package com.smartkitchen.tests.business;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.io.IOException;
import java.util.ArrayList;


public class ListActionsTest {
    private ListActions listActions;
    private DBManager testTarget;

    @Before
    public void setUp(){
        testTarget = mock(DBManager.class);
        //listActions = new ListActions(testTarget);
    }

    @Test
    public void addToGroceryTest(){
        System.out.println("Starting addToGroceryTest");
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1, testAllergies, 1, 1);
        testItem.setId(1);

        try{
            listActions.addToGrocery(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //listActions.getGroceryItem(1);

        System.out.println("Finishing addToGroceryTest");
    }
}


