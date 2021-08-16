package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Responsible for displaying data from the model into a row in the recycler view.
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClicked(int position);
    }
    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener =  clickListener;
    }

    @NonNull
    @Override // creating each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator to inflate a view
        // wrap it inside a view holder and return it

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        // wrap inside view holder
        return new ViewHolder(todoView);
    }

    @Override // taking data from a position and putting it into view holder; binds the data to a particular view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // Grab the item at the position
    String item = items.get(position);
    // Bind the item into a specified view holder
    holder.bind(item);

    }

    @Override // tells recycler view how many items are in the list
    public int getItemCount() {
        return items.size();
    }

    // Container to provide esy access to views that represent each row of the list.
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);

        }
    // meant to update the view inside the view holder with this data
        public void bind(String item) {
        tvItem.setText(item);
        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        });
        tvItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // notify the listener which position was long pressed.
                longClickListener.onItemLongClicked(getAdapterPosition());
                return true;
            }
        });

        }
    }
}
