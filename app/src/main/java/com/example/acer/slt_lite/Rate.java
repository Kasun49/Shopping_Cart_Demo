package com.example.acer.slt_lite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Rate extends AppCompatActivity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        back = (Button)findViewById(R.id.back);

        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        final TextView ratingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Rate.this,ProductActivity.class);
                startActivity(intent);
            }
        });





        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDisplayTextView.setText("Your rating is: " + ratingRatingBar.getRating());
            }
        });
    }
}
