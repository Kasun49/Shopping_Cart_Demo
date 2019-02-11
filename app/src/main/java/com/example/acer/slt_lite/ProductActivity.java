package com.example.acer.slt_lite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.constant.Constant;
import com.example.acer.slt_lite.common.commontw;
import com.example.acer.slt_lite.model.Product;
import com.example.paymentlibrary.model.Cart;
import com.example.paymentlibrary.util.CartHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private static final String TAG = "ProductActivity";

    TextView tvProductName;
    TextView tvProductDesc;
    ImageView ivProductImage;
    Spinner spQuantity;
    Button bOrder;
    Product product;
    List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);



        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);


        Bundle data = getIntent().getExtras();
        product = (Product) data.getSerializable("product");

//        Log.d(TAG, "Product hashCode: " + product.hashCode());

        //Set Shopping Cart link
        setShoppingCartLink();

        //Retrieve views
        retrieveViews();

        //Set product properties
        setProductProperties();

        //Initialize quantity
        initializeQuantity();

        //On ordering of product
        onOrderProduct();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ProductActivity.this, storeMainActivityy.class);
        startActivity(intent);
    }

    private void setShoppingCartLink() {
        TextView tvViewShoppingCart = (TextView)findViewById(R.id.tvViewShoppingCart);
        SpannableString content = new SpannableString(getText(R.string.shopping_cart));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvViewShoppingCart.setText(content);
        tvViewShoppingCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveViews() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        bOrder = (Button) findViewById(R.id.bOrder);
    }

    private void setProductProperties() {
        try {
            tvProductName.setText(product.getName());
            tvProductDesc.setText(product.getDescription());
            String s = product.getImageName();
            byte decodedString1[] = Base64.decode(s, Base64.DEFAULT | Base64.NO_WRAP);
            Bitmap bitmapd = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
//        ivProductImage.setImageResource(this.getResources().getIdentifier(product.getImageName(), "drawable", this.getPackageName()));
//        ivImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapd, 120, 120, false));
            Bitmap b = Bitmap.createScaledBitmap(bitmapd, 800, 800, false);
            ivProductImage.setImageBitmap(b);
        }catch (Exception e){
            tvProductName.setText(common.backProduct.getName());
            tvProductDesc.setText(common.backProduct.getDescription());
            String s = common.backProduct.getImageName();
            byte decodedString1[] = Base64.decode(s, Base64.DEFAULT | Base64.NO_WRAP);
            Bitmap bitmapd = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
//        ivProductImage.setImageResource(this.getResources().getIdentifier(product.getImageName(), "drawable", this.getPackageName()));
//        ivImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapd, 120, 120, false));
            Bitmap b = Bitmap.createScaledBitmap(bitmapd, 800, 800, false);
            ivProductImage.setImageBitmap(b);
        }
    }

    private void initializeQuantity() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, QUANTITY_LIST);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuantity.setAdapter(dataAdapter);
        }

    private void onOrderProduct() {
        bOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG, "Adding product: " + product.getName());
                cart.add(product, Integer.valueOf(spQuantity.getSelectedItem().toString()));
                commontw.qty = Integer.valueOf(spQuantity.getSelectedItem().toString());

                commontw.productname.add(product.getName());
                commontw.pqty.add(spQuantity.getSelectedItem().toString());
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivitynew.class);
                startActivity(intent);

            }
        });
    }

}
