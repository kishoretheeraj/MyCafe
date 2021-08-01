package com.example.mycafe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.RecyclerAdapter;
import com.example.mycafe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    ListView liveCounter_listview;
    ListAdapter listAdapter;
    ArrayList<HashMap<String, String>> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        liveCounter_listview = view.findViewById(R.id.liveCounter_List_View);
        getItems();

        return view;
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
                int qty_avl = jo.getInt("quantity");

                HashMap<String, String> item = new HashMap<>();
                item.put("fname", name);
                item.put("avail", "Availability : " + qty_avl);
                list.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listAdapter = new SimpleAdapter(getContext(), list, R.layout.live_counter_single_item_layout,
                new String[]{"fname", "avail"}, new int[]{R.id.foodName_TextView_LiveCounter, R.id.quantRem_TextView_LiveCounter});

        liveCounter_listview.setAdapter(listAdapter);
    }

}
