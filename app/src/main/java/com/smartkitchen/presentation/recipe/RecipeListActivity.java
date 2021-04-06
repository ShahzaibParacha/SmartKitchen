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

public class RecipeListActivity extends ParentActivity {

    private RecyclerView recipesRecView;
    private RecipeRecViewAdapter adapter;

    private FloatingActionButton btnAdd;
    private IRecipeActions recipeActions = new RecipeActions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        setTitle("Recipes");
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        ArrayList<Recipe> recipes = recipeActions.getRecipeList();
        recipeActions.refreshAvailability(recipes);

        adapter = new RecipeRecViewAdapter(this);
        recipesRecView = findViewById(R.id.recipesRecView);
        btnAdd = findViewById(R.id.btnGoToAddRecipeActivity);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeListActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });

        recipesRecView.setAdapter(adapter);
        recipesRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(recipes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Recipe> recipes = recipeActions.getRecipeList();
        recipeActions.refreshAvailability(recipes);
        adapter.setItems(recipes);
    }
}
