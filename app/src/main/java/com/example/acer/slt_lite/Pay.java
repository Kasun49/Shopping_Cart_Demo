package com.example.acer.slt_lite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.commontw;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Pay extends AppCompatActivity {


    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(com.example.acer.slt_lite.config.config.PAYPAL_CLIENT_ID);
                     Button btnPayNow;
                     TextView edtAmout;

    String amount="";

    @Override
    protected void onDestroy(){
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);


        btnPayNow = (Button) findViewById(R.id.btnPayNow);
        edtAmout = (TextView) findViewById(R.id.edtAmout);

        edtAmout.setText(commontw.price.toString());

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessPayment();
            }
        });
    }
    private void proccessPayment() {
        amount = edtAmout.getText().toString();
        PayPalPayment PayPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Pay for SLT", com.paypal.android.sdk.payments.PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,PayPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this,PaymentDetails.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("PaymentAmount",amount)

                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode ==PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this,"Invalid", Toast.LENGTH_SHORT).show();
    }

}
