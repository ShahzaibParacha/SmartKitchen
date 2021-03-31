package com.smartkitchen.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartkitchen.business.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Recipe;

public class EditInstructionPopUp {

    static IListValidation validation = new ListValidation();

    public static void showDialog(Context context, Recipe recipe, int position, boolean isAdding, InstructionRecViewAdapter adapter){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(isAdding){
            builder.setTitle("Add New Instruction");
        }
        else{
            builder.setTitle("Edit Instruction " + position+1);
        }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editInstruction = new EditText(context);
        editInstruction.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editInstruction.setHint("Instruction");
        editInstruction.setText(recipe.getInstructions().get(position));
        layout.addView(editInstruction);

        builder.setView(layout);

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Submit", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdding){
                    recipe.getInstructions().remove(recipe.getInstructions().size()-1);
                    adapter.notifyItemRemoved(recipe.getInstructions().size());
                }
                dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    validation.checkInstructionInput(editInstruction.getText().toString());
                    recipe.getInstructions().set(position, editInstruction.getText().toString());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch(InvalidInputException e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
