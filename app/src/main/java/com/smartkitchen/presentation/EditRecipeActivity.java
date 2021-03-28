package com.smartkitchen.presentation;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartkitchen.R;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.IRecipeActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.business.RecipeActions;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public class EditRecipeActivity extends ParentActivity {

    private RecyclerView ingredientsRecView, instructionsRecView;
    private IngredientsRecViewAdapter ingredientsAdapter;
    private InstructionRecViewAdapter instructionsAdapter;

    IRecipeActions recipeActions = new RecipeActions();
    IListActions listActions = new ListActions();

    private TextView title;
    private EditText inputName;
    private Button btnCancel, btnSubmit, btnAddIngredient, btnAddInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        setColour(ContextCompat.getColor(this, R.color.redColour3));

        initViews();

        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra("Position", -1);
        Recipe recipe = recipeActions.getRecipeList().get(itemPosition);

        title.setText("Edit " + recipe.getName() + " Recipe");
        setTitle("Edit " + recipe.getName() + " Recipe");

        inputName.setText(recipe.getName());

        ingredientsAdapter = new IngredientsRecViewAdapter(this, true);
        ingredientsRecView.setAdapter(ingredientsAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter.setItems(recipe, recipe.getIngredients(), recipe.getIngredientQuantities(), recipe.getIngredientUnits(), recipe.getHasIngredient());

        instructionsAdapter = new InstructionRecViewAdapter(this, true);
        instructionsRecView.setAdapter(instructionsAdapter);
        instructionsRecView.setLayoutManager(new LinearLayoutManager(this));
        instructionsAdapter.setItems(recipe.getInstructions());

        btnAddIngredient.setOnClickListener(v -> {
            recipe.getIngredients().add("");
            recipe.getIngredientQuantities().add("");
            recipe.getIngredientUnits().add("");
            //newRecipe.getHasIngredient().add(true);
            ingredientsAdapter.notifyItemInserted(recipe.getIngredients().size()-1);
        });

        btnAddInstruction.setOnClickListener(v -> {
            recipe.getInstructions().add("");
            EditIngredientPopUp.showDialog(this, recipe, recipe.getInstructions().size(), true);
            instructionsAdapter.notifyItemInserted(recipe.getInstructions().size()-1);
        });

        btnCancel.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listActions.editValidation(checkData());
                    updateRecipe(recipe);
                    finish();
                }
                catch (InvalidInputException e) {
                    Toast.makeText(EditRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(){
        title = findViewById(R.id.txtEditRecipeTitle);

        inputName = findViewById(R.id.editRecipeName);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        instructionsRecView = findViewById(R.id.instructionsRecView);

        btnCancel = findViewById(R.id.btnCancelEditRecipe);
        btnSubmit = findViewById(R.id.btnSubmitEditRecipe);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddInstruction = findViewById(R.id.btnAddInstruction);
    }

    private void updateRecipe(Recipe recipe){
        recipe.setName(inputName.getText().toString());
        recipe.setIngredients(ingredientsAdapter.getIngredientNames());
        recipe.setIngredientQuantities(ingredientsAdapter.getIngredientQuantities());
        recipe.setIngredientUnits(ingredientsAdapter.getIngredientUnits());
        recipe.setInstructions(instructionsAdapter.getInstructions());
        recipeActions.updateRecipe(recipe);
    }

    //Grabs the info from the text field and stores in an Item object
    private Recipe checkData() {
        //Temporary parameter until edit button is created
        String checkName = inputName.getText().toString();
        ArrayList<String> checkIngredients = ingredientsAdapter.getIngredientNames();
        ArrayList<String> checkQuantities = ingredientsAdapter.getIngredientQuantities();
        ArrayList<String> checkUnits = ingredientsAdapter.getIngredientUnits();
        ArrayList<String> checkInstructions = instructionsAdapter.getInstructions();

        Recipe checkRecipe = new Recipe(checkName, checkIngredients, checkQuantities, checkUnits, checkInstructions);
        return checkRecipe;
    }
}
