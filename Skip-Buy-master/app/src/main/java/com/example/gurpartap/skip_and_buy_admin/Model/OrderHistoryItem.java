package com.example.gurpartap.skip_and_buy_admin.Model;

/**
 * Created by GURPARTAP on 2016-09-16.
 */

/*
    *   OrderHistoryItem maps to the database orderhistory item
    *   It has information about the store and amount that user spent at that store
*/

public class OrderHistoryItem {


    public int icon;
    public String name;
    public String amount;
    public String weight;
    public String productId;

    public OrderHistoryItem(){
        super();
    }

    public OrderHistoryItem(int icon, String name, String amount, String weight, String productId){
        super();
        this.icon=icon;
        this.name=name;
        this.amount=amount;
        this.weight=weight;
        this.productId=productId;
    }

}
