package com.smartkitchen.presentation.recipe;

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

public class InstructionRecViewAdapter extends RecyclerView.Adapter<InstructionRecViewAdapter.ViewHolder> {

    ArrayList<String> instructions = new ArrayList<>();
    Context mContext;
    boolean isEditable;
    Recipe recipe;
    InstructionRecViewAdapter adapter = this;

    public InstructionRecViewAdapter(Context mContext, boolean isEditable){
        this.mContext = mContext;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.numberLabel1.setText("" + (position+1) + ".");
        holder.numberLabel2.setText("" + (position+1) + ".");
        holder.instruction.setText(instructions.get(position));
        holder.edtInstruction.setText(instructions.get(position));

        if(isEditable){
            holder.editable.setVisibility(View.VISIBLE);
            holder.notEditable.setVisibility(View.GONE);
        }
        else{
            holder.editable.setVisibility(View.GONE);
            holder.notEditable.setVisibility(View.VISIBLE);
        }

        holder.btnRemove.setOnClickListener(v -> {
            instructions.remove(instructions.get(position));
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInstructionPopUp.showDialog(mContext, recipe, position, false, adapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public void setItems(Recipe recipe, ArrayList<String> instructions){
        this.recipe = recipe;
        this.instructions = instructions;
    }

    public ArrayList<String> getInstructions(){
        return instructions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView instruction, numberLabel1, numberLabel2;
        private TextView edtInstruction;
        private LinearLayout editable, notEditable;
        private Button btnRemove, btnEdit;

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

