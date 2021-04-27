package com.example.mycafe.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder>
{

    ArrayList<ArrayList<String>> dataitems;
    Context context;

    public CartListAdapter(Context context, ArrayList<ArrayList<String>> data) {
        this.dataitems = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_item_layout,parent,false);
        @SuppressWarnings("UnnecessaryLocalVariable") ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartListAdapter.ViewHolder holder, final int position) {

        holder.itemname.setText(dataitems.get(position).get(0));
        holder.itemrate.setText(dataitems.get(position).get(1));
        holder.textQty.setText(dataitems.get(position).get(2));

        int amt = 0;
        String str;
        str = String.valueOf(holder.itemrate.getText()).replace("Rs.", "");
        amt = Integer.parseInt(String.valueOf(holder.textQty.getText())) * Integer.parseInt(str);
        holder.itemamt.setText("Rs." + amt);

    }

    @Override
    public int getItemCount() {
        return dataitems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemrate,itemname, itemamt;
        private final EditText textQty;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemName);
            itemrate = itemView.findViewById(R.id.itemRate);
            textQty = itemView.findViewById(R.id.textqty);
            itemamt = itemView.findViewById(R.id.itemamt);

        }
    }

}