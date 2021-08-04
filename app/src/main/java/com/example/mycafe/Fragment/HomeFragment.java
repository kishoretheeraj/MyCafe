package com.example.mycafe.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.Activity.foods;
import com.example.mycafe.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    RecyclerView live_counter_recycler_view;
    FirebaseRecyclerOptions<foods> options;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("1X4TFdIJLPIzE3gIZvkBRq_SvWviDrU6JoomJaXeXac0/FoodList");
    FirebaseRecyclerAdapter<foods, LiveCounterAdapter> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        live_counter_recycler_view = view.findViewById(R.id.liveCounter_RecyclerView);
        LinearLayoutManager myLayoutManager;
        myLayoutManager = new LinearLayoutManager(getContext());
        myLayoutManager.setReverseLayout(true);
        live_counter_recycler_view.setLayoutManager(myLayoutManager);

        ReadData();

        return view;
    }

    private void ReadData() {
        options = new FirebaseRecyclerOptions.Builder<foods>().setQuery(ref.orderByChild("QTY_AVAILABLE"), foods.class).build();
        adapter = new FirebaseRecyclerAdapter<foods, LiveCounterAdapter>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull LiveCounterAdapter holder, int position, @NonNull foods model) {

                    holder.foodName.setText(model.getNAME());
                    holder.foodAvailability.setText("REMAINING : " + model.getQTY_AVAILABLE());

            }

            @NonNull
            @Override
            public LiveCounterAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_counter_single_item_layout, parent, false);
                return new LiveCounterAdapter(view2);
            }
        };
        adapter.startListening();
        live_counter_recycler_view.setAdapter(adapter);
    }
}