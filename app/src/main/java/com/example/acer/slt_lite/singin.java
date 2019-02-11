package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class singin extends AppCompatActivity {


    String data = "";
    String dataParse = "";
    String singleParse = "";
    MaterialEditText password,conformpass,accno,username;

    Button btnverfy,bnconform,back;
   // String usrname="";
   String acno;
    String kkk="true";
    String namee,iid="";
    String pass="",conff="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

       // username =(MaterialEditText)findViewById(R.id.editNewUserNames);
       // accno =(MaterialEditText)findViewById(R.id.accno);
        btnverfy = (Button)findViewById(R.id.btnverfy);
        bnconform = (Button)findViewById(R.id.conform);
        back = (Button)findViewById(R.id.back);

      password = (MaterialEditText)findViewById(R.id.password);
      conformpass =(MaterialEditText)findViewById(R.id.conformpass);
      username =(MaterialEditText)findViewById(R.id.editNewUserNames);
      accno =(MaterialEditText)findViewById(R.id.accno);



       // pass = password.getText().toString();
       // conff = conformpass.getText().toString();

        conformpass.setEnabled(false);
        password.setEnabled(false);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeActivity = new Intent(singin.this,loginmenu.class);
                startActivity(homeActivity);
                finish();


            }
        });



        bnconform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(conformpass.getText().toString().equals(password.getText().toString()) ){
                    new PutDataTask().execute("http://"+common.ip+"/apii/edititem/"+common.iid,password.getText().toString());
                }else{
                    Toast.makeText(singin.this,"check the input passwords again.!",Toast.LENGTH_SHORT).show();
                }



            }
        });



        btnverfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetDataTask().execute("http://"+common.ip+":4000/apii/viewcuz",accno.getText().toString());
            }
        });




    }




   public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();




         // common.uname = username.getText().toString().trim();
         // common.acno = accno.getText().toString().trim();


            progressDialog = new ProgressDialog(singin.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        public String doInBackground(String... params) {



            try{

                return getData(params[0],params[1]);

            }catch (IOException ex ){
                return  "network error!";
            }
        }



       @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


           if(kkk == "true"){
               Toast.makeText(singin.this,"user verfy success",Toast.LENGTH_SHORT).show();
               conformpass.setEnabled(true);
               password.setEnabled(true);
               common.uname = username.getText().toString();

           }else{
               Toast.makeText(singin.this,"try again",Toast.LENGTH_SHORT).show();

           }


            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }






        public String getData(String urlPath,String nn) throws IOException {




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

                    if(jo.get("accountNumber").toString().equals(nn)){
                        kkk = "true";
                        iid = (String) jo.get("_id");
                    }

                        dataParse = dataParse  + singleParse;

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

   public class PutDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(singin.this);
            progressDialog.setMessage("Updating data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return putData(params[0],params[1]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data invalid !";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // mResult.setText(result);

            Toast.makeText(singin.this,"sign in success",Toast.LENGTH_SHORT).show();
            Intent homeActivity = new Intent(singin.this,MainActivity.class);
            common.currrenttUser = username.getText().toString();

            startActivity(homeActivity);
            finish();


            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String putData(String urlPath,String pw) throws IOException, JSONException {

            BufferedWriter bufferedWriter = null;
            String result = null;

            try {
                //Create data to update
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("password", pw);
               // dataToSend.put("content", "feel good - UPDATED !");
               // dataToSend.put("likes", 999);
              //  dataToSend.put("comments", 999);

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
