package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.commontw;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pay1 extends AppCompatActivity {
    Button acc,payp,acc1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay1);

        commontw.paymth = "";


       acc = (Button) findViewById(R.id.acc);
        payp = (Button) findViewById(R.id.payp);
        acc1 = (Button) findViewById(R.id.acc1);



        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pay1.this, Acc2.class);
                startActivity(intent);
            }
        });
        payp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commontw.paymth = "paypal";
                Intent intent = new Intent(Pay1.this, Pay.class);
                startActivity(intent);
            }
        });

        acc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostDataTask().execute("http://"+common.ip+":4000/apii/email");
                commontw.paymth = "bill";
                Intent intent = new Intent(Pay1.this, Acc3.class);
                startActivity(intent);
            }
        });
    }

    public class PostDataTask extends AsyncTask<String, Void, String> {

        // String uuname = user.getText().toString();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //descr = jokeList.get(0);
            /// String joined = String.join(";", jokeList);   java 8, min apk 24
            progressDialog = new ProgressDialog(Pay1.this);
            progressDialog.setMessage("Inserting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {




            try {
                return postData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data Invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //mResult.setText(result);

            Toast.makeText(Pay1.this," Email was sent", Toast.LENGTH_SHORT).show();



            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        public String postData(String urlPath) throws IOException, JSONException {

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;



            try {


                //  JSONObject objectk = new JSONObject();



                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                //dataToSend.optJSONObject(String.valueOf(showsignupDialoge(objectk)));
                dataToSend.put("email",common.email);



                //Initialize and config request, then connect to server.

                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }

            return result.toString();
        }
    }

}
