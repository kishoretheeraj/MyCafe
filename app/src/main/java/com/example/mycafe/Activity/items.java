package com.example.mycafe.Activity;

import java.util.ArrayList;

public class items{
    private static final ArrayList<ArrayList<String>> list = new ArrayList<>();

    public static void addItems(ArrayList<String> lis) {
        //kishore theeraj v j
        list.add(lis);
    }

    public static void updateItems(String itemName, String itemQuant){
        for (int i=0; i < list.size(); i++){
            if (list.get(i).get(0) == itemName){
                if (Integer.parseInt(itemQuant) == 0){
                    list.remove(i);
                    break;
                }
                else {
                    list.get(i).set(2, itemQuant);
                    break;
                }

            }
        }
    }

    public static ArrayList<ArrayList<String>> getList() {
        return list;
    }

}

