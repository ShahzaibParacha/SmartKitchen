package com.smartkitchen.presentation;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartkitchen.R;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.IRecipeActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.business.RecipeActions;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public class AddRecipeActivity extends ParentActivity {

    IListActions listActions = new ListActions();

    private RecyclerView ingredientsRecView, instructionsRecView;
    private IngredientsRecViewAdapter ingredientsAdapter;
    private InstructionRecViewAdapter instructionsAdapter;

    IRecipeActions recipeActions = new RecipeActions();

    private EditText inputName;
    private Button btnCancel, btnSubmit, btnAddIngredient, btnAddInstruction;

    private Recipe newRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        setTitle("Add Recipe");
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> ingredientQuantities = new ArrayList<>();
        ArrayList<String> ingredientUnits = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();
        ArrayList<Boolean> hasIngredient = new ArrayList<>();

        newRecipe = new Recipe("", ingredients, ingredientQuantities, ingredientUnits, instructions);

        initViews();
        ingredientsAdapter = new IngredientsRecViewAdapter(this, true);
        ingredientsRecView.setAdapter(ingredientsAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter.setItems(newRecipe, ingredients, ingredientQuantities, ingredientUnits, hasIngredient);

        instructionsAdapter = new InstructionRecViewAdapter(this, true);
        instructionsRecView.setAdapter(instructionsAdapter);
        instructionsRecView.setLayoutManager(new LinearLayoutManager(this));
        instructionsAdapter.setItems(instructions);

        btnAddIngredient.setOnClickListener(v -> {
            newRecipe.getIngredients().add("");
            newRecipe.getIngredientQuantities().add("");
            newRecipe.getIngredientUnits().add("");
            //newRecipe.getHasIngredient().add(true);
            ingredientsAdapter.notifyItemInserted(newRecipe.getIngredients().size()-1);
        });

        btnAddInstruction.setOnClickListener(v -> {
            newRecipe.getInstructions().add("");
            instructionsAdapter.notifyItemInserted(newRecipe.getInstructions().size()-1);
        });

        btnCancel.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void initViews(){
        inputName = findViewById(R.id.addRecipeName);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        instructionsRecView = findViewById(R.id.instructionsRecView);

        btnCancel = findViewById(R.id.btnCancelAddRecipe);
        btnSubmit = findViewById(R.id.btnSubmitAddRecipe);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddInstruction = findViewById(R.id.btnAddInstruction);
    }

    private void addItem(){
        String name = inputName.getText().toString();
        ArrayList<String> ingredientNames = ingredientsAdapter.getIngredientNames();
        ArrayList<String> ingredientQuantities = ingredientsAdapter.getIngredientQuantities();
        ArrayList<String> ingredientUnits = ingredientsAdapter.getIngredientUnits();
        ArrayList<String> instructions = instructionsAdapter.getInstructions();
        Recipe newRecipe = new Recipe(name, ingredientNames, ingredientQuantities, ingredientUnits, instructions);
        try {
            if(listActions.getDuplicateByName(newRecipe, recipeActions.getRecipeList()) == null) {
                recipeActions.addToRecipes(newRecipe);
                finish();
            }
            else {
                Toast.makeText(AddRecipeActivity.this, "A Recipe with this name already exists in Inventory.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (InvalidInputException e) {
            Toast.makeText(AddRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
