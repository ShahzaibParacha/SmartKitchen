package com.smartkitchen.presentation;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

import static com.smartkitchen.presentation.EditInventoryItemActivity.POSITION_KEY;

public class ItemRecViewAdapter extends RecyclerView.Adapter<ItemRecViewAdapter.ViewHolder> {

    IListActions listActions = new ListActions();
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
        holder.price.setText("$" + items.get(position).getPricePerUnit() + "/" + items.get(position).getUnits());

        //Creates on click listener, removes the item from the list
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = items.get(position);
                listActions.removeFromInventory(item);
                items.remove(item);
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

        //Button to quickly add items to the grocery list
        holder.btnAddToGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls the prompt for user to enter quantity to buy (doesn't need to return to main because it is already on main)
                AlertMessage.showDialog(mContext, listActions.getInventoryItem(position), false);
            }
        });

        //On Click Listeners to expand/collapse the cardview
        holder.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).setInvIsExpanded(true);
                notifyItemChanged(position);
            }
        });

        holder.upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).setInvIsExpanded(false);
                notifyItemChanged(position);
            }
        });

        //Set up expanded/collapsed view
        if(items.get(position).invIsExpanded()){
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
        private TextView price;
        private ImageView upArrow, downArrow;
        private LinearLayout expandedLayout;
        private Button btnEdit, btnRemove, btnAddToGrocery;

        //Finds and assigns the information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.invItemCardView);
            name = itemView.findViewById(R.id.txtInvItemName);
            quantity = itemView.findViewById(R.id.txtInvQuantity);
            price = itemView.findViewById(R.id.txtInvPrice);

            expandedLayout = itemView.findViewById(R.id.itemViewExpanded);

            upArrow = itemView.findViewById(R.id.itemUpArrow);
            downArrow = itemView.findViewById(R.id.itemDownArrow);

            btnEdit = itemView.findViewById(R.id.btnEditInvItem);
            btnRemove = itemView.findViewById(R.id.btnRemoveInvItem);
            btnAddToGrocery = itemView.findViewById(R.id.btnAddInvItemToGrocery);
        }
    }
}
