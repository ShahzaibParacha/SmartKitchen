package com.smartkitchen.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public class RecipeRecViewAdapter extends RecyclerView.Adapter<RecipeRecViewAdapter.ViewHolder> {


    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context mContext;

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

        //On Click Listeners to expand/collapse the cardview
        holder.downArrow.setOnClickListener(v -> {
            recipes.get(position).setExpanded(true);
            notifyItemChanged(position);
        });

        holder.upArrow.setOnClickListener(v -> {
            recipes.get(position).setExpanded(false);
            notifyItemChanged(position);
        });

        //Set up expanded/collapsed view
        if(recipes.get(position).isExpanded()){
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            holder.upArrow.setVisibility(View.VISIBLE);
        }
        else{
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
            holder.upArrow.setVisibility(View.GONE);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView name;
        private TextView isPossible;
        private ImageView upArrow, downArrow;
        private LinearLayout expandedLayout;
        private Button btnEdit, btnRemove, btnMake;

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
        }
    }
}
