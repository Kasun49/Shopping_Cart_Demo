package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.urls;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class techlogin extends AppCompatActivity {

    TextView mResult;

    String data = "",datas ="";
    String dataParse = "",dataParses = "";
    String singleParse = "",singleParses = "";

    MaterialEditText User,Password;

    Button btnSignup,btnlogin,back;
    String kkk = "",iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tecch_login);

      //  mResult = (TextView) findViewById(R.id.tv_result);


        User=(MaterialEditText)findViewById(R.id.User);
        Password=(MaterialEditText)findViewById(R.id.password);

        btnlogin=(Button)findViewById(R.id.btn_log_in);
        back=(Button)findViewById(R.id.backk);
        kkk = User.getText().toString();



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new GetDataTaskk().execute("http://192.168.8.103:1000/api/complains");
                new GetDataTask().execute("http://"+ urls.ip+":1000/api/tech",User.getText().toString(),Password.getText().toString().trim());

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeActivity = new Intent(techlogin.this,loginmenu.class);
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


            progressDialog = new ProgressDialog(techlogin.this);
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


            if(kkk =="true")
            {

                Toast.makeText(techlogin.this,"sign in success", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(techlogin.this,Activity2.class);
                startActivity(homeActivity);
                common.attepmt = "tech";
                common.uname = User.getText().toString();
                finish();
            }else {

                Toast.makeText(techlogin.this,"please sign in",Toast.LENGTH_SHORT).show();
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

                        singleParse = (String) jo.get("name");
                        //+ jo.get("likes") + jo.get("_id");

                        if(jo.get("name").toString().equals(nn) && jo.get("id").toString().equals(ac)){
                            kkk = "true";
                            iid = (String) jo.get("_id");
                        }

                        dataParse = dataParse  + singleParse;
                       // ComplainDetails.complainName.add(singleParse);

                    }




                }catch (Exception e){}

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }



            return kkk;
            // return dataParse.toString();
        }
    }





}
