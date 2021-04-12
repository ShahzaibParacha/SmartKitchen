package com.smartkitchen.presentation.recipe.instruction;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.objects.Recipe;

//Pop up that takes user input for an instruction
public class EditInstructionPopUp {

    //Access to validation methods
    static IListValidation validation = new ListValidation();

    public static void showDialog(Context context, Recipe recipe, int position, boolean isAdding, InstructionRecViewAdapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //Sets the text in the dialog
        if (isAdding) {
            builder.setTitle("Add New Instruction");
        } else {
            builder.setTitle("Edit Instruction " + position + 1);
        }

        //Creates the parent layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Creates the instruction input field
        final EditText editInstruction = new EditText(context);
        editInstruction.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editInstruction.setHint("Instruction");
        editInstruction.setText(recipe.getInstructions().get(position));
        layout.addView(editInstruction);

        //Sets the layout
        builder.setView(layout);

        //Creates the negative and positive buttons
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Submit", null);

        //Creates and displays the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //On Click for cancel button
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            if (isAdding) {
                //If it was adding, remove the space reserved
                recipe.getInstructions().remove(recipe.getInstructions().size() - 1);
                adapter.notifyItemRemoved(recipe.getInstructions().size());
            }
            dialog.dismiss();
        });

        //On Click for submit button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                //Validate the input then put the information in the list
                validation.checkInstructionInput(editInstruction.getText().toString());
                recipe.getInstructions().set(position, editInstruction.getText().toString());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            } catch (InvalidInputException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
