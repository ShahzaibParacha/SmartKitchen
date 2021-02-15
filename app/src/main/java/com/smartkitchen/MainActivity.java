package com.smartkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //The list view of the current inventory and its adapter
    private RecyclerView itemsRecView;
    private ItemRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the adapter and find the list view
        adapter = new ItemRecViewAdapter(this);
        itemsRecView = findViewById(R.id.itemRecView);

        //Set up the list view
        itemsRecView.setAdapter(adapter);
        itemsRecView.setLayoutManager(new LinearLayoutManager(this));

        //TODO: This is where the Current Inventory array list will be passed in
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Milk", 4, "L"));
        items.add(new Item("Sugar", 100, "g"));
        items.add(new Item("Pizza", 10, "Boxes"));

        adapter.setItems(items);
    }
}