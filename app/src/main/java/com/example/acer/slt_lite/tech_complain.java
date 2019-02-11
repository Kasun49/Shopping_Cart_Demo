package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.acer.slt_lite.model.ComplainDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class tech_complain extends AppCompatActivity {

    TextView mResult;

    String data = "";
    String dataParse = "";
    String singleParse = "";




    ArrayList<String> complains1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain_main);



      //  new GetDataTaskk().execute("http://192.168.8.103:1000/api/complains");
    }

    public void clicked(View view) {

        //new GetDataTask().execute("http://192.168.8.101:1000/api/tech");
        Intent intent=new Intent(this,Activity2.class);
        startActivity(intent);

    }

    public class GetDataTaskk extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();



        }

        @Override
        public String doInBackground(String... params) {



            try{

                return getData(params[0]);

            }catch (IOException ex ){
                return  "network error!";
            }
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }






        public String getData(String urlPath) throws IOException {




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

                        singleParse = (String) jo.get("content");

                        //+ jo.get("likes") + jo.get("_id");

                        dataParse = dataParse  + singleParse;
                        ComplainDetails.complainName.add(singleParse.toString());


                    }


                }catch (Exception e){}

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }



            return result.toString();
            // return dataParse.toString();
        }
    }


}





