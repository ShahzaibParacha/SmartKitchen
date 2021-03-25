package com.smartkitchen.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class IngredientsRecViewAdapter extends RecyclerView.Adapter<IngredientsRecViewAdapter.ViewHolder>{

    ArrayList<String> ingredientNames = new ArrayList<>();
    ArrayList<String> ingredientQuantities = new ArrayList<>();
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

        if(isEditable){
            holder.editable.setVisibility(View.VISIBLE);
            holder.notEditable.setVisibility(View.GONE);
        }
        else{
            holder.editable.setVisibility(View.GONE);
            holder.notEditable.setVisibility(View.VISIBLE);
        }

        holder.btnRemove.setOnClickListener(v -> {
            ingredientNames.remove(ingredientNames.get(position));
            ingredientQuantities.remove(ingredientQuantities.get(position));
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientNames.size();
    }

    public void setItems(ArrayList<String> ingredientNames, ArrayList<String> ingredientQuantities){
        this.ingredientNames = ingredientNames;
        this.ingredientQuantities = ingredientQuantities;
    }

    public ArrayList<String> getIngredientNames(){
        return ingredientNames;
    }

    public ArrayList<String> getIngredientQuantities(){
        return ingredientQuantities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, quantity;
        private EditText edtName, edtQuantity;
        private LinearLayout editable, notEditable;
        private Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtIngredientName);
            quantity = itemView.findViewById(R.id.txtIngredientQuantity);

            edtName = itemView.findViewById(R.id.inputIngredientName);
            edtQuantity = itemView.findViewById(R.id.inputIngredientQuantity);

            editable = itemView.findViewById(R.id.ingredientEditable);
            notEditable = itemView.findViewById(R.id.ingredientNonEditable);

            btnRemove = itemView.findViewById(R.id.btnRemoveIngredient);
        }
    }
}
