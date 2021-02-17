package com.smartkitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryListRecViewAdapter extends RecyclerView.Adapter<GroceryListRecViewAdapter.ViewHolder> {

    //List to be displayed
    private ArrayList<Item> items = new ArrayList<>();
    private Context mContext;

    //Constructor
    public GroceryListRecViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    //Creates the list view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Passes in the list item card view as the item to be placed in the list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    //Sets the information inside of the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.quantityToBuy.setText(items.get(position).getQuantityToBuyString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Use to pass in an array list
    public void setItems(ArrayList<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    //Holds and assigns the card view information
    public class ViewHolder extends RecyclerView.ViewHolder{

        //Basic fields
        private CardView parent;
        private TextView name;
        private TextView quantityToBuy;

        //Finds and assigns the information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.txtName);
            quantityToBuy = itemView.findViewById(R.id.txtQuantity);
        }
    }
}
