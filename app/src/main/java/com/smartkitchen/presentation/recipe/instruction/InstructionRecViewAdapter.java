package com.smartkitchen.presentation.recipe.instruction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkitchen.R;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

//Adapter for displaying the instruction list
public class InstructionRecViewAdapter extends RecyclerView.Adapter<InstructionRecViewAdapter.ViewHolder> {

    ArrayList<String> instructions = new ArrayList<>();
    Context mContext;
    boolean isEditable;
    Recipe recipe;
    InstructionRecViewAdapter adapter = this;

    //Constructor
    public InstructionRecViewAdapter(Context mContext, boolean isEditable) {
        this.mContext = mContext;
        this.isEditable = isEditable;
    }

    //Creates the list view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Passes in the instruction card view as the item to be placed in the list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_list_item, parent, false);
        return new ViewHolder(view);
    }

    //Sets the information inside the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.numberLabel1.setText("" + (position + 1) + ".");
        holder.numberLabel2.setText("" + (position + 1) + ".");
        holder.instruction.setText(instructions.get(position));
        holder.edtInstruction.setText(instructions.get(position));

        //Determines if the editable or noneditable version should be displayed
        if (isEditable) {
            holder.editable.setVisibility(View.VISIBLE);
            holder.notEditable.setVisibility(View.GONE);
        } else {
            holder.editable.setVisibility(View.GONE);
            holder.notEditable.setVisibility(View.VISIBLE);
        }

        //Removes an instruction from the list
        holder.btnRemove.setOnClickListener(v -> {
            instructions.remove(instructions.get(position));
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });

        //Calls the popup to take user input
        holder.btnEdit.setOnClickListener(v -> EditInstructionPopUp.showDialog(mContext, recipe, position, false, adapter));
    }

    //Returns the size of the list
    @Override
    public int getItemCount() {
        return instructions.size();
    }

    //Pass in the input list
    public void setItems(Recipe recipe, ArrayList<String> instructions) {
        this.recipe = recipe;
        this.instructions = instructions;
    }

    //Get the instruction list
    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView instruction, numberLabel1, numberLabel2;
        private final TextView edtInstruction;
        private final LinearLayout editable, notEditable;
        private final Button btnRemove, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            instruction = itemView.findViewById(R.id.txtInstruction);
            numberLabel1 = itemView.findViewById(R.id.txtInstructionNumber);
            numberLabel2 = itemView.findViewById(R.id.txtInstructionNumber2);
            edtInstruction = itemView.findViewById(R.id.txtEditInstruction);

            editable = itemView.findViewById(R.id.instructionEditable);
            notEditable = itemView.findViewById(R.id.instructionNonEditable);

            btnRemove = itemView.findViewById(R.id.btnRemoveInstruction);
            btnEdit = itemView.findViewById(R.id.btnEditInstruction);
        }
    }
}

