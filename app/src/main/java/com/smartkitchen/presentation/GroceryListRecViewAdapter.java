package com.smartkitchen.presentation;

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
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

import static com.smartkitchen.presentation.EditInventoryItemActivity.POSITION_KEY;

public class GroceryListRecViewAdapter extends RecyclerView.Adapter<GroceryListRecViewAdapter.ViewHolder> {

    IListActions listActions = new ListActions();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
        return new ViewHolder(view);
    }

    //Sets the information inside of the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.quantityToBuy.setText(items.get(position).getQuantityToBuyString());

        //Creates on click listener, removes the item from the list
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = listActions.getGroceryItem(position);
                listActions.removeFromGrocery(item);
                notifyItemRemoved(position);
            }
        });

        //Creates on click listener, moves to the edit screen, passes the item identifier
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditGroceryListItemActivity.class);
                intent.putExtra(POSITION_KEY, position);
                mContext.startActivity(intent);
            }
        });

        //Buy button for quickly adding an item to the inventory
        holder.btnBuyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listActions.buyItem(listActions.getGroceryItem(position));
                notifyItemRemoved(position);
            }
        });

        //On Click Listeners to expand/collapse the cardview
        holder.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).setGroceryIsExpanded(true);
                notifyItemChanged(position);
            }
        });

        holder.upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).setGroceryIsExpanded(false);
                notifyItemChanged(position);
            }
        });

        //Set up expanded/collapsed view
        if(items.get(position).groceryIsExpanded()){
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

    //Returns size of the list
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
        private ImageView downArrow, upArrow;
        private LinearLayout expandedLayout;
        private Button btnEdit, btnRemove, btnBuyItem;

        //Finds and assigns the information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.groceryCardView);
            name = itemView.findViewById(R.id.txtGroceryItemName);
            quantityToBuy = itemView.findViewById(R.id.txtQuantityToBuy);

            expandedLayout = itemView.findViewById(R.id.groceryViewExpanded);

            upArrow = itemView.findViewById(R.id.groceryUpArrow);
            downArrow = itemView.findViewById(R.id.groceryDownArrow);

            btnEdit = itemView.findViewById(R.id.btnEditGroceryItem);
            btnRemove = itemView.findViewById(R.id.btnRemoveGroceryItem);
            btnBuyItem = itemView.findViewById(R.id.btnBuyItem);
        }
    }
}
