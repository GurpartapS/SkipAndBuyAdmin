package com.example.gurpartap.skip_and_buy_admin.Controller;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gurpartap.skip_and_buy_admin.Model.SqlConnection;
import com.example.gurpartap.skip_and_buy_admin.Model.UserAccount;
import com.example.gurpartap.skip_and_buy_admin.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
/*
    *   ProductInfoFragment displays all the information related to the scanned product
    *   ProductInfoFragment also provides functionality to the user to add product to the cart
    *   It also allows user to go back to the store page so that user can scan another item
*/

public class ProductInfoFragment extends Fragment {

    private String productName;
    private String productPrice;
    private String productDescription;
    private String productQuantity;
    private String productBrand;
    private String productSKU;
    private String productUPC;
    private String productWeight;
    private String productReviews;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_product_info, container, false);

        setProduct(rootView);

        TextView productDetail=(TextView)rootView.findViewById(R.id.productDescription);
        productDetail.setText(productDescription);

        TextView productBrandName=(TextView)rootView.findViewById(R.id.productName);
        productBrandName.setText(productName);

        TextView productWeightField=(TextView)rootView.findViewById(R.id.productWeight);
        productWeightField.setText(productWeight);

        TextView productTotalPrice=(TextView)rootView.findViewById(R.id.productPrice);
        productTotalPrice.setText("$"+productPrice+" / Pc.");

        TextView productBrandField=(TextView)rootView.findViewById(R.id.productBrand);
        productBrandField.setText("Brand:  "+productBrand);

        TextView productReviewsField=(TextView)rootView.findViewById(R.id.productReviews);
        productReviewsField.setText(productReviews+" Reviews");

        TextView productWeightSpec=(TextView)rootView.findViewById(R.id.productWeightSpec);
        productWeightSpec.setText("Weight:  "+productWeight);

        EditText productQuantity=(EditText)rootView.findViewById(R.id.productQuantity);
        productQuantity.setEnabled(false);

        TextView productSKUField=(TextView)rootView.findViewById(R.id.productSKUSpec);
        productSKUField.setText("SKU:  "+productSKU);

        TextView productUPCField=(TextView)rootView.findViewById(R.id.productUPCSpec);
        productUPCField.setText("UPC:  "+productUPC);

        Button scanAnother=(Button)rootView.findViewById(R.id.scanAnotherItemButton);
        Button addItemToCart=(Button)rootView.findViewById(R.id.addItemToCartButton);

        String SHOWCASE_ID="26";
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this.getActivity(), SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(productQuantity,
                "Increase product quantity in shopping cart once you add this item to cart", "GOT IT");

        sequence.addSequenceItem(scanAnother,
                "Scan another item by going back to the scanner and discard this item", "GOT IT");

        sequence.addSequenceItem(addItemToCart,
                "Use this button to add this product and navigate to shopping cart","GOT IT");

        sequence.start();


        return rootView;
    }

    public void setProduct(View rootView){

        String storeId="";

        try {
            SqlConnection connn = new SqlConnection();

            Connection connect = connn.connect();
            Connection connect1 = connn.connect();

            if (connect != null) {

                PreparedStatement getStoreInfo = connect.prepareStatement("Select storeId from Store where storeAddress=?");

                getStoreInfo.setString(1,MainActivity.location);

                System.out.println("THE STORE LOCATION IS:"+MainActivity.location);

                ResultSet verifyStoreResultset = getStoreInfo.executeQuery();

                if(verifyStoreResultset.next()){
                    System.out.println("PRINTING THE RESULTS OF USER ACCOUNT QUERY1");
                    storeId=verifyStoreResultset.getString("storeId");
                }
            }
            else {
                Snackbar.make(rootView, "Connection Problem", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            if (connect1 != null) {

                PreparedStatement getProductInfo = connect1.prepareStatement("Select * from products where Product_ID=? and Store_Id=?");

                getProductInfo.setString(1,MainActivity.scannedProductCode);
                getProductInfo.setString(2,storeId);

                System.out.println("THE PRODUCT ID IS:"+MainActivity.scannedProductCode);
                System.out.println("THE STORE ID IS:"+storeId);

                ResultSet verifyProductResultset = getProductInfo.executeQuery();

                if(verifyProductResultset.next()){
                    System.out.println("FOUND THE PRODUCT");
                    productDescription=verifyProductResultset.getString("Product_Detail");
                    productPrice=verifyProductResultset.getString("Product_Price");
                    productName=verifyProductResultset.getString("Product_Name");
                    productBrand=verifyProductResultset.getString("Product_Brand");
                    productSKU=verifyProductResultset.getString("Product_SKU");
                    productWeight=verifyProductResultset.getString("Product_Weight");
                    productReviews=verifyProductResultset.getString("Product_Reviews");
                    productUPC=MainActivity.scannedProductCode;

                }
                else{
                    System.out.println("UNABLE TO FIND THE PRODUCT");
                    ((MainActivity)getActivity()).productNotFound();
                }

            }
            else {
                Snackbar.make(rootView, "Connection Problem", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        catch(Exception e){

        }
    }


    public void saveShoppingCart(){
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
            if (connect1 != null) {

                PreparedStatement getProductInfo = connect1.prepareStatement("Select * from products where Product_ID=? and Store_Id=?");

                getProductInfo.setString(1,MainActivity.scannedProductCode);
                getProductInfo.setString(2,storeId);

                System.out.println("THE PRODUCT ID IS:"+MainActivity.scannedProductCode);
                System.out.println("THE STORE ID IS:"+storeId);

                ResultSet verifyProductResultset = getProductInfo.executeQuery();

                if(verifyProductResultset.next()){
                    productPrice=verifyProductResultset.getString("Product_Price");

                }
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

                PreparedStatement getProductInfo = connect1.prepareStatement("Insert into cart values(?,?,?,?,?)");


                getProductInfo.setString(1,userId);
                getProductInfo.setString(2,MainActivity.scannedProductCode);
                getProductInfo.setString(3,"1");
                getProductInfo.setString(4,productPrice);
                getProductInfo.setString(5,storeId);

                getProductInfo.executeUpdate();

            }
            else {

            }
        }
        catch(Exception e){
                System.out.println("EXCEPTION OCCURED -: "+e.getMessage());
        }
    }

}
