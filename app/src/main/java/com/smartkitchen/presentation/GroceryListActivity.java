package com.smartkitchen.presentation;

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
import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.IGroceryActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class GroceryListActivity extends ParentActivity {

    private final IGroceryActions groceryActions = new GroceryActions();

    //The list view of the grocery list and its adapter
    private RecyclerView groceryListRecView;
    private GroceryListRecViewAdapter adapter;
    private TextView totalSum;
    private Button btnBuyAll;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_list_activity);
        setTitle("Grocery List");
        setColour(ContextCompat.getColor(this, R.color.greenColour3));

        //Create the adapter and find the list view
        adapter = new GroceryListRecViewAdapter(this);
        groceryListRecView = findViewById(R.id.groceryListRecView);
        btnAdd = findViewById(R.id.btnGoToAddGroceryActivity);
        btnBuyAll = findViewById(R.id.btnBuyAll);
        totalSum = findViewById(R.id.groceryListTotalSum);

        totalSum.setText("$" + groceryActions.getGroceryListTotal());

        ArrayList<Item> groceryList = groceryActions.getGroceryList();

        //Moves to add grocery item screen
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(GroceryListActivity.this, AddGroceryItemActivity.class);
            startActivity(intent);
        });

        btnBuyAll.setEnabled(groceryList.size() != 0);

        btnBuyAll.setOnClickListener(v -> {
            try {
                groceryActions.buyAll();
                onResume();
            }
            catch (InvalidInputException e){
                Toast.makeText(GroceryListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Set up the list view
        groceryListRecView.setAdapter(adapter);
        groceryListRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(groceryList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setItems(groceryActions.getGroceryList());
        totalSum.setText("$" + groceryActions.getGroceryListTotal());
        btnBuyAll.setEnabled(groceryActions.getGroceryList().size() != 0);
    }

}
