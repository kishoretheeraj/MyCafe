package com.example.mycafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mycafe.Fragment.CartFragment;
import com.example.mycafe.Fragment.HomeFragment;
import com.example.mycafe.Fragment.FoodsFragment;
import com.example.mycafe.Fragment.ProfileFragment;
import com.example.mycafe.Fragment.SearchFragment;
import com.example.mycafe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    static final Fragment fragment1 = new FoodsFragment();
    final Fragment fragment2 = new CartFragment();
    final Fragment fragment3 = new ProfileFragment();
    final Fragment fragment4 = new SearchFragment();
    final Fragment fragment5 = new HomeFragment();
    final FragmentManager fm = getSupportFragmentManager();
    static Fragment active = fragment1;

    public static void setActive() {
        active = fragment1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.foods:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;
                //selectedFragment = HomeFragment.getInstance();
                //break;

                case R.id.orders:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    return true;
                //selectedFragment = new SearchFragment();
                //break;
                case R.id.home:
                    fm.beginTransaction().hide(active).show(fragment5).commit();
                    active = fragment5;
                    return true;

                case R.id.cart:
                    FragmentTransaction fragmentTransaction = fm.beginTransaction().hide(active).show(fragment2);
                    fragmentTransaction.detach(fragment2);
                    fragmentTransaction.attach(fragment2);
                    fragmentTransaction.commit();
                    active = fragment2;
                    return true;
                //selectedFragment = new CartFragment();
                //break;

                case R.id.profile:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;
                //selectedFragment = new ProfileFragment();
                //break;

            }

            return false;

            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            //return true;
        }
    };


    public void onBackPressed() {
        //finishAffinity();
        //finish();
    }

}