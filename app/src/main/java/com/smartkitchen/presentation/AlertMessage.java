package com.smartkitchen.presentation;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;

public class AlertMessage {
    private static IListActions listActions = new ListActions();

    //Builds, displays and takes input
    public static void showDialog(Context context, Item item, boolean returnToMain){

        //Creates the object and sets the text
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Adding " + item.getName() + " to grocery list, please set quantity you wish to buy:");

        //Creates the input field and assigns it to the object
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        //Create the submit button, the on click listener will be overrided later
        builder.setPositiveButton("Submit", null);
        //Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //Implements the submit button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks for valid input before continuing
                if(!input.getText().toString().equals("")) {
                    //Take the input and modify item object
                    item.setQuantityToBuy(Integer.parseInt(input.getText().toString()));
                    //If the item is not in the grocery list yet, add it in
                    if (!listActions.isInGrocery(item)) {
                        try {
                            listActions.addToGrocery(item);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    //If the function needs to return to the inventory screen, do that here
                    if (returnToMain) {
                        Intent intent = new Intent(context, CurrentInventoryActivity.class);
                        context.startActivity(intent);
                    }
                    //Closes the dialog
                    dialog.dismiss();
                }
                //If not valid input, show toast message
                else{
                    Toast.makeText(context, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
