package com.example.acer.slt_lite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class facebook extends AppCompatActivity {


    CallbackManager callbackManager;
    TextView txtEmail,txtbirthday,txtfriends;
    ProgressDialog mdialoge;
    ImageView imgavtr;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);


        callbackManager = CallbackManager.Factory.create();

        txtbirthday  = (TextView)findViewById(R.id.birthday);
        txtEmail = (TextView)findViewById(R.id.email);
        txtfriends = (TextView)findViewById(R.id.friends);
        imgavtr = (ImageView)findViewById(R.id.avater);

       // printkeyhash();

        final LoginButton loginButton = (LoginButton)findViewById(R.id.logingbtn);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        //,"user_birthday","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginButton.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(facebook.this,MainActivity.class);
                startActivity(intent);
                finish();



                mdialoge = new ProgressDialog(facebook.this);
                mdialoge.setMessage("retiving data...");
                mdialoge.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mdialoge.dismiss();

                        getData(object);
                    }
                });

                Bundle parameters =  new Bundle();
                parameters.putString("fields","id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();





            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });




        if(AccessToken.getCurrentAccessToken() != null){

            txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }

   private void getData(JSONObject object) {

        try {

            URL profile_picture  =  new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
           Picasso.with(this).load(profile_picture.toString()).into(imgavtr);

            txtEmail.setText(object.getString("email"));
            txtbirthday.setText(object.getString("public_user").toString());
          //  txtfriends.setText(object.getString("Friends: "+object.getJSONObject("friends").getJSONObject("summery").getString("total_count")));
           // txtbirthday.setText(object.getString("email"));



        }
        catch (MalformedURLException e) {
           e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void printkeyhash() {


        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.acer.slt_lite", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){

                MessageDigest md= MessageDigest.getInstance("SHA");
               md.update(signature.toByteArray());
               Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
