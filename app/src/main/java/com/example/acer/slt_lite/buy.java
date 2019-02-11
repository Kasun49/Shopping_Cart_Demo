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
import android.widget.TextView;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.urls;

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

public class buy extends AppCompatActivity {

    TextView name,des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        new PostDataTask().execute("http://"+ urls.ip+":1000/api/order");

        name= (TextView)findViewById(R.id.title2);
        des = (TextView)findViewById(R.id.title3);
        name.setText(common.uname);
        des.setText(common.catogeryId);




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
       // Menu menu = bottomNavigationView.getMenu();
       // MenuItem menuItem = menu.getItem(2);
       /// menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        if(common.attepmt == "tech" ) {
                            Intent intent0 = new Intent(buy.this, tech_main.class);
                            startActivity(intent0);
                            break;
                        }else{
                            Intent intent0 = new Intent(buy.this, MainActivity.class);
                            startActivity(intent0);
                            break;


                        }

                    case R.id.ic_android:
                        Intent intent1 = new Intent(buy.this, ActivityOne.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:

                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(buy.this, ActivityThree.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(buy.this, MapsActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

    public class PostDataTask extends AsyncTask<String, Void, String> {

        // String uuname = user.getText().toString();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            progressDialog = new ProgressDialog(buy.this);
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
                dataToSend.put("cname", common.uname );
                dataToSend.put("content",common.catogeryId);
                dataToSend.put("id", 1);
                dataToSend.put("details", 111);



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
