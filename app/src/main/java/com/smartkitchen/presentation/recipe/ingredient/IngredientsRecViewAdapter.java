package com.smartkitchen.presentation.recipe.ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

//Adapter for displaying the list of ingredients
public class IngredientsRecViewAdapter extends RecyclerView.Adapter<IngredientsRecViewAdapter.ViewHolder> {

    //Lists to store ingredient information
    ArrayList<String> ingredientNames = new ArrayList<>();
    ArrayList<String> ingredientQuantities = new ArrayList<>();
    ArrayList<String> ingredientUnits = new ArrayList<>();
    ArrayList<Boolean> hasIngredient = new ArrayList<>();
    //Parent recipe
    Recipe recipe;
    Context mContext;
    boolean isEditable;
    IngredientsRecViewAdapter adapter = this;

    //Constructor
    public IngredientsRecViewAdapter(Context mContext, boolean isEditable) {
        this.mContext = mContext;
        this.isEditable = isEditable;
    }

    //Creates the list view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Passes in the ingredient card view as the item to be placed in the list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new ViewHolder(view);
    }

    //Sets the information inside of the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Sets the default data
        setData(holder, position);

        //Determines if the editable or noneditable version should be displayed
        if (isEditable) {
            holder.editable.setVisibility(View.VISIBLE);
            holder.notEditable.setVisibility(View.GONE);
        } else {
            holder.editable.setVisibility(View.GONE);
            holder.notEditable.setVisibility(View.VISIBLE);
            //Greys out unavailable ingredient in view screen
            if (!hasIngredient.get(position)) {
                holder.parent.getBackground().setTint(ContextCompat.getColor(mContext, R.color.redColour1Disabled));
            }
        }

        //Removes an ingredient from the lists
        holder.btnRemove.setOnClickListener(v -> {
            ingredientNames.remove(ingredientNames.get(position));
            ingredientQuantities.remove(ingredientQuantities.get(position));
            ingredientUnits.remove(ingredientUnits.get(position));
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });

        //Calls up the popup for user input
        holder.btnEdit.setOnClickListener(v -> EditIngredientPopUp.showDialog(mContext, recipe, position, false, adapter));
    }

    //Sets the default data for the ingredient
    private void setData(ViewHolder holder, int position) {
        holder.name.setText(ingredientNames.get(position));
        holder.quantity.setText(ingredientQuantities.get(position));
        holder.units.setText(ingredientUnits.get(position));

        holder.edtName.setText(ingredientNames.get(position));
        holder.edtQuantity.setText(ingredientQuantities.get(position));
        holder.edtUnits.setText(ingredientUnits.get(position));
    }

    //Return the size of the list
    @Override
    public int getItemCount() {
        return ingredientNames.size();
    }

    //Passes in the input lists
    public void setItems(Recipe recipe, ArrayList<String> ingredientNames, ArrayList<String> ingredientQuantities, ArrayList<String> ingredientUnits, ArrayList<Boolean> hasIngredient) {
        this.recipe = recipe;
        this.ingredientNames = ingredientNames;
        this.ingredientQuantities = ingredientQuantities;
        this.ingredientUnits = ingredientUnits;
        this.hasIngredient = hasIngredient;
    }

    //Returns the ingredient names
    public ArrayList<String> getIngredientNames() {
        return ingredientNames;
    }

    //Returns the ingredient quantities
    public ArrayList<String> getIngredientQuantities() {
        return ingredientQuantities;
    }

    //Returns the ingredient units
    public ArrayList<String> getIngredientUnits() {
        return ingredientUnits;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView parent;
        private final TextView name, quantity, units;
        private final TextView edtName, edtQuantity, edtUnits;
        private final LinearLayout editable, notEditable;
        private final Button btnRemove, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.ingredientCardView);

            name = itemView.findViewById(R.id.txtIngredientName);
            quantity = itemView.findViewById(R.id.txtIngredientQuantity);
            units = itemView.findViewById(R.id.txtIngredientUnits);

            edtName = itemView.findViewById(R.id.txtEditIngredientName);
            edtQuantity = itemView.findViewById(R.id.txtEditIngredientQuantity);
            edtUnits = itemView.findViewById(R.id.txtEditIngredientUnits);

            editable = itemView.findViewById(R.id.ingredientEditable);
            notEditable = itemView.findViewById(R.id.ingredientNonEditable);

            btnRemove = itemView.findViewById(R.id.btnRemoveIngredient);
            btnEdit = itemView.findViewById(R.id.btnEditIngredient);
        }
    }
}

