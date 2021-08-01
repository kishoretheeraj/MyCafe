package com.example.mycafe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.Activity.items;
import com.example.mycafe.Fragment.FoodsFragment;
import com.example.mycafe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.mycafe.Activity.items.addItems;
import static com.example.mycafe.Activity.items.updateItems;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<String> data;
    ArrayList<String> data2;
    ArrayList<String> data3;
    Context context;

    public RecyclerAdapter(Context context, ArrayList<String> data, ArrayList<String> data2, ArrayList<String> data3) {
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_design, parent, false);
        @SuppressWarnings("UnnecessaryLocalVariable") ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.foodName.setText(data.get(position));
        holder.foodRate.setText(data2.get(position));
        Picasso.with(context).load(data3.get(position)).into(holder.foodImg);

        if (items.quant_sel_list.get(position) == 0) {
            holder.AddButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.AddButton.setVisibility(View.INVISIBLE);
            holder.quantity.setText("" + items.quant_sel_list.get(position));
        }

        holder.QtyPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                if (count == 10) {
                    String foodname = (String) holder.foodName.getText();
                    Toast.makeText(context, "Cannot order more than 10 " + foodname, Toast.LENGTH_SHORT).show();
                } else {
                    count += 1;
                    holder.quantity.setText("" + count);
                    items.quant_sel_list.set(position, count);
                    updateItems((String) holder.foodName.getText(), String.valueOf(count));
                }
            }
        });

        holder.QtyMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                count -= 1;
                if (count == 0) {
                    holder.AddButton.setVisibility(View.VISIBLE);
                }
                holder.quantity.setText("" + count);
                items.quant_sel_list.set(position, count);
                updateItems((String) holder.foodName.getText(), String.valueOf(count));
            }
        });

        holder.AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> lis2 = new ArrayList<>();
                holder.quantity.setText("1");
                items.quant_sel_list.set(position, 1);
                lis2.add(data.get(position));
                lis2.add(data2.get(position));
                lis2.add(String.valueOf(holder.quantity.getText()));

                addItems(lis2);

                holder.AddButton.setVisibility(View.INVISIBLE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        EditText quantity;
        Button AddButton;
        TextView foodName, QtyPlus, QtyMinus, foodRate;

        public ViewHolder(@NonNull final View itemView) {

            super(itemView);
            foodImg = itemView.findViewById(R.id.imageView);
            quantity = itemView.findViewById(R.id.editText);
            AddButton = itemView.findViewById(R.id.button);
            foodName = itemView.findViewById(R.id.textView1);
            QtyPlus = itemView.findViewById(R.id.textView3);
            QtyMinus = itemView.findViewById(R.id.textView4);
            foodRate = itemView.findViewById(R.id.textView2);

        }
    }
}
