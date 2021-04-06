package com.smartkitchen.presentation.inventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.presentation.AlertMessage;
import com.smartkitchen.presentation.ViewInformationActivity;

import java.util.ArrayList;

import static com.smartkitchen.presentation.inventory.EditInventoryItemActivity.POSITION_KEY;

//Adapter for displaying the inventory items
public class ItemRecViewAdapter extends RecyclerView.Adapter<ItemRecViewAdapter.ViewHolder> {

    //Access to relevant business layer methods
    IInventoryActions inventoryActions = new InventoryActions();
    //List to be displayed
    private ArrayList<Item> items = new ArrayList<>();
    private final Context mContext;

    //Constructor
    public ItemRecViewAdapter(Context mContext) {
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

        //When the item card is clicked, transition to more info screen
        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ViewInformationActivity.class);
            //Passes the current item that should be displayed and where the call came from
            intent.putExtra("Position", position);
            intent.putExtra("Origin", "Inventory");

            //Move to next screen with transition
            ActivityOptionsCompat options = getTransition(holder);
            mContext.startActivity(intent, options.toBundle());
        });

        //Creates on click listener, removes the item from the list
        holder.btnRemove.setOnClickListener(v -> {
            Item item = items.get(position);
            inventoryActions.removeFromInventory(item);
            items.remove(item);
            notifyItemRemoved(position);
        });

        //Creates on click listener, moves to edit item screen, passes the item identifier
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditInventoryItemActivity.class);
            intent.putExtra(POSITION_KEY, position);
            ActivityOptionsCompat options = getTransition(holder);
            mContext.startActivity(intent, options.toBundle());
        });

        //Button to quickly add items to the grocery list
        holder.btnAddToGrocery.setOnClickListener(v -> {
            //Calls the prompt for user to enter quantity to buy (doesn't need to return to main because it is already on main)
            AlertMessage.showDialog(mContext, inventoryActions.getInventoryItem(position), false);
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
    }

    //Returns the size of the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Use to pass in an array list
    public void setItems(ArrayList<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    //Prepares the transition to next screen
    private ActivityOptionsCompat getTransition(ViewHolder holder) {
        androidx.core.util.Pair<View, String> p1 = androidx.core.util.Pair.create(holder.name, "itemName");
        androidx.core.util.Pair<View, String> p2 = androidx.core.util.Pair.create(holder.quantity, "itemQuantity");
        androidx.core.util.Pair<View, String> p3 = androidx.core.util.Pair.create(holder.price, "itemPrice");
        return ActivityOptionsCompat.makeSceneTransitionAnimation((CurrentInventoryActivity) mContext, p1, p2, p3);
    }

    //Holds and assigns the card view information
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Basic fields
        private final CardView parent;
        private final TextView name, quantity, price;
        private final ImageView upArrow, downArrow;
        private final LinearLayout expandedLayout;
        private final Button btnEdit, btnRemove, btnAddToGrocery;

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
