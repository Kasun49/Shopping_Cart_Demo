package com.example.acer.slt_lite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseAdapter extends ArrayAdapter<PurchaseDetails> {



    public PurchaseAdapter(Activity context, ArrayList<PurchaseDetails> purchaseDetailsn){
        super(context, 0, purchaseDetailsn);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.purchase_listview, parent, false
            );
        }



        PurchaseDetails purchaseDetails = getItem(position);

        TextView ratingTextView = (TextView) listItemView.findViewById(R.id.item_purchase);
        ratingTextView.setText(purchaseDetails.getItem());


        TextView productTextView = (TextView) listItemView.findViewById(R.id.purchqty_purchase);
        productTextView.setText(purchaseDetails.getPurchqty());

        TextView priceTextView = (TextView) listItemView.findViewById(R.id.email_purchase);
        priceTextView.setText(purchaseDetails.getEmail());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_purchase);
        dateTextView.setText(purchaseDetails.getDate());





        return listItemView;
    }

}