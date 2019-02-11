package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
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

public class loginmenu extends AppCompatActivity {

    TextView mResult;
    String test;

    String data = "";
    String dataParse = "";
    String singleParse = "";

    MaterialEditText User,Password;

    Button btnSignup,btnlogin,tech;
    String kkk = "",iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);

      //  mResult = (TextView) findViewById(R.id.tv_result);


        User=(MaterialEditText)findViewById(R.id.User);
        Password=(MaterialEditText)findViewById(R.id.password);

        btnlogin=(Button)findViewById(R.id.btn_log_in);
        btnSignup=(Button)findViewById(R.id.btn_sign_up);
        tech = (Button)findViewById(R.id.tech);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent homeActivity = new Intent(loginmenu.this,singin.class);
                common.currrenttUser = iid;

                startActivity(homeActivity);
                finish();


            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // new GetDataTask().execute("http://"+urls.ip+":1000/api/status",User.getText().toString(),Password.getText().toString().trim());

                new GetDataTask().execute("http://"+common.ip+":4000/apii/viewcuz",User.getText().toString(),Password.getText().toString().trim());



            }
        });


        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeActivity = new Intent(loginmenu.this,techlogin.class);
                startActivity(homeActivity);
                finish();


            }
        });

    }



    public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            // namee = username.getText().toString();
            // acno = username.getText().toString();


            progressDialog = new ProgressDialog(loginmenu.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        public String doInBackground(String... params) {



            try{

                return getData(params[0],params[1],params[2]);

            }catch (IOException ex ){
                return  "network error!";
            }
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


           // Toast.makeText(loginmenu.this,test, Toast.LENGTH_SHORT).show();
          //  Toast.makeText(loginmenu.this,common.iid, Toast.LENGTH_SHORT).show();
            Toast.makeText(loginmenu.this,common.address, Toast.LENGTH_SHORT).show();



            if(kkk =="true" ){

                Toast.makeText(loginmenu.this,"log in success", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(loginmenu.this,MainActivity.class);
                common.uname = User.getText().toString();
                startActivity(homeActivity);
                finish();

            }else{
                Toast.makeText(loginmenu.this," please sign in", Toast.LENGTH_SHORT).show();


            }






            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }






        public String getData(String urlPath,String nn,String ac) throws IOException {




            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader =null;

            try {
                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;


                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                    result.append(line).append("\n");
                }



                try {
                    JSONArray ja = new JSONArray(data);



                    for (int i =0; i<ja.length();i++){
                        JSONObject jo = (JSONObject) ja.get(i);

                        singleParse = (String) jo.get("firstname");
                        //+ jo.get("likes") + jo.get("_id");

                        if(jo.get("firstname").toString().equals(nn) && jo.get("accountNumber").toString().equals(ac)){
                            kkk = "true";
                            common.iid = (String) jo.get("_id");
                            common.fname = (String) jo.get("firstname");
                            common.email = (String) jo.get("email");
                            common.sub = (String) jo.get("subarea");
                            common.address = (String) jo.get("address");

                        }
                       // test="no";


                        dataParse = dataParse  + singleParse;

                    }


                }catch (Exception e){

                }

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }



            return result.toString();
            // return dataParse.toString();
        }
    }


    class PutDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(loginmenu.this);
            progressDialog.setMessage("Updating data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return putData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data invalid !";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String putData(String urlPath) throws IOException, JSONException {

            BufferedWriter bufferedWriter = null;
            String result = null;

            try {
                //Create data to update
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("fbname", "khan kasun !");
                dataToSend.put("content", "feel good - UPDATED !");
                dataToSend.put("likes", 999);
                dataToSend.put("comments", 999);

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Check update successful or not
                if (urlConnection.getResponseCode() == 200) {
                    return "Update successfully !";
                } else {
                    return "Update failed !";
                }
            } finally {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
        }
    }

}
