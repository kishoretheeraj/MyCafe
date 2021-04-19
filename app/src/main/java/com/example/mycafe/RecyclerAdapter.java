package com.example.mycafe;

import android.content.Context;
import android.graphics.Color;
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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{

    String data[], data2[];
    int data3[];
    int qty;
    Context context;

    public RecyclerAdapter(Context context, String[] data, String[] data2, int[] data3) {
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_design,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.ViewHolder holder, int position) {

        holder.foodName.setText(data[position]);
        holder.foodRate.setText(data2[position]);
        holder.foodImg.setImageResource(data3[position]);

        holder.AddButton.setEnabled(false);
        holder.AddButton.setBackgroundColor(Color.parseColor("#FFF27D"));
        holder.AddButton.setTextColor(Color.parseColor("#C5C5C5"));

        holder.QtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                if(count == 10){
                    String foodname = (String) holder.foodName.getText();
                    Toast.makeText(context, "Cannot order more than 10 " + foodname, Toast.LENGTH_SHORT).show();
                } else if (count == 0){
                    holder.AddButton.setEnabled(true);
                    holder.quantity.setText("1");
                    holder.AddButton.setBackgroundResource(R.drawable.button_border);
                    holder.AddButton.setTextColor(Color.parseColor("#000000"));
                } else{
                    count += 1;
                    holder.quantity.setText("" + count);
                }
            }
        });

        holder.QtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                if(count == 0){
                    holder.AddButton.setEnabled(false);
                    holder.AddButton.setBackgroundColor(Color.parseColor("#FFF27D"));
                    holder.AddButton.setTextColor(Color.parseColor("#C5C5C5"));
                } else{
                    count -= 1;
                    if (count == 0){
                        holder.AddButton.setEnabled(false);
                        holder.AddButton.setBackgroundColor(Color.parseColor("#FFF27D"));
                        holder.AddButton.setTextColor(Color.parseColor("#C5C5C5"));
                    }
                    holder.quantity.setText("" + count);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        EditText quantity;
        Button AddButton;
        TextView foodName, QtyPlus, QtyMinus, foodRate;
        public ViewHolder(@NonNull final View itemView)
        {

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
