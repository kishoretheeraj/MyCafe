package com.example.mycafe.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycafe.Activity.HomePage;
import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.CartListAdapter;
import com.example.mycafe.Auth.MainActivity;
import com.example.mycafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CartFragment extends Fragment {

    FirebaseFirestore fStore;
    static RecyclerView recyclerView2;
    static CartListAdapter adapter2;
    Button placeorder;

    static ArrayList<ArrayList<String>> itm = items.getList();
    public static int total = 0;

    static TextView tot, not;
    static FloatingActionButton fab;
    static CardView cardorder;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Dialog dialog;

    String userId;
    String id;
    String Name;
    String Mobile;
    String thisDate;
    String thisTime;
    int ordercount;
    ArrayList<String> orders = new ArrayList<>();

    ArrayList<Long> sortid = new ArrayList<Long>();
    Long sum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        placeorder = view.findViewById(R.id.PlaceOrder);
        tot = view.findViewById(R.id.tv_total);
        not = view.findViewById(R.id.NoitemText);
        fab = view.findViewById(R.id.fabadd);
        cardorder = view.findViewById(R.id.ordercard);
        cardorder.setVisibility(View.GONE);
        dialog = new Dialog(view.getContext());
        recyclerView2 = view.findViewById(R.id.recycler_cart);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter2 = new CartListAdapter(view.getContext(), itm);
        recyclerView2.setAdapter(adapter2);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = user.getUid();

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Name = task.getResult().get("Name").toString();
                            Mobile = task.getResult().get("Mobile").toString();

                            orders = (ArrayList<String>) task.getResult().get("orders");


                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
                            Date todayDate = new Date();
                            thisDate = currentDate.format(todayDate);
                            //Toast.makeText(view.getContext(), "" + thisDate, Toast.LENGTH_SHORT).show();

                            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                            thisTime = dateFormat.format(new Date());
                            //Toast.makeText(view.getContext(), "" + thisTime, Toast.LENGTH_SHORT).show();


                            //Arrays.asList(itm)
                            String item = itm.toString();
                            String items = item.substring(1, item.length() - 1);
                            String[] splits = items.replace("[", "").replace("]", "").split(",");

                            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splits));

                            Random random = new Random();
                            id = "#" + String.format("%04d", random.nextInt(10000));


                            fStore.collection("sort")
                                    .document("array").get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                sortid = (ArrayList<Long>) document.get("sortid");
                                                //Toast.makeText(view.getContext(), "" + sortid, Toast.LENGTH_SHORT).show();

                                                sum = sortid.get(sortid.size() - 1);
                                                //Toast.makeText(view.getContext(), "" + sum, Toast.LENGTH_SHORT).show();


                                                final Map<String, Object> usermap = new HashMap<>();
                                                usermap.put("foodorder", arrayList);
                                                usermap.put("totalcost", total);
                                                usermap.put("name", Name);
                                                usermap.put("mobile", Mobile);
                                                usermap.put("date", thisDate);
                                                usermap.put("status", "ordered");
                                                usermap.put("time", thisTime);
                                                usermap.put("orderid", id);
                                                usermap.put("sortid", sum);

                                                final DocumentReference documentReference = fStore.collection("orders").document();

                                                documentReference.set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        orders.add(documentReference.getId());

                                                        ordercount = orders.size();
                                                        int points = (ordercount * 2);

                                                        String count = String.valueOf(ordercount);
                                                        String orderpoints = String.valueOf(points);


                                                        ////    Toast.makeText(view.getContext(), "" + orders, Toast.LENGTH_SHORT).show();

                                                        fStore.collection("users")
                                                                .document(userId)
                                                                .update("orders", orders);

                                                        fStore.collection("users")
                                                                .document(userId)
                                                                .update("ordercount", count);


                                                        fStore.collection("users")
                                                                .document(userId)
                                                                .update("points", orderpoints);


                                                        // Toast.makeText(view.getContext(), "Order Placed", Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //// Toast.makeText(view.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                                sum = sum + 1;
                                                sortid.add(sum);
                                                fStore.collection("sort")
                                                        .document("array")
                                                        .update("sortid", sortid);

                                                //Toast.makeText(view.getContext(), "" + sortid, Toast.LENGTH_SHORT).show();
                                            } else {

                                            }


                                        }
                                    });


                            //int last = sortid.get(sortid.size() - 1);
                            //Toast.makeText(view.getContext(), "" + last, Toast.LENGTH_SHORT).show();

                            //  usermap.put("sortid", last);


                            ordersuccessdialog(id, thisDate, thisTime, total);

                            ////Toast.makeText(view.getContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                        } else {
                            //show order failed dialog box
                        }
                    }
                });


                // Toast.makeText(view.getContext(), "" + items + total, Toast.LENGTH_SHORT).show();
            }
        });

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
            fab.setVisibility(View.INVISIBLE);
            not.setVisibility(View.INVISIBLE);


        } else {
            tot.setText("0");
            cardorder.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            not.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void ordersuccessdialog(String id, String date, String time, int cost) {

        updateItemToSheet(); //To change Live Counter

        dialog.setContentView(R.layout.orderplaced_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose = dialog.findViewById(R.id.imageViewclose);
        Button btnokay = dialog.findViewById(R.id.okaybtn);
        TextView orderid = dialog.findViewById(R.id.orderid);
        TextView orderdate = dialog.findViewById(R.id.orderdate);
        TextView ordertime = dialog.findViewById(R.id.ordertime);
        TextView ordercost = dialog.findViewById(R.id.ordercost);

        String totalcost = "Rs. " + cost;

        orderid.setText(id);
        orderdate.setText(date);
        ordertime.setText(time);
        ordercost.setText(totalcost);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                com.example.mycafe.Activity.items.removeItems();
                FoodsFragment.itemChanged();

            }
        });
        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                com.example.mycafe.Activity.items.removeItems();
                FoodsFragment.itemChanged();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
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

    private void updateItemToSheet() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzmyWtzindXUgW1vGA0AS7f_PgSrJxOr51yvLrczdSh5T8c2KoxMIj0EpN_4W8FGRFm/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(AddItem.this,response,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getContext(), MainActivity.class);
                        //startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                String foodlist = items.getList().get(0).get(0);
                String quantlist = items.getList().get(0).get(2);

                for (int i = 1; i < items.getList().size(); i++) {
                    foodlist = foodlist + "/" + items.getList().get(i).get(0);
                    quantlist = quantlist + "/" + items.getList().get(i).get(2);
                }

                //here we pass params
                parmas.put("action", "updateItem");
                parmas.put("FOODNAMES", foodlist);
                parmas.put("QUANTITY", quantlist);

                return parmas;
            }
        };

        //int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        //RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        queue.add(stringRequest);
    }
}
