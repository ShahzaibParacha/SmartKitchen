package com.smartkitchen.TestHelpers;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

// this class was made so espresso clicks can be done in specific buttons inside a list item in recyclerview
// source: https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item

public class TestViewAction{

    public static ViewAction clickChildviewWithId(final int id){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints(){
                return null;
            }

            @Override
            public String getDescription(){
                return "Click on a child view with specified ID.";
            }

            @Override
            public void perform(UiController uiController, View view){
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}