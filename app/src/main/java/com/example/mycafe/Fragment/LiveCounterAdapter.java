package com.example.mycafe.Fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.R;

public class LiveCounterAdapter extends RecyclerView.ViewHolder {

    //To store the foodname and its availability in live_counter_single_item_layout
    TextView foodName, foodAvailability;

    public LiveCounterAdapter(@NonNull View itemView) {
        super(itemView);

        foodName = itemView.findViewById(R.id.foodName_TextView_LiveCounter);
        foodAvailability = itemView.findViewById(R.id.quantRem_TextView_LiveCounter);

    }

}
