package com.example.mycafe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.Activity.HomePage;
import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.CartListAdapter;
import com.example.mycafe.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartFragment extends Fragment{
    static RecyclerView recyclerView2;
    static CartListAdapter adapter2;

    static ArrayList<ArrayList<String>> itm = items.getList();
    public static int total = 0;

    static TextView tot, not;
    static FloatingActionButton fab;
    static CardView cardorder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tot = view.findViewById(R.id.tv_total);
        not = view.findViewById(R.id.NoitemText);
        fab = view.findViewById(R.id.fabadd);
        cardorder = view.findViewById(R.id.ordercard);
        cardorder.setVisibility(View.GONE);

        recyclerView2 = view.findViewById(R.id.recycler_cart);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter2 = new CartListAdapter(view.getContext(), itm);
        recyclerView2.setAdapter(adapter2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragmentManager.findFragmentByTag("2")).show(fragmentManager.findFragmentByTag("1"));
                fragmentTransaction.commit();
                HomePage.setActive();
            }
        });

        int len = itm.size();

        if (len != 0) {

            calculateTotal();
            cardorder.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            not.setVisibility(View.GONE);

        } else {
            tot.setText("0");
            cardorder.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            not.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public static void calculateTotal() {
        int i = 0;
        total = 0;
        int len = itm.size();
        if (len != 0) {
            while (i < len) {
                String str = itm.get(i).get(1).replace("Rs.", "");
                total = total + (Integer.parseInt(str) * Integer.parseInt(itm.get(i).get(2)));
                i++;
            }
        }

        if (total == 0) {
            cardorder.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            not.setVisibility(View.VISIBLE);
        }

        tot.setText("" + total);

    }
}
