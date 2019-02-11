package com.example.acer.slt_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.adapter.ProductAdapter;
import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class storeMainActivityy extends AppCompatActivity {
    private static final String TAG = "storeMainActivity";
    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> dess = new ArrayList<String>();
    ArrayList<String> imagename = new ArrayList<String>();
    ArrayList<Double> pricee = new ArrayList<Double>();

    String data = "";
    String dataParse = "";
    String singleParse = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeactivity_main);
        PRODUCT_LIST.clear();


       // final Product PRODUCT1 = new Product(1, "Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
        //PRODUCT_LIST.add(PRODUCT1);

        new GetDataTask().execute("http://"+common.ip+":4000/apii/viewstore");



        TextView tvViewShoppingCart = (TextView)findViewById(R.id.tvViewShoppingCart);
        SpannableString content = new SpannableString(getText(R.string.shopping_cart));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvViewShoppingCart.setText(content);

        tvViewShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(storeMainActivityy.this, ShoppingCartActivitynew.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(storeMainActivityy.this, MainActivity.class);
        startActivity(intent);
    }

    public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            // namee = username.getText().toString();
            // acno = username.getText().toString();


            progressDialog = new ProgressDialog(storeMainActivityy.this);
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
            for (String s : name)
                listString.append(s+"\n");

          String descr = listString.toString();



           for(int i=0;i<name.size();i++){
                // String naaa = "product"+i;
               Double d = pricee.get(i).doubleValue();
               BigDecimal d1 = BigDecimal.valueOf(d);

               Product naaa = new Product(1,name.get(i).toString(), d1, dess.get(i).toString(), imagename.get(i).toString());
               PRODUCT_LIST.add(naaa);
            }


            // String naaa = "product"+i;
            //Product naaa = new Product(1,"Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
            //PRODUCT_LIST.add(naaa);

            ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
            lvProducts.addHeaderView(getLayoutInflater().inflate(R.layout.product_list_header, lvProducts, false));

            ProductAdapter productAdapter = new ProductAdapter(storeMainActivityy.this);
            productAdapter.updateProducts(PRODUCT_LIST);

            lvProducts.setAdapter(productAdapter);

            lvProducts.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Product product = PRODUCT_LIST.get(position - 1);
                    common.backProduct=product;
                    Intent intent = new Intent(storeMainActivityy.this, ProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", product);
                    Log.d(TAG, "View product: " + product.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });




            Toast.makeText(storeMainActivityy.this,descr, Toast.LENGTH_SHORT).show();

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
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

                      //  singleParse = (String) jo.get("firstname");
                        //+ jo.get("likes") + jo.get("_id");


                            //kkk = "true";
                               name.add((String) jo.get("item"));
                               dess.add((String) jo.get("brand"));
                               imagename.add((String) jo.get("imagepath"));
                               pricee.add((Double) jo.get("price"));

                           // dess= (String) jo.get("firstname");
                           // common.email = (String) jo.get("email");
                           // common.sub = (String) jo.get("subarea");




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
