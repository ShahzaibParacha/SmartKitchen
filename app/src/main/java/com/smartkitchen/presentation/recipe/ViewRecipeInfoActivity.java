package com.smartkitchen.presentation.recipe;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.smartkitchen.R;
import com.smartkitchen.business.interfaces.IRecipeActions;
import com.smartkitchen.business.implementation.RecipeActions;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.presentation.ParentActivity;

public class ViewRecipeInfoActivity extends ParentActivity {

    private RecyclerView ingredientsRecView, instructionsRecView;
    private IngredientsRecViewAdapter ingredientsAdapter;
    private InstructionRecViewAdapter instructionsAdapter;

    IRecipeActions recipeActions = new RecipeActions();

    private TextView txtTitle, txtName, txtTotalCalories;
    private Button btnBackToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_info);
        setColour(ContextCompat.getColor(this, R.color.redColour3));


        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra("Position", -1);
        Recipe recipe = recipeActions.getRecipeList().get(itemPosition);

        initViews();
        setTitle("View " + recipe.getName() + " Information");
        txtTitle.setText(recipe.getName());
        txtName.setText(recipe.getName());
        txtTotalCalories.setText("" + recipeActions.calculateTotalCalories(recipe));

        ingredientsAdapter = new IngredientsRecViewAdapter(this, false);
        ingredientsRecView.setAdapter(ingredientsAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("Before ingredient set item");
        recipe.setHasIngredient(recipeActions.checkIngredients(recipe));
        ingredientsAdapter.setItems(recipe, recipe.getIngredients(), recipe.getIngredientQuantities(), recipe.getIngredientUnits(), recipe.getHasIngredient());

        instructionsAdapter = new InstructionRecViewAdapter(this, false);
        instructionsRecView.setAdapter(instructionsAdapter);
        instructionsRecView.setLayoutManager(new LinearLayoutManager(this));
        instructionsAdapter.setItems(recipe, recipe.getInstructions());

        btnBackToList.setOnClickListener(v -> finish());
    }

    private void initViews(){
        txtTitle = findViewById(R.id.txtViewRecipeTitle);
        txtName = findViewById(R.id.txtRecipeName);
        txtTotalCalories = findViewById(R.id.txtRecipeTotalCalories);

        btnBackToList = findViewById(R.id.btnBackToList);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        instructionsRecView = findViewById(R.id.instructionsRecView);
    }
}
