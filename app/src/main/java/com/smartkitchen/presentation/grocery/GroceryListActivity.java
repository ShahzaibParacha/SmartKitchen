package com.smartkitchen.presentation.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartkitchen.R;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;
import com.smartkitchen.presentation.ParentActivity;

import java.util.ArrayList;

//Main screen that displays the grocery list
public class GroceryListActivity extends ParentActivity {

    //Access to relevant business layer methods
    private final IGroceryActions groceryActions = new GroceryActions();

    private GroceryListRecViewAdapter adapter;

    //Total sum and buy all, and add button
    private TextView totalSum;
    private Button btnBuyAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_list_activity);

        //Set the text and colour of the taskbar
        setTitle("Grocery List");
        setColour(ContextCompat.getColor(this, R.color.greenColour3));

        //Create the adapter and find the list view
        adapter = new GroceryListRecViewAdapter(this);
        //Get the list view of the grocery list and the buttons
        RecyclerView groceryListRecView = findViewById(R.id.groceryListRecView);
        FloatingActionButton btnAdd = findViewById(R.id.btnGoToAddGroceryActivity);
        btnBuyAll = findViewById(R.id.btnBuyAll);
        totalSum = findViewById(R.id.groceryListTotalSum);

        //Get the total sum of the grocery list
        totalSum.setText("$" + groceryActions.getGroceryListTotal());

        ArrayList<Item> groceryList = groceryActions.getGroceryList();

        //Moves to add grocery item screen
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(GroceryListActivity.this, AddGroceryItemActivity.class);
            startActivity(intent);
        });

        //If the grocery list is empty, disable the buy all button
        btnBuyAll.setEnabled(groceryList.size() != 0);

        btnBuyAll.setOnClickListener(v -> {
            try {
                //Buy all items then refresh the page
                groceryActions.buyAll();
                onResume();
            } catch (InvalidInputException e) {
                Toast.makeText(GroceryListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Set up the list view
        groceryListRecView.setAdapter(adapter);
        groceryListRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(groceryList);
    }

    //Refreshes the information on the page
    @Override
    protected void onResume() {
        super.onResume();
        //Pass in updated grocery list, recalculate total sum, check if it should be enabled
        adapter.setItems(groceryActions.getGroceryList());
        totalSum.setText("$" + groceryActions.getGroceryListTotal());
        btnBuyAll.setEnabled(groceryActions.getGroceryList().size() != 0);
    }

}
