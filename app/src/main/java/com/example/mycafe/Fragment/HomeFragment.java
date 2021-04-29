package com.example.mycafe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycafe.R;
import com.example.mycafe.Adapter.RecyclerAdapter;
import com.google.common.primitives.Ints;

import java.util.List;

public class HomeFragment extends Fragment {

    static RecyclerView recyclerView;
    static RecyclerAdapter adapter;

    static String[] snackNames = {"Samosa", "Dosa", "Idly-Vada", "VegCutlet", "Pasta", "EggPasta", "Noodles", "EggNoodles", "Sandwich", "ChocoSandwich"};
    String snackRate[] = {"Rs.15", "Rs.30", "Rs.20", "Rs.15", "Rs.35", "Rs.40", "Rs.30", "Rs.35", "Rs.25", "Rs.20"};
    int imgFood[] = {R.drawable.samosa, R.drawable.dosa, R.drawable.idlyvada, R.drawable.vegcutlet, R.drawable.vegpasta, R.drawable.eggpasta, R.drawable.noodles, R.drawable.noodles, R.drawable.sandwich, R.drawable.chocosandwich};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new RecyclerAdapter(view.getContext(), snackNames, snackRate, imgFood);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static void Changed(String fname) {
        int i;
        for (i=0; i<snackNames.length; i++) {
            if (snackNames[i].equalsIgnoreCase(fname))
                break;
        }
        View view2;
        view2 = recyclerView.getChildAt(i);
        view2.findViewById(R.id.button).setVisibility(View.VISIBLE);
    }
}
