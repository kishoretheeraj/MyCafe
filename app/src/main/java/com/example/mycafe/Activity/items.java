package com.example.mycafe.Activity;

import java.util.ArrayList;

public class items {

    public static ArrayList<String> food_list = new ArrayList<>();
    public static ArrayList<String> food_rate = new ArrayList<>();
    public static ArrayList<Integer> quant_avail = new ArrayList<>();
    public static ArrayList<String> food_image_uri = new ArrayList<>();
    public static ArrayList<Integer> quant_sel_list = new ArrayList<>();

    private static final ArrayList<ArrayList<String>> list = new ArrayList<>();

    public static void addItems(ArrayList<String> lis) {
        list.add(lis);
    }

    public static void updateItems(String itemName, String itemQuant) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(0) == itemName) {
                if (Integer.parseInt(itemQuant) == 0) {
                    list.remove(i);
                    break;
                } else {
                    list.get(i).set(2, itemQuant);
                    break;
                }

            }
        }
    }

    public static ArrayList<ArrayList<String>> getList() {
        return list;
    }

    public static void removeItems() {
        list.clear();
        for (int j = 0; j < quant_sel_list.size(); j++)
            quant_sel_list.set(j, 0);
        HomePage.refreshCartFragment();
    }

}

