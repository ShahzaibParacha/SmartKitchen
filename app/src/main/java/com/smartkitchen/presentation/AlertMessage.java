package com.smartkitchen.presentation;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;

public class AlertMessage {
    private static IListActions listActions = new ListActions();

    public static void showDialog(Context context, Item item){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Adding item to grocery list, please set quantity you wish to buy:");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);


        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.setQuantityToBuy(Integer.parseInt(input.getText().toString()));
                listActions.addToGrocery(item);
                Intent intent = new Intent(context, CurrentInventoryActivity.class);
                context.startActivity(intent);
            }
        });

        builder.show();
    }
}
