package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.store;

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

public class DetailActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImageView;
    TextView mdetails;


    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DetailActivity.this, buy.class);
                startActivity(myIntent);
            }
        });

        //new PostDataTask().execute("http://192.168.8.102:1000/api/order");






      //  mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        mImageView = (ImageView) findViewById(R.id.imageView2);
        mdetails = (TextView) findViewById(R.id.textView2);
        Bundle mBundle = getIntent().getExtras();



        if (mBundle != null) {
           // mToolbar.setTitle(mBundle.getString("countryName"));
            mImageView.setImageResource(mBundle.getInt("countryFlag"));
            mdetails.setText(mBundle.getString("details"));
            store.item_name = mBundle.getString("details");
            common.catogeryId = mBundle.getString("details");
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(DetailActivity.this, ActivityOne.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:

                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(DetailActivity.this, ActivityThree.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(DetailActivity.this, ActivityFour.class);
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



            progressDialog = new ProgressDialog(DetailActivity.this);
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

            Bundle mBundle = getIntent().getExtras();


            try {


              //  JSONObject objectk = new JSONObject();



                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                //dataToSend.optJSONObject(String.valueOf(showsignupDialoge(objectk)));
                 dataToSend.put("cname", common.uname );
                 dataToSend.put("content",mBundle.getString("details"));
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
