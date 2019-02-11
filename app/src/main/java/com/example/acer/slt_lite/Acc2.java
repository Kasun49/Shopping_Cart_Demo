package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import org.json.JSONException;

public class Acc2 extends AppCompatActivity {
    private static final String TAG = "Acc2";
    public static final ArrayList<PurchaseDetails> PurchaseList = new ArrayList<PurchaseDetails>();
    ArrayList<String> item = new ArrayList<String>();
    ArrayList<String> email = new ArrayList<String>();
    // ArrayList<String> imagename = new ArrayList<String>();
    ArrayList<Integer> purchqty = new ArrayList<Integer>();
    ArrayList<String> datesw = new ArrayList<String>();
    ArrayList<String>  bbb = new ArrayList<String>();
    ArrayList<String>is_delivered=new ArrayList<String>();

    String data = "";
    String dataParse = "";
    String singleParse = "";

    int x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc2);

        PurchaseList.clear();
        x=1;
        String id="5c472397fb6fc02d2ef115fb";
        // final Product PRODUCT1 = new Product(1, "Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
        //PRODUCT_LIST.add(PRODUCT1);
        new GetDataTask().execute("http://"+common.ip+":4000/apii/getpurch");





    }


    public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;
        private String urlPath;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            // namee = username.getText().toString();
            // acno = username.getText().toString();


            progressDialog = new ProgressDialog(Acc2.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();


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


            /*

            Toast.makeText(loginmenu.this,test, Toast.LENGTH_SHORT).show();


            if(kkk =="true" ){

                Toast.makeText(loginmenu.this,"log in success", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(loginmenu.this,MainActivity.class);
                common.uname = User.getText().toString();
                startActivity(homeActivity);
                finish();

            }else{
                Toast.makeText(loginmenu.this," please sign in", Toast.LENGTH_SHORT).show();


            }

            */


            StringBuilder listString = new StringBuilder();
            for (String s : item)
                listString.append(s+"\n");

            String descr = listString.toString();
//
//
//
            for(int i=0;i<item.size();i++){
                // String naaa = "product"+i;
                String d = Integer.toString(purchqty.get(i).intValue());
//                BigDecimal d1 = BigDecimal.valueOf(d);

                String strDate=is_delivered.get(i);

//                        datesw.get(i).toString();

                PurchaseDetails naaa = new PurchaseDetails(item.get(i),d,email.get(i),datesw.get(i).split("T")[0]);
                PurchaseList.add(naaa);
            }
        final ArrayList<PurchaseDetails> purchaseDetails = PurchaseList;
        ListView PurchaseDetailsList = (ListView) findViewById(R.id.list);
        PurchaseAdapter adapter = new PurchaseAdapter(Acc2.this, purchaseDetails);
        PurchaseDetailsList.setAdapter(adapter);

            // String naaa = "product"+i;
            //Product naaa = new Product(1,"Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
            //PRODUCT_LIST.add(naaa);



//            lvProducts.setOnItemClickListener(new OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    Product product = PRODUCT_LIST.get(position - 1);
//                    Intent intent = new Intent(Acc2.this, ProductActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("product", product);
//                    Log.d(TAG, "View product: " + product.getName());
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });




            Toast.makeText(Acc2.this,Integer.toString(x), Toast.LENGTH_SHORT).show();

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }






        public String getData(String urlPath) throws IOException {
            this.urlPath = urlPath;


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
                x=4;
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

                        //  singleParse = (String) jo.get("firstname");
                        //+ jo.get("likes") + jo.get("_id");

                        if(jo.get("name").toString().equals(common.fname)) {
                            //kkk = "true";
                            item.add((String) jo.get("item"));
                            purchqty.add((Integer) jo.get("purchqty"));
                            email.add((String) jo.get("paymentmethod"));
                            is_delivered.add((String) jo.get("is_delivered"));
                            datesw.add((String) jo.get("date"));


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




}