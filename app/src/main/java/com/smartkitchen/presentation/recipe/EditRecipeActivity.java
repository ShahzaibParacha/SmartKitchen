package com.smartkitchen.presentation.recipe;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditRecipeActivity extends ParentActivity {

    //Lists and adapters for ingredients and instructions
    private RecyclerView ingredientsRecView, instructionsRecView;
    private IngredientsRecViewAdapter ingredientsAdapter;
    private InstructionRecViewAdapter instructionsAdapter;

    //Access to relevant business layer methods
    IRecipeActions recipeActions = new RecipeActions();
    IListActions listActions = new ListActions();

    //Fields for user input, relevant buttons
    private TextView title;
    private EditText inputName;
    private Button btnCancel, btnSubmit, btnAddIngredient, btnAddInstruction;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        //Initializes the views
        initViews();

        //Gets the recipe to be edited
        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra("Position", -1);
        recipe = recipeActions.getRecipeList().get(itemPosition);

        title.setText("Edit " + recipe.getName() + " Recipe");

        //Sets the text and colour of the taskbar
        setTitle("Edit " + recipe.getName() + " Recipe");
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        inputName.setText(recipe.getName());

        //Sets up the ingredients/instructions lists
        ingredientsAdapter = new IngredientsRecViewAdapter(this, true);
        ingredientsRecView.setAdapter(ingredientsAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter.setItems(recipe, recipe.getIngredients(), recipe.getIngredientQuantities(), recipe.getIngredientUnits(), recipe.getHasIngredient());

        instructionsAdapter = new InstructionRecViewAdapter(this, true);
        instructionsRecView.setAdapter(instructionsAdapter);
        instructionsRecView.setLayoutManager(new LinearLayoutManager(this));
        instructionsAdapter.setItems(recipe, recipe.getInstructions());

        //If an ingredient is added, make space and prompt the user for input
        btnAddIngredient.setOnClickListener(v -> {
            recipe.getIngredients().add("");
            recipe.getIngredientQuantities().add("");
            recipe.getIngredientUnits().add("");
            ingredientsAdapter.notifyItemInserted(recipe.getIngredients().size() - 1);
            EditIngredientPopUp.showDialog(this, recipe, recipe.getIngredients().size() - 1, true, ingredientsAdapter);
        });

        //If an instruction is added, make space and prompt the user for input
        btnAddInstruction.setOnClickListener(v -> {
            recipe.getInstructions().add("");
            instructionsAdapter.notifyItemInserted(recipe.getInstructions().size() - 1);
            EditInstructionPopUp.showDialog(this, recipe, recipe.getInstructions().size() - 1, true, instructionsAdapter);
        });

        //Cancels the edit screen
        btnCancel.setOnClickListener(v -> finish());

        //Submits the edit
        btnSubmit.setOnClickListener(v -> {
            try {
                //Validates the input, then updates the recipe
                listActions.editValidation(checkData());
                updateRecipe(recipe);
                finish();
            } catch (InvalidInputException e) {
                Toast.makeText(EditRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Intializes the views
    private void initViews() {
        title = findViewById(R.id.txtEditRecipeTitle);

        inputName = findViewById(R.id.editRecipeName);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        instructionsRecView = findViewById(R.id.instructionsRecView);

        btnCancel = findViewById(R.id.btnCancelEditRecipe);
        btnSubmit = findViewById(R.id.btnSubmitEditRecipe);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddInstruction = findViewById(R.id.btnAddInstruction);
    }

    //Sets the recipe fields, then updates the database
    private void updateRecipe(Recipe recipe) {
        recipe.setName(inputName.getText().toString());
        recipe.setIngredients(ingredientsAdapter.getIngredientNames());
        recipe.setIngredientQuantities(ingredientsAdapter.getIngredientQuantities());
        recipe.setIngredientUnits(ingredientsAdapter.getIngredientUnits());
        recipe.setInstructions(instructionsAdapter.getInstructions());
        recipeActions.updateRecipe(recipe);
    }

    //Grabs the info from the user input and stores in a Recipe object to be checked
    private Recipe checkData() {
        String checkName = inputName.getText().toString();
        ArrayList<String> checkIngredients = ingredientsAdapter.getIngredientNames();
        ArrayList<String> checkQuantities = ingredientsAdapter.getIngredientQuantities();
        ArrayList<String> checkUnits = ingredientsAdapter.getIngredientUnits();
        ArrayList<String> checkInstructions = instructionsAdapter.getInstructions();

        return new Recipe(checkName, checkIngredients, checkQuantities, checkUnits, checkInstructions);
    }
}
