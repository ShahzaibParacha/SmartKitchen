package com.smartkitchen.presentation.recipe;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartkitchen.R;
import com.smartkitchen.business.interfaces.IRecipeActions;
import com.smartkitchen.business.implementation.RecipeActions;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.presentation.ParentActivity;

import java.util.ArrayList;

//Main screen for viewing all the recipes
public class RecipeListActivity extends ParentActivity {

    //Adapter for the recipes list
    private RecipeRecViewAdapter adapter;
    //Access to relevant business layer methods
    private final IRecipeActions recipeActions = new RecipeActions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Set text and colour for the taskbar
        setTitle("Recipes");
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        //Refresh the availability information of the recipes list
        ArrayList<Recipe> recipes = recipeActions.getRecipeList();
        recipeActions.refreshAvailability(recipes);

        //Set up the list and button
        adapter = new RecipeRecViewAdapter(this);
        RecyclerView recipesRecView = findViewById(R.id.recipesRecView);
        FloatingActionButton btnAdd = findViewById(R.id.btnGoToAddRecipeActivity);

        //Navigate to add screen
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeListActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });

        //Set up the list adapter
        recipesRecView.setAdapter(adapter);
        recipesRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(recipes);

    }

    //Refreshes the page
    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Recipe> recipes = recipeActions.getRecipeList();
        recipeActions.refreshAvailability(recipes);
        adapter.setItems(recipes);
    }
}
