package com.example.acer.slt_lite;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.urls;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class qrcoder extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    String data="";
    StringBuffer soldDate;
    boolean qrstatus;
    int warrenty;
    StringBuffer serialnum;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr);

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(qrcoder.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create vibrate
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);

//                            txtResult.setText(qrcodes.valueAt(0).displayValue.split("//")[1]);
                            serialnum=new StringBuffer(qrcodes.valueAt(0).displayValue.split("//")[1]);
                            new PostDataTask().execute("http://"+common.ip+":4000/apii/checkWarrantys/"+serialnum.toString());

                        }
                    });
                }
            }
        });




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:

                        Intent intent0 = new Intent(qrcoder.this,MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        break;

                    case R.id.ic_books:
                        Intent intent3 = new Intent(qrcoder.this, storeMainActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent4 = new Intent(qrcoder.this, ActivityThree.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_backup:
                        Intent intent5 = new Intent(qrcoder.this, MapsActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent10 = new Intent(qrcoder.this, tech_main.class);
        startActivity(intent10);
    }



    public class PostDataTask extends AsyncTask<String, Void, String> {

        // String uuname = user.getText().toString();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();





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
            if(qrstatus&&(!(soldDate.toString().isEmpty()))){
                try {
                    Date date=new Date();
                    String currentDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
                    Date dateo=new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
                    Date daten=new SimpleDateFormat("yyyy-MM-dd").parse(soldDate.toString());
                    long diff=getDateDiff(daten,dateo,TimeUnit.DAYS);

                    long warrantys=warrenty;
//                    (diffDays-(warranty*365))>0
                    if(diff<(warrantys*365)){
                        txtResult.clearComposingText();
                        txtResult.setText("Item is within Warranty Period");
                    }else{
                        txtResult.clearComposingText();
                        txtResult.setText("Items Warranty has expired");
                    }
                } catch (Exception e) {
                    Toast.makeText(qrcoder.this,"Catcher", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(qrcoder.this,"QR code is not valid", Toast.LENGTH_SHORT).show();
            }



        }

        public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
            long diffInMillies = date2.getTime() - date1.getTime();
            return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
        }

        public String postData(String urlPath) throws IOException, JSONException {

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;



            try {


                //  JSONObject objectk = new JSONObject();



                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
//                     dataToSend.optJSONObject(String.valueOf(showsignupDialoge(objectk)));






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
                    data = data + line;
                    result.append(line).append("\n");
                }

                JSONObject jos = new JSONObject(data);
                JSONObject joss=jos.optJSONObject("details");
                soldDate=new StringBuffer((String)joss.optString("solddate"));
                warrenty=joss.optInt("warrenty");


                if (urlConnection.getResponseCode()== 200) {
                    qrstatus=true;
                    return "Login successfully !";
                } else {
                    qrstatus=false;
                    return "Login failed !";
                }


            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }


        }
    }



}