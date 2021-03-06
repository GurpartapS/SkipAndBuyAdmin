package com.example.gurpartap.skip_and_buy_admin.Controller;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gurpartap.skip_and_buy_admin.Model.ShoppingCartItem;
import com.example.gurpartap.skip_and_buy_admin.Model.SqlConnection;
import com.example.gurpartap.skip_and_buy_admin.Model.UserAccount;
import com.example.gurpartap.skip_and_buy_admin.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    * This is adapter for shopping cart fragment
    * and handles all onclick function in shopping cart
*/

public class ShoppingCartFragment extends ListFragment implements AdapterView.OnItemClickListener {

    Button checkout;
    public static String receiptId;
    public static String customerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        updateShoppingCart();

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(),"Item: "+position,Toast.LENGTH_SHORT).show();

    }

    public void updateShoppingCart(){
        String storeId="";
        String userId="";

        try {
            SqlConnection connn = new SqlConnection();

            Connection connect = connn.connect();
            Connection connect1 = connn.connect();
            Connection connect2 = connn.connect();

            if (connect != null) {

                PreparedStatement getStoreInfo = connect.prepareStatement("Select storeId from Store where storeAddress=?");

                getStoreInfo.setString(1,MainActivity.location);

                ResultSet verifyStoreResultset = getStoreInfo.executeQuery();

                if(verifyStoreResultset.next()){
                    storeId=verifyStoreResultset.getString("storeId");
                }
            }
            else {

            }

            if (connect2 != null) {

                PreparedStatement getStoreInfo = connect.prepareStatement("Select customerId from customer where customerEmail=?");

                getStoreInfo.setString(1, UserAccount.email);

                ResultSet verifyStoreResultset = getStoreInfo.executeQuery();

                if(verifyStoreResultset.next()){
                    userId=verifyStoreResultset.getString("customerId");
                }
            }
            else {

            }

            if (connect1 != null) {

                String scanningResult=MainActivity.scannedProductCode;

                String[] split = scanningResult.split(",");
                customerId = split[0];
                receiptId = split[1];

                PreparedStatement getProductInfo = connect1.prepareStatement("Select * from receiptProducts where receiptId=? and isChecked=''");

                EditText productQuantity=(EditText)getActivity().findViewById(R.id.productQuantity);


                getProductInfo.setString(1,receiptId);

                ResultSet verifyStoreResultset = getProductInfo.executeQuery();
               /* ShoppingCartItem shoppingCartItem[]=new ShoppingCartItem[]{
                        new ShoppingCartItem(R.drawable.ketchup,"Ketchup    "),
                        new ShoppingCartItem(R.drawable.ritz,"Ritz Snack"),
                        new ShoppingCartItem(R.drawable.excelchew,"Excel Gum")
                };*/

                List<ShoppingCartItem> myList = new ArrayList<ShoppingCartItem>();


                while (verifyStoreResultset.next()){
                    if (connect1 != null) {

                        PreparedStatement getCartProductInfo = connect1.prepareStatement("Select * from products where Product_Id=?");
                        getCartProductInfo.setString(1,verifyStoreResultset.getString("productId"));

                        ResultSet verifyCartProductResultSet= getCartProductInfo.executeQuery();
                        if(verifyCartProductResultSet.next()){
                            int productImage=R.drawable.ketchup;
                            if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Excel")){
                                productImage=R.drawable.excelchew;
                            }

                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Sauce")){
                                productImage=R.drawable.sauce;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Hair Spray")){
                                productImage=R.drawable.tresemme;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Organics")){
                                productImage=R.drawable.organics;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Apples")){
                                productImage=R.drawable.apple;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Knorr Soup")){
                                productImage=R.drawable.knorr;
                            }

                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Honey")){
                                productImage=R.drawable.honey;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Croissants")){
                                productImage=R.drawable.crescent;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Milk")){
                                productImage=R.drawable.milk;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Veg Soup")){
                                productImage=R.drawable.campbell;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Coffee")){
                                productImage=R.drawable.coffee;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Beans")){
                                productImage=R.drawable.beans;
                            }
                            else if(verifyCartProductResultSet.getString("Product_Name").equalsIgnoreCase("Apples")){
                                productImage=R.drawable.apple;
                            }
                            else{

                            }

                                myList.add(new ShoppingCartItem(productImage,verifyCartProductResultSet.getString("Product_Name"),verifyCartProductResultSet.getString("Product_Price"),verifyCartProductResultSet.getString("Product_Weight"),verifyCartProductResultSet.getString("Product_Id")));
                        }
                    }
                    else {

                    }

                    System.out.println("THIS IS THE SHOPPING CART CLASS METHOD");

                }
                ShoppingCartItem[] shoppingCartItem = myList.toArray(new ShoppingCartItem[myList.size()]);
                for (ShoppingCartItem myObject : shoppingCartItem) {
                    System.out.println(myObject);
                }

                ArrayList<ShoppingCartItem> itemList = new ArrayList<ShoppingCartItem>(Arrays.asList(shoppingCartItem));

                if(shoppingCartItem.length!=0) {
                    ShoppingCartAdapter adapter = new ShoppingCartAdapter(this.getContext(), R.layout.activity_shopping_cart_item, itemList);
                    setListAdapter(adapter);
                }
                else{
                    ((MainActivity)getActivity()).emptyShoppingCart();
                }


                //getListView().setOnItemClickListener(this);

            }
            else {

            }
        }
        catch(Exception e){
            System.out.println("EXCEPTION OCCURED IN SHOPPING CART FRAGMENT -: "+e.getMessage());
        }


    }

}
