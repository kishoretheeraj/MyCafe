package com.example.mycafe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.mycafe.Activity.HomePage;
import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.CartListAdapter;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

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
    String userId, Name, Mobile, thisDate, thisTime;
    ArrayList<String> orders = new ArrayList<>();

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

                            orders = (ArrayList<String>) task.getResult().get("order Id");


                            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                            Date todayDate = new Date();
                            thisDate = currentDate.format(todayDate);
                            Toast.makeText(view.getContext(), "" + thisDate, Toast.LENGTH_SHORT).show();

                            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                            thisTime = dateFormat.format(new Date());
                            Toast.makeText(view.getContext(), "" + thisTime, Toast.LENGTH_SHORT).show();


                            //Arrays.asList(itm)
                            String item = itm.toString();
                            String items = item.substring(1, item.length() - 1);
                            String[] splits = items.replace("[", "").replace("]", "").split(",");

                            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splits));

                            final Map<String, Object> usermap = new HashMap<>();
                            usermap.put("foodorder", arrayList);
                            usermap.put("totalcost", total);
                            usermap.put("name", Name);
                            usermap.put("mobile", Mobile);
                            usermap.put("date", thisDate);
                            usermap.put("time", thisTime);


                            final DocumentReference documentReference = fStore.collection("orders").document();

                            documentReference.set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    orders.add(documentReference.getId());


                                    Toast.makeText(view.getContext(), "" + orders, Toast.LENGTH_SHORT).show();

                                    fStore.collection("users")
                                            .document(userId)
                                            .update("order Id", orders);

                                    // Toast.makeText(view.getContext(), "Order Placed", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            Toast.makeText(view.getContext(), "Order Placed", Toast.LENGTH_SHORT).show();
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
