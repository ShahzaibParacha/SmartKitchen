package com.smartkitchen.presentation;

import android.content.Context;
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

import java.util.ArrayList;

public class IngredientsRecViewAdapter extends RecyclerView.Adapter<IngredientsRecViewAdapter.ViewHolder>{

    ArrayList<String> ingredientNames = new ArrayList<>();
    ArrayList<String> ingredientQuantities = new ArrayList<>();
    ArrayList<String> ingredientUnits = new ArrayList<>();
    ArrayList<Boolean> hasIngredient = new ArrayList<>();
    Context mContext;
    boolean isEditable;

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
        holder.name.setText(ingredientNames.get(position));
        holder.quantity.setText(ingredientQuantities.get(position));

        holder.edtName.setText(ingredientNames.get(position));
        holder.edtQuantity.setText(ingredientQuantities.get(position));
        holder.edtUnits.setText(ingredientUnits.get(position));

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

        holder.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientNames.set(position, holder.edtName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientQuantities.set(position, holder.edtQuantity.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.edtUnits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientUnits.set(position, holder.edtUnits.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientNames.size();
    }

    public void setItems(ArrayList<String> ingredientNames, ArrayList<String> ingredientQuantities, ArrayList<String> ingredientUnits, ArrayList<Boolean> hasIngredient){
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
        private TextView name, quantity;
        private EditText edtName, edtQuantity, edtUnits;
        private LinearLayout editable, notEditable;
        private Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.ingredientCardView);

            name = itemView.findViewById(R.id.txtIngredientName);
            quantity = itemView.findViewById(R.id.txtIngredientQuantity);

            edtName = itemView.findViewById(R.id.inputIngredientName);
            edtQuantity = itemView.findViewById(R.id.inputIngredientQuantity);
            edtUnits = itemView.findViewById(R.id.inputIngredientUnits);

            editable = itemView.findViewById(R.id.ingredientEditable);
            notEditable = itemView.findViewById(R.id.ingredientNonEditable);

            btnRemove = itemView.findViewById(R.id.btnRemoveIngredient);
        }
    }
}

