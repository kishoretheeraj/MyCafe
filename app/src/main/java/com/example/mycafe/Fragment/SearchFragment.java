package com.example.mycafe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.Activity.orders;
import com.example.mycafe.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView3;
    String mobile;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser user = fAuth.getCurrentUser();
    String userId = user.getUid();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    CollectionReference ref = fStore.collection("orders");
    FirestoreRecyclerOptions<orders> options;
    FirestoreRecyclerAdapter<orders, OrdersAdapter> adapter;


    DocumentReference ref2 = fStore.collection("users").document(userId);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_search,container,false);

        recyclerView3 = view.findViewById(R.id.recycleView);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mobile = document.getString("Mobile");
                        ReadData();
                    } else {
                        Toast.makeText(getContext(), "No Orders found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data, Sorry! Please try after some time.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void ReadData() {
        options = new FirestoreRecyclerOptions.Builder<orders>().setQuery(ref.whereEqualTo("mobile", mobile).orderBy("sortid", Query.Direction.DESCENDING), orders.class).build();
        adapter = new FirestoreRecyclerAdapter<orders, OrdersAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull OrdersAdapter holder, int position, @NonNull @NotNull orders model) {
                List<String> list1 = model.getFoodorder();
                String foodnames = "FOOD NAME";
                String foodqty = "QTY";
                String sno = "S NO";
                int i = 1;
                for (String x:list1) {
                    int temp = list1.indexOf(x);
                    if (temp%3 == 0) {
                        sno += "\n" + i + ".";
                        foodnames += "\n" + x;
                    }
                    else if ((temp+1)%3 == 0) {
                        foodqty += "\n" + x;
                        i += 1;
                    }
                }

                holder.orderID.setText(model.getOrderid());
                holder.orderDate.setText(model.getDate());
                holder.orderTime.setText(model.getTime());
                holder.totalCost.setText("Total Cost : Rs." + model.getTotalcost());
                holder.orderFoodName.setText(foodnames);
                holder.orderFoodQty.setText(foodqty);
                holder.sno.setText(sno);

            }

            @NonNull
            @NotNull
            @Override
            public OrdersAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item_layout, parent, false);
                return new OrdersAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView3.setAdapter(adapter);
    }
}
