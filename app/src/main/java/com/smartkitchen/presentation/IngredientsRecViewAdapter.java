package com.smartkitchen.presentation;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public class IngredientsRecViewAdapter extends RecyclerView.Adapter<IngredientsRecViewAdapter.ViewHolder>{

    ArrayList<String> ingredientNames = new ArrayList<>();
    ArrayList<String> ingredientQuantities = new ArrayList<>();
    ArrayList<String> ingredientUnits = new ArrayList<>();
    ArrayList<Boolean> hasIngredient = new ArrayList<>();
    Recipe recipe;
    Context mContext;
    boolean isEditable;
    IngredientsRecViewAdapter adapter = this;

    public IngredientsRecViewAdapter(Context mContext, boolean isEditable){
        this.mContext = mContext;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setData(holder, position);

        if(isEditable){
            holder.editable.setVisibility(View.VISIBLE);
            holder.notEditable.setVisibility(View.GONE);
        }
        else{
            holder.editable.setVisibility(View.GONE);
            holder.notEditable.setVisibility(View.VISIBLE);
            if(!hasIngredient.get(position)) {
                holder.parent.getBackground().setTint(ContextCompat.getColor(mContext, R.color.redColour1Disabled));
            }
        }

        holder.btnRemove.setOnClickListener(v -> {
            ingredientNames.remove(ingredientNames.get(position));
            ingredientQuantities.remove(ingredientQuantities.get(position));
            ingredientUnits.remove(ingredientUnits.get(position));
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditIngredientPopUp.showDialog(mContext, recipe, position, false, adapter);
            }
        });
    }

    private void setData(ViewHolder holder, int position){
        holder.name.setText(ingredientNames.get(position));
        holder.quantity.setText(ingredientQuantities.get(position));
        holder.units.setText(ingredientUnits.get(position));

        holder.edtName.setText(ingredientNames.get(position));
        holder.edtQuantity.setText(ingredientQuantities.get(position));
        holder.edtUnits.setText(ingredientUnits.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientNames.size();
    }

    public void setItems(Recipe recipe, ArrayList<String> ingredientNames, ArrayList<String> ingredientQuantities, ArrayList<String> ingredientUnits, ArrayList<Boolean> hasIngredient){
        this.recipe = recipe;
        this.ingredientNames = ingredientNames;
        this.ingredientQuantities = ingredientQuantities;
        this.ingredientUnits = ingredientUnits;
        this.hasIngredient = hasIngredient;
    }

    public ArrayList<String> getIngredientNames(){
        return ingredientNames;
    }

    public ArrayList<String> getIngredientQuantities(){
        return ingredientQuantities;
    }

    public ArrayList<String> getIngredientUnits(){
        return ingredientUnits;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView name, quantity, units;
        private TextView edtName, edtQuantity, edtUnits;
        private LinearLayout editable, notEditable;
        private Button btnRemove, btnEdit;

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

