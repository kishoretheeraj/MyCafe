package com.example.mycafe.Activity;

import java.util.List;

public class orders {
    String orderid;
    String name;
    String mobile;
    String date;
    String time;
    int totalcost;
    List<String> foodorder;

    public orders() {
    }

    public orders(String orderid, String name, String mobile, String date, String time, int totalcost, List<String> foodorder) {
        this.orderid = orderid;
        this.name = name;
        this.mobile = mobile;
        this.date = date;
        this.time = time;
        this.totalcost = totalcost;
        this.foodorder = foodorder;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public List<String> getFoodorder() {
        return foodorder;
    }

    public void setFoodorder(List<String> foodorder) {
        this.foodorder = foodorder;
    }
}
