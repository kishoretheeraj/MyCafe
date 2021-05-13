package com.example.mycafe.Fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.R;

public class OrdersAdapter extends RecyclerView.ViewHolder {
    TextView orderID, orderDate, orderTime, totalCost, orderFoodName, orderFoodQty, sno;

    public OrdersAdapter(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);

        orderID = itemView.findViewById(R.id.order_ID);
        orderDate = itemView.findViewById(R.id.order_Date);
        orderTime = itemView.findViewById(R.id.order_Time);
        totalCost = itemView.findViewById(R.id.total_Cost);
        orderFoodName = itemView.findViewById(R.id.order_food_name);
        orderFoodQty = itemView.findViewById(R.id.order_food_qty);
        sno = itemView.findViewById(R.id.serialno);
    }
}
