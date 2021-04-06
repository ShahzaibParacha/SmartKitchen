package com.smartkitchen.presentation.recipe;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Recipe;

public class EditIngredientPopUp {

    final static int stringMaxLength = 20;
    final static int intMaxLength = 9;

    static IListValidation validation = new ListValidation();

    public static void showDialog(Context context, Recipe recipe, int position, boolean isAdding, IngredientsRecViewAdapter adapter){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(isAdding){
            builder.setTitle("Add New Ingredient:");
        }
        else{
            builder.setTitle("Edit " + recipe.getIngredients().get(position) + " Information");
        }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editName = new EditText(context);
        editName.setInputType(InputType.TYPE_CLASS_TEXT);
        editName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(stringMaxLength)});
        editName.setHint("Name");
        editName.setText(recipe.getIngredients().get(position));
        layout.addView(editName);

        final EditText editQuantity = new EditText(context);
        editQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        editQuantity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(intMaxLength)});
        editQuantity.setHint("Quantity");
        editQuantity.setText(recipe.getIngredientQuantities().get(position));
        layout.addView(editQuantity);

        final EditText editUnit = new EditText(context);
        editUnit.setInputType(InputType.TYPE_CLASS_TEXT);
        editUnit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(stringMaxLength)});
        editUnit.setHint("Units");
        editUnit.setText(recipe.getIngredientUnits().get(position));
        layout.addView(editUnit);

        builder.setView(layout);

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Submit", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdding){
                    recipe.getIngredients().remove(recipe.getIngredients().size()-1);
                    recipe.getIngredientQuantities().remove(recipe.getIngredientQuantities().size()-1);
                    recipe.getIngredientUnits().remove(recipe.getIngredientUnits().size()-1);
                    adapter.notifyItemRemoved(recipe.getIngredients().size());
                }
                dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    validation.checkIngredientInputs(editName.getText().toString(), editQuantity.getText().toString(), editUnit.getText().toString());
                    recipe.getIngredients().set(position, editName.getText().toString());
                    recipe.getIngredientQuantities().set(position, editQuantity.getText().toString());
                    recipe.getIngredientUnits().set(position, editUnit.getText().toString());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch(InvalidInputException e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
