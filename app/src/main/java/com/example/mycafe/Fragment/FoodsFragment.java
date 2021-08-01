package com.example.mycafe.Fragment;

import android.annotation.SuppressLint;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycafe.Activity.items;
import com.example.mycafe.R;
import com.example.mycafe.Adapter.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodsFragment extends Fragment {

    static RecyclerView recyclerView;
    static RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        getItems();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void itemChanged() {
        adapter.notifyDataSetChanged();
    }

    public static void Changed(String fname) {
        int i;
        for (i=0; i<items.food_list.size(); i++) {
            if (items.food_list.get(i).equalsIgnoreCase(fname)) {
                items.quant_sel_list.set(i, 0);
                break;
            }
        }
        //View view2;
        //view2 = recyclerView.getChildAt(i);
        //view2.findViewById(R.id.button).setVisibility(View.VISIBLE);
    }

    private void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxrX-WVaERsxd3wijW7EJlU4F4TXGadTFEgZRoWDGqE3yC9AG8YzaMgOxgKjwOtGsV5/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error 404 Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("foodlist");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String name = jo.getString("food");
                String rate = jo.getString("rate");
                int qty_avl = jo.getInt("quantity");
                String img = jo.getString("image");

                if (qty_avl > 0) {
                    items.food_list.add(name);
                    items.food_rate.add(rate);
                    items.quant_avail.add(qty_avl);
                    items.food_image_uri.add(img);
                    items.quant_sel_list.add(0);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new RecyclerAdapter(getContext(), items.food_list, items.food_rate, items.food_image_uri);
        recyclerView.setAdapter(adapter);
    }
}
