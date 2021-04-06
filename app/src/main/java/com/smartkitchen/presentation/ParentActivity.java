package com.smartkitchen.presentation;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.R;
import com.smartkitchen.presentation.grocery.GroceryListActivity;
import com.smartkitchen.presentation.inventory.CurrentInventoryActivity;
import com.smartkitchen.presentation.recipe.RecipeListActivity;

//Parent activity for all screens, handles the taskbar and menu
public class ParentActivity extends AppCompatActivity {

    //Sets the colour of the taskbar
    public void setColour(int colour) {
        ActionBar actionbar = getSupportActionBar();
        ColorDrawable drawable = new ColorDrawable(colour);
        actionbar.setBackgroundDrawable(drawable);
        Window window = getWindow();
        window.setStatusBarColor(colour);
    }

    //Creates the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Handles menu navigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuCurrentInventory:
                intent = new Intent(ParentActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuGroceryList:
                intent = new Intent(ParentActivity.this, GroceryListActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuRecipes:
                intent = new Intent(ParentActivity.this, RecipeListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
