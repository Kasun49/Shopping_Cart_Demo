package com.example.acer.slt_lite;


public class PurchaseDetails {

    private String item;
    private String purchqty;
    private String email;
    private String date;

    public PurchaseDetails(String item, String email) {
        this.item = item;
        this.email = email;
    }

    public PurchaseDetails(String item, String purchqty, String email, String date) {
        this.item = item;
        this.purchqty = purchqty;
        this.email = email;
        this.date = date;
    }



    public String getItem() {
        return item;
    }

    public String getPurchqty() {
        return purchqty;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

}