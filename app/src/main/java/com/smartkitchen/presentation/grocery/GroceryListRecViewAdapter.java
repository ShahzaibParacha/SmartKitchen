package com.smartkitchen.presentation.grocery;

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
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;
import com.smartkitchen.presentation.ViewInformationActivity;

import java.util.ArrayList;

import static com.smartkitchen.presentation.inventory.EditInventoryItemActivity.POSITION_KEY;

//Adapter used for displaying and interacting with grocery list items
public class GroceryListRecViewAdapter extends RecyclerView.Adapter<GroceryListRecViewAdapter.ViewHolder> {

    //Access to relevant business layer methods
    IGroceryActions groceryActions = new GroceryActions();

    //List to be displayed
    private ArrayList<Item> items = new ArrayList<>();
    private final Context mContext;

    //Constructor
    public GroceryListRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //Creates the list view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Passes in the list item card view as the item to be placed in the list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
        return new ViewHolder(view);
    }

    //Sets the information inside of the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.quantityToBuy.setText(items.get(position).getQuantityToBuyString());
        holder.price.setText("$" + items.get(position).getPricePerUnit() + "/" + items.get(position).getUnits());

        //Clicking on the item card moves to the more info screen
        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ViewInformationActivity.class);
            //Passes the current item that should be displayed and where the call came from
            intent.putExtra("Position", position);
            intent.putExtra("Origin", "Grocery");

            //Move to next screen with transition
            ActivityOptionsCompat options = getTransition(holder);
            mContext.startActivity(intent, options.toBundle());
        });

        //Creates on click listener, removes the item from the list
        holder.btnRemove.setOnClickListener(v -> {
            Item item = items.get(position);
            groceryActions.removeFromGrocery(item);
            //Refreshes the page
            ((GroceryListActivity) mContext).onResume();
        });

        //Creates on click listener, moves to the edit screen, passes the item identifier
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditGroceryListItemActivity.class);
            intent.putExtra(POSITION_KEY, position);
            ActivityOptionsCompat options = getTransition(holder);
            mContext.startActivity(intent, options.toBundle());
        });

        //Buy button for quickly adding an item to the inventory
        holder.btnBuyItem.setOnClickListener(v -> {
            try {
                groceryActions.buyItem(groceryActions.getGroceryItem(position));
                ((GroceryListActivity) mContext).onResume();
            } catch (InvalidInputException e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    //Returns size of the list
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
        androidx.core.util.Pair<View, String> p2 = androidx.core.util.Pair.create(holder.quantityToBuy, "itemQuantityToBuy");
        androidx.core.util.Pair<View, String> p3 = androidx.core.util.Pair.create(holder.price, "itemPrice");
        return ActivityOptionsCompat.makeSceneTransitionAnimation((GroceryListActivity) mContext, p1, p2, p3);
    }

    //Holds and assigns the card view information
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Basic fields
        private final CardView parent;
        private final TextView name;
        private final TextView quantityToBuy;
        private final TextView price;
        private final ImageView downArrow, upArrow;
        private final LinearLayout expandedLayout;
        private final Button btnEdit, btnRemove, btnBuyItem;

        //Finds and assigns the information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.groceryCardView);
            name = itemView.findViewById(R.id.txtGroceryItemName);
            quantityToBuy = itemView.findViewById(R.id.txtQuantityToBuy);
            price = itemView.findViewById(R.id.txtGroceryPrice);

            expandedLayout = itemView.findViewById(R.id.groceryViewExpanded);

            upArrow = itemView.findViewById(R.id.groceryUpArrow);
            downArrow = itemView.findViewById(R.id.groceryDownArrow);

            btnEdit = itemView.findViewById(R.id.btnEditGroceryItem);
            btnRemove = itemView.findViewById(R.id.btnRemoveGroceryItem);
            btnBuyItem = itemView.findViewById(R.id.btnBuyItem);
        }
    }
}
