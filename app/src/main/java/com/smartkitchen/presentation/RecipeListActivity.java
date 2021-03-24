package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartkitchen.R;
import com.smartkitchen.business.IRecipeActions;
import com.smartkitchen.business.RecipeActions;

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

        recipeActions.refreshAvailability();

        adapter = new RecipeRecViewAdapter(this);
        recipesRecView = findViewById(R.id.recipesRecView);
        btnAdd = findViewById(R.id.btnGoToAddRecipeActivity);

        btnAdd.setOnClickListener(v -> {
            Toast.makeText(this, "To be Implemented", Toast.LENGTH_SHORT).show();
        });

        recipesRecView.setAdapter(adapter);
        recipesRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(recipeActions.getRecipeList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipeActions.refreshAvailability();
    }
}