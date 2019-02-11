package com.example.acer.slt_lite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acer.slt_lite.model.ComplainDetails;

public class complain_details extends AppCompatActivity {
    String nameS;
    Button dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);



        TextView name=(TextView)findViewById(R.id.name);
        dir = (Button)findViewById(R.id.directions);
      //  name.setText(ComplainDetails.complainNo);


        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent myIntent = new Intent(complain_details.this, MapsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
