package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.slt_lite.common.urls;
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


public class Activity2 extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> complains1;
    String []complains;

    String data = "";
    String dataParse = "";
    String singleParse = "";



    public int complainNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complainsnew);

        mListView=(ListView)findViewById(R.id.listview);

       // obj.change();




        new GetDataTaskk().execute("http://"+urls.ip+":1000/api/complains");


        //  new GetDataTask().execute("http://192.168.8.103:1000/api/complains");


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:

                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(Activity2.this, qrcoder.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(Activity2.this, store_main.class);
                        startActivity(intent2);
                        break;


                    case R.id.ic_backup:
                        Intent intent4 = new Intent(Activity2.this, MapsActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }


    public void clicked(View view) {





        Intent intent=getIntent();
        finish();
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

                return getDataa(params[0]);

            }catch (IOException ex ){
                return  "network error!";
            }
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            ComplainDetails obj=new ComplainDetails();

            complains1=new ArrayList<>(obj.complainName);

            TechhMyAdapter myAdapter = new TechhMyAdapter(Activity2.this, complains1);
            mListView.setAdapter(myAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent mIntent = new Intent(Activity2.this, complain_details.class);
                    startActivity(mIntent);
                    ComplainDetails.complainNo=i+1;
                }
            });

        }






        public String getDataa(String urlPath) throws IOException {




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


    public class GetDataTask extends AsyncTask<String, Void, String> {



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
