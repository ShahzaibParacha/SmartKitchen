package com.smartkitchen.presentation.recipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.interfaces.IRecipeActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.business.implementation.RecipeActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.presentation.AlertMessage;

import java.util.ArrayList;

public class RecipeRecViewAdapter extends RecyclerView.Adapter<RecipeRecViewAdapter.ViewHolder> {

    private static final IListActions listActions = new ListActions();
    private static final IInventoryActions inventoryActions = new InventoryActions();
    private static final IGroceryActions groceryActions = new GroceryActions();
    private static final IListValidation validation = new ListValidation();

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context mContext;

    private IRecipeActions recipeActions = new RecipeActions();

    public RecipeRecViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(recipes.get(position).getName());
        holder.isPossible.setText("" + recipes.get(position).haveAllIngredients());

        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ViewRecipeInfoActivity.class);
            intent.putExtra("Position", position);
            mContext.startActivity(intent);
        });

        holder.btnRemove.setOnClickListener(v -> {
            Recipe recipe = recipes.get(position);
            recipeActions.removeRecipe(recipe);
            ((RecipeListActivity)mContext).onResume();
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditRecipeActivity.class);
            intent.putExtra("Position", position);
            mContext.startActivity(intent);
        });

        holder.btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipes.get(position);
                make(recipe);
                Toast.makeText(mContext, "Removed Ingredients From Inventory", Toast.LENGTH_SHORT).show();
                ((RecipeListActivity)mContext).onResume();
            }
        });

        holder.btnAddToGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipes.get(position);
                addToGrocery(recipe);
                Toast.makeText(mContext, "Added Missing Ingredients To Grocery List", Toast.LENGTH_SHORT).show();
            }
        });

        //On Click Listeners to expand/collapse the cardview
        holder.downArrow.setOnClickListener(v -> {
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            holder.upArrow.setVisibility(View.VISIBLE);
        });

        holder.upArrow.setOnClickListener(v -> {
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
            holder.upArrow.setVisibility(View.GONE);
        });

        if(!recipes.get(position).haveAllIngredients()){
            holder.btnMake.setEnabled(false);
            holder.btnAddToGrocery.setEnabled(true);
        }
        else{
            holder.btnMake.setEnabled(true);
            holder.btnAddToGrocery.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setItems(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void addToGrocery(Recipe recipe) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (!recipe.getHasIngredient().get(i)) {
                Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
                //Take the input and modify item object
                if (currIngredient == null) {
                    currIngredient = new Item(recipe.getIngredients().get(i), 0, recipe.getIngredientUnits().get(i),
                            Integer.parseInt(recipe.getIngredientQuantities().get(i)), 0, new ArrayList<String>(),
                            0, 0.0);
                } else {
                    currIngredient.setQuantityToBuy(Integer.parseInt(recipe.getIngredientQuantities().get(i)));
                }
                //If the item is not in the grocery list yet, add it in
                Item duplicate = listActions.getDuplicateByName(currIngredient, groceryActions.getGroceryList());
                if (duplicate == null) {
                    try {
                        groceryActions.addToGrocery(currIngredient);
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    duplicate.setQuantityToBuy(duplicate.getQuantityToBuy() + currIngredient.getQuantityToBuy());
                    groceryActions.updateGroceryItem(duplicate);
                }
            }
        }
    }

    public void make(Recipe recipe){
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
            currIngredient.setQuantity(currIngredient.getQuantity()-Integer.parseInt(recipe.getIngredientQuantities().get(i)));
            inventoryActions.updateInventoryItem(currIngredient);
            if(validation.thresholdStatus(currIngredient)){
                AlertMessage.showDialog(mContext, currIngredient, false);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView name;
        private TextView isPossible;
        private ImageView upArrow, downArrow;
        private LinearLayout expandedLayout;
        private Button btnEdit, btnRemove, btnMake, btnAddToGrocery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.recipeItemCardView);

            name = itemView.findViewById(R.id.txtRecipeName);
            isPossible = itemView.findViewById(R.id.txtRecipePossible);

            upArrow = itemView.findViewById(R.id.itemUpArrow);
            downArrow = itemView.findViewById(R.id.itemDownArrow);

            expandedLayout = itemView.findViewById(R.id.recipeViewExpanded);

            btnEdit = itemView.findViewById(R.id.btnEditRecipe);
            btnRemove = itemView.findViewById(R.id.btnRemoveRecipe);
            btnMake = itemView.findViewById(R.id.btnMakeRecipe);
            btnAddToGrocery = itemView.findViewById(R.id.btnAddToGroceryList);
        }
    }
}

