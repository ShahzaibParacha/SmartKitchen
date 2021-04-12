package com.smartkitchen.presentation.recipe;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartkitchen.R;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.interfaces.IRecipeActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.business.implementation.RecipeActions;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.presentation.ParentActivity;
import com.smartkitchen.presentation.recipe.ingredient.EditIngredientPopUp;
import com.smartkitchen.presentation.recipe.ingredient.IngredientsRecViewAdapter;
import com.smartkitchen.presentation.recipe.instruction.EditInstructionPopUp;
import com.smartkitchen.presentation.recipe.instruction.InstructionRecViewAdapter;

import java.util.ArrayList;

//Screen for adding a recipe
public class AddRecipeActivity extends ParentActivity {

    //Access to relevant business layer methods
    IListActions listActions = new ListActions();
    IRecipeActions recipeActions = new RecipeActions();
    //The lists and adapters for ingredients and instructions
    private RecyclerView ingredientsRecView, instructionsRecView;
    private IngredientsRecViewAdapter ingredientsAdapter;
    private InstructionRecViewAdapter instructionsAdapter;

    //Input field for name
    private EditText inputName;
    //Relevant buttons
    private Button btnCancel, btnSubmit, btnAddIngredient, btnAddInstruction;

    private Recipe newRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //Sets the text and colour of the taskbar
        setTitle("Add Recipe");
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        //Creates empty array lists for the new recipe
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> ingredientQuantities = new ArrayList<>();
        ArrayList<String> ingredientUnits = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();
        ArrayList<Boolean> hasIngredient = new ArrayList<>();
        //Creates the new recipe
        newRecipe = new Recipe("", ingredients, ingredientQuantities, ingredientUnits, instructions);

        //Initializes the views
        initViews();

        //Sets up the adapters for ingredients and instructions
        ingredientsAdapter = new IngredientsRecViewAdapter(this, true);
        ingredientsRecView.setAdapter(ingredientsAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter.setItems(newRecipe, ingredients, ingredientQuantities, ingredientUnits, hasIngredient);

        instructionsAdapter = new InstructionRecViewAdapter(this, true);
        instructionsRecView.setAdapter(instructionsAdapter);
        instructionsRecView.setLayoutManager(new LinearLayoutManager(this));
        instructionsAdapter.setItems(newRecipe, instructions);

        //If an ingredient is added, make space for it and prompt for user input
        btnAddIngredient.setOnClickListener(v -> {
            newRecipe.getIngredients().add("");
            newRecipe.getIngredientQuantities().add("");
            newRecipe.getIngredientUnits().add("");
            ingredientsAdapter.notifyItemInserted(newRecipe.getIngredients().size() - 1);
            EditIngredientPopUp.showDialog(this, newRecipe, newRecipe.getIngredients().size() - 1, true, ingredientsAdapter);
        });

        //If an instruction is added, make space for it and prompt user for input
        btnAddInstruction.setOnClickListener(v -> {
            newRecipe.getInstructions().add("");
            instructionsAdapter.notifyItemInserted(newRecipe.getInstructions().size() - 1);
            EditInstructionPopUp.showDialog(this, newRecipe, newRecipe.getInstructions().size() - 1, true, instructionsAdapter);
        });

        //Cancels the add activity
        btnCancel.setOnClickListener(v -> finish());

        //Adds the item to the database
        btnSubmit.setOnClickListener(v -> addItem());
    }

    //Initializes the views
    private void initViews() {
        inputName = findViewById(R.id.addRecipeName);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        instructionsRecView = findViewById(R.id.instructionsRecView);

        btnCancel = findViewById(R.id.btnCancelAddRecipe);
        btnSubmit = findViewById(R.id.btnSubmitAddRecipe);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddInstruction = findViewById(R.id.btnAddInstruction);
    }

    //Handles validating and adding the item
    private void addItem() {
        //Gets the user input and ingredient/instruction lists from the adapters
        String name = inputName.getText().toString();
        ArrayList<String> ingredientNames = ingredientsAdapter.getIngredientNames();
        ArrayList<String> ingredientQuantities = ingredientsAdapter.getIngredientQuantities();
        ArrayList<String> ingredientUnits = ingredientsAdapter.getIngredientUnits();
        ArrayList<String> instructions = instructionsAdapter.getInstructions();
        //Builds the new recipe
        Recipe newRecipe = new Recipe(name, ingredientNames, ingredientQuantities, ingredientUnits, instructions);
        try {
            //Checks for duplicates and validates inputs, then adds
            if (listActions.getDuplicateByName(newRecipe, recipeActions.getRecipeList()) == null) {
                recipeActions.addToRecipes(newRecipe);
                finish();
            } else {
                Toast.makeText(AddRecipeActivity.this, "A Recipe with this name already exists in Inventory.", Toast.LENGTH_SHORT).show();
            }
        } catch (InvalidInputException e) {
            Toast.makeText(AddRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
