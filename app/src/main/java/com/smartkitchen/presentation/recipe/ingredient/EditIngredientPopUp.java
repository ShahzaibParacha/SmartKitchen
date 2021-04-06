package com.smartkitchen.presentation.recipe.ingredient;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Recipe;

//Pop up that takes user input for an ingredient
public class EditIngredientPopUp {

    //Maximum lengths for input strings
    final static int stringMaxLength = 20;
    final static int intMaxLength = 9;

    //Access to validation methods
    static IListValidation validation = new ListValidation();

    public static void showDialog(Context context, Recipe recipe, int position, boolean isAdding, IngredientsRecViewAdapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //Sets the text in the dialog
        if (isAdding) {
            builder.setTitle("Add New Ingredient:");
        } else {
            builder.setTitle("Edit " + recipe.getIngredients().get(position) + " Information");
        }

        //Creates the parent layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Creates the name input field
        final EditText editName = new EditText(context);
        editName.setInputType(InputType.TYPE_CLASS_TEXT);
        editName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(stringMaxLength)});
        editName.setHint("Name");
        editName.setText(recipe.getIngredients().get(position));
        layout.addView(editName);

        //Creates the quantity input field
        final EditText editQuantity = new EditText(context);
        editQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        editQuantity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(intMaxLength)});
        editQuantity.setHint("Quantity");
        editQuantity.setText(recipe.getIngredientQuantities().get(position));
        layout.addView(editQuantity);

        //Creates the unit input field
        final EditText editUnit = new EditText(context);
        editUnit.setInputType(InputType.TYPE_CLASS_TEXT);
        editUnit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(stringMaxLength)});
        editUnit.setHint("Units");
        editUnit.setText(recipe.getIngredientUnits().get(position));
        layout.addView(editUnit);

        //Sets the layout
        builder.setView(layout);

        //Create positive/negative buttons
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Submit", null);

        //Create and display the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //On Click for cancel button
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            if (isAdding) {
                //If it was adding a new item, remove the space reserved for it from the list
                recipe.getIngredients().remove(recipe.getIngredients().size() - 1);
                recipe.getIngredientQuantities().remove(recipe.getIngredientQuantities().size() - 1);
                recipe.getIngredientUnits().remove(recipe.getIngredientUnits().size() - 1);
                adapter.notifyItemRemoved(recipe.getIngredients().size());
            }
            dialog.dismiss();
        });

        //On click for submit button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                //Validate input then add information to the recipe ingredient lists
                validation.checkIngredientInputs(editName.getText().toString(), editQuantity.getText().toString(), editUnit.getText().toString());
                recipe.getIngredients().set(position, editName.getText().toString());
                recipe.getIngredientQuantities().set(position, editQuantity.getText().toString());
                recipe.getIngredientUnits().set(position, editUnit.getText().toString());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            } catch (InvalidInputException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
