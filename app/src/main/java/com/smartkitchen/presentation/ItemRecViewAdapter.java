package com.smartkitchen.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.ItemLists;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

import static com.smartkitchen.presentation.EditInventoryItemActivity.POSITION_KEY;

public class ItemRecViewAdapter extends RecyclerView.Adapter<ItemRecViewAdapter.ViewHolder> {

    //List to be displayed
    private ArrayList<Item> items = new ArrayList<>();
    private Context mContext;

    //Constructor
    public ItemRecViewAdapter(Context mContext){
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
        holder.quantity.setText(items.get(position).getQuantityString());

        //Creates on click listener, removes the item from the list
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = DBManager.getInventoryDB().getInventoryList().get(position);
                DBManager.getInventoryDB().removeFromInventory(item);
                notifyItemRemoved(position);
            }
        });

        //Creates on click listener, moves to edit item screen, passes the item identifier
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditInventoryItemActivity.class);
                intent.putExtra(POSITION_KEY, position);
                mContext.startActivity(intent);
            }
        });
    }

    //Returns the size of the list
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
        private TextView quantity;
        private Button btnEdit, btnRemove;

        //Finds and assigns the information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.invItemCardView);
            name = itemView.findViewById(R.id.txtInvItemName);
            quantity = itemView.findViewById(R.id.txtInvQuantity);
            btnEdit = itemView.findViewById(R.id.btnEditInvItem);
            btnRemove = itemView.findViewById(R.id.btnRemoveInvItem);
        }


    }
}
