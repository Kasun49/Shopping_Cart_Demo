package com.example.acer.slt_lite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.adapter.CartItemAdapter;
import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.constant.Constant;
import com.example.acer.slt_lite.common.commontw;
import com.example.acer.slt_lite.model.CartItem;
import com.example.acer.slt_lite.model.Product;
import com.example.paymentlibrary.model.Cart;
import com.example.paymentlibrary.model.Saleable;
import com.example.paymentlibrary.util.CartHelper;

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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShoppingCartActivitynew extends AppCompatActivity {
    private static final String TAG = "ShoppingCartActivity";

    ListView lvCartItems;
    Button bClear;
    Button bShop;
    Button pay;
    TextView tvTotalPrice;
    String data = "";
    String dataParse = "";
    String singleParse = "";
    String state = "yes";


     public  static int thisqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

      // Toast.makeText(ShoppingCartActivity.this,common.qty, Toast.LENGTH_SHORT).show();
       // Toast.makeText(ShoppingCartActivity.this, (CharSequence) common.productname, Toast.LENGTH_SHORT).show();


        lvCartItems = (ListView) findViewById(R.id.lvCartItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        final Cart cart = CartHelper.getCart();
        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));

        lvCartItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCartItems, false));

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(this);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        lvCartItems.setAdapter(cartItemAdapter);

        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);
        pay = (Button) findViewById(R.id.pay);

        thisqty= commontw.qty;
        for (int i = 0; i < commontw.productname.size(); i++) {
            new GetDataTask().execute("http://"+common.ip+":4000/apii/viewstore",commontw.productname.get(i),"yes");
        }


        bClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clearing the shopping cart");
                cart.clear();
                cartItemAdapter.updateCartItems(getCartItems(cart));
                cartItemAdapter.notifyDataSetChanged();
                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
        });

        bShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(ShoppingCartActivity.this,common.qty, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShoppingCartActivitynew.this, storeMainActivityy.class);
                startActivity(intent);
            }
        });

        lvCartItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ShoppingCartActivitynew.this)
                        .setTitle(getResources().getString(R.string.delete_item))
                        .setMessage(getResources().getString(R.string.delete_item_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<CartItem> cartItems = getCartItems(cart);
                                cart.remove(cartItems.get(position-1).getProduct());
                                cartItems.remove(position-1);
                                cartItemAdapter.updateCartItems(cartItems);
                                cartItemAdapter.notifyDataSetChanged();
                                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));

                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return false;
            }
        });

        lvCartItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                List<CartItem> cartItems = getCartItems(cart);
                Product product = cartItems.get(position-1).getProduct();
                Log.d(TAG, "Viewing product: " + product.getName());
                bundle.putSerializable("product", product);
                Intent intent = new Intent(ShoppingCartActivitynew.this, ProductActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        pay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commontw.price = cart.getTotalPrice();



                if(state == "kk"){

                    Intent intent = new Intent(ShoppingCartActivitynew.this,Pay1.class);
                    startActivity(intent);
                }else{

                    Toast.makeText(ShoppingCartActivitynew.this,"try agin", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
    }




    public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            // namee = username.getText().toString();
            // acno = username.getText().toString();


            progressDialog = new ProgressDialog(ShoppingCartActivitynew.this);
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

           // String strI = Integer.toString(common.idew);
            Toast.makeText(ShoppingCartActivitynew.this,commontw.idew, Toast.LENGTH_SHORT).show();

         if(commontw.qqqe >= thisqty) {
             state = "kk";
         }else{
             state = "";
             Toast.makeText(ShoppingCartActivitynew.this,commontw.itemnn+" select diffrnet quntity", Toast.LENGTH_SHORT).show();

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

                        //singleParse = (String) jo.get("firstname");
                        //+ jo.get("likes") + jo.get("_id");

                        if(jo.get("item").toString().equals(nn)){
                            commontw.qqqe = (Integer) jo.get("qty");
                            commontw.sold = (Integer) jo.get("soldqty");
                            commontw.idew= (String) jo.get("_id");
                            commontw.itemnn = (String) jo.get("item");


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
