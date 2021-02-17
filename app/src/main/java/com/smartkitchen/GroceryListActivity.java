package com.smartkitchen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {

    //The list view of the grocery list and its adapter
    private RecyclerView groceryListRecView;
    private GroceryListRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_list_activity);

        //Create the adapter and find the list view
        adapter = new GroceryListRecViewAdapter(this);
        groceryListRecView = findViewById(R.id.groceryListRecView);

        //Set up the list view
        groceryListRecView.setAdapter(adapter);
        groceryListRecView.setLayoutManager(new LinearLayoutManager(this));

        //TODO: This is where the Grocery List array list will be passed in
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Milk", 4, "L", 8));
        items.add(new Item("Sugar", 100, "g", 200));
        items.add(new Item("Pizza", 10, "Boxes", 5));

        adapter.setItems(items);

    }
}
