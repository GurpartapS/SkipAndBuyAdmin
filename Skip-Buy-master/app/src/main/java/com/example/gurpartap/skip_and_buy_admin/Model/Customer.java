package com.example.gurpartap.skip_and_buy_admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by OWNER on 11/15/2016.
 */

/*
    * This class maps to the customer table
    * and instance variables of this class
    * are mapped to the columns of the customer table
*/

public class Customer {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public Customer() {
    }


    public Customer(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer getCustomerDetails(String customerEmail){


        Customer customer=null;
        try {
            SqlConnection connn = new SqlConnection();

            Connection connect = connn.connect();

            if (connect != null) {

                PreparedStatement getStoreInfo = connect.prepareStatement("Select * from agent where agentEmail=?");

                getStoreInfo.setString(1, customerEmail);

                ResultSet verifyStoreResultset = getStoreInfo.executeQuery();

                if (verifyStoreResultset.next()) {
                    customer=new Customer(verifyStoreResultset.getString("agentName"),verifyStoreResultset.getString("agentEmail"),verifyStoreResultset.getString("agentPassword"),verifyStoreResultset.getString("agentPhone"));
                }
            } else {

            }
        }
        catch (SQLException e){

        }
        catch(Exception e){

        }
        return customer;
    }
}
