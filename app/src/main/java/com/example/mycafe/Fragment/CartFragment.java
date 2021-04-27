package com.example.mycafe.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.CartListAdapter;
import com.example.mycafe.R;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView2;
    CartListAdapter adapter2;

    static ArrayList<ArrayList<String>> itm = items.getList();
    public static int total = 0;

    static TextView tot;
    static Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tot = view.findViewById(R.id.tv_total);
        btn = view.findViewById(R.id.PlaceOrder);
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#A5B4F4"));

        recyclerView2 = view.findViewById(R.id.recycler_cart);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter2 = new CartListAdapter(view.getContext(), itm);
        recyclerView2.setAdapter(adapter2);

        int len = itm.size();

        if (len != 0) {

            calculateTotal();
            Toast.makeText(getContext(), "" + total, Toast.LENGTH_SHORT).show();

        } else {
            tot.setText("0");
            btn.setEnabled(false);
            btn.setBackgroundColor(Color.parseColor("#A5B4F4"));
        }

        return view;
    }

    static void calculateTotal() {
        int i = 0;
        total = 0;
        int len = itm.size();
        if (len != 0) {
            while (i < len) {
                String str = itm.get(i).get(1).replace("Rs.", "");
                total = total + (Integer.parseInt(str) * Integer.parseInt(itm.get(i).get(2)));
                i++;
            }

            btn.setEnabled(true);
            btn.setBackgroundColor(Color.parseColor("#2149FF"));

        }

        tot.setText("" + total);

    }
}
