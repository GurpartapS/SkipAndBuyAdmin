package com.example.gurpartap.skip_and_buy_admin.Controller;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.gurpartap.skip_and_buy_admin.Model.OrderHistoryItem;
import com.example.gurpartap.skip_and_buy_admin.Model.SqlConnection;
import com.example.gurpartap.skip_and_buy_admin.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/*
    *   OrderHistoryFragment has the information about the User purchase OrderHistory
    *   OrderHistoryFragment has the ListView of the OrderHistory Items
    *   OrderHistoryFragment also handles operations regarding list items including removing items from listview
*/

public class OrderHistoryFragment extends ListFragment implements AdapterView.OnItemClickListener {

    Button checkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.order_history, container, false);
        updateOrderHistory();

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(),"Item: "+position,Toast.LENGTH_SHORT).show();

    }

    public void updateOrderHistory(){

        System.out.println("IN THE ORDER HISTORY YES YES");
        String storeId="";
        String userId="";

        try {
            SqlConnection connn = new SqlConnection();


            Connection connect1 = connn.connect();



            List<OrderHistoryItem> myList = new ArrayList<OrderHistoryItem>();


                    if (connect1 != null) {

                        PreparedStatement getCartProductInfo = connect1.prepareStatement("Select * from orderHistory where receiptId=?");
                        getCartProductInfo.setString(1,ConfirmationFragment.receiptId);
                        System.out.println("RECEIPT ID IS:"+ConfirmationFragment.receiptId);
                        ResultSet verifyCartProductResultSet= getCartProductInfo.executeQuery();
                        if(verifyCartProductResultSet.next()){
                            int productImage=R.drawable.walmarts;

                                myList.add(new OrderHistoryItem(productImage,"Walmart",verifyCartProductResultSet.getString("orderAmount"),verifyCartProductResultSet.getString("orderDateTime").substring(0, 10),ConfirmationFragment.receiptId));

                        }
                    }
                    else {

                    }

                OrderHistoryItem[] shoppingCartItem = myList.toArray(new OrderHistoryItem[myList.size()]);
                for (OrderHistoryItem myObject : shoppingCartItem) {
                    System.out.println(myObject);
                }
                if(shoppingCartItem.length!=0) {
                    OrderHistoryAdapter adapter = new OrderHistoryAdapter(this.getContext(), R.layout.activity_order_history_item, shoppingCartItem);
                    setListAdapter(adapter);
                }
                else{
                    ((MainActivity)getActivity()).emptyOrderHistory();
                }

        }
        catch(Exception e){
            System.out.println("EXCEPTION OCCURED IN SHOPPING CART FRAGMENT -: "+e.getMessage());
        }


    }

}
