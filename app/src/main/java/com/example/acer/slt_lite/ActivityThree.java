package com.example.acer.slt_lite;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;
import com.example.acer.slt_lite.common.urls;
import com.example.acer.slt_lite.model.TagListSimpleSearch;
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
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by User on 4/15/2017.
 */

public class ActivityThree extends AppCompatActivity {

    MaterialEditText complaintxt;
    Button submit;
    String data = "";
    String dataParse = "";
    String singleParse = "";
    String subarea,email,fname,address,state,descr;
    public static String oneSpace =" ";
    public static int tikMark =0X2714;
    public static int crossMark =0X2715;
    public static int tikMarkAroundBox =0X2611;
    public static int crossMarkAroundBox =0X274E;
    public static String dash ="-";
    private Spinner mySpinner;
    public static String ADSLcol,peocol,othercol;
    List<TagListSimpleSearch> tagsNames = new ArrayList<>();
    //ArrayList<String> jokeList;
    ArrayList<String> jokeList = new ArrayList<String>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);



        complaintxt = (MaterialEditText)findViewById(R.id.editNewUserNames);
        submit = (Button)findViewById(R.id.btnverfy);
        state = "notdone";


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PostDataTask().execute("http://"+common.ip+":4000/apii/complains");
                Toast.makeText(ActivityThree.this,jokeList.get(0), Toast.LENGTH_SHORT).show();
                Intent intent0 = new Intent(ActivityThree.this, cuzzmappp.class);
                startActivity(intent0);

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
                        Intent intent0 = new Intent(ActivityThree.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(ActivityThree.this, ActivityOne.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(ActivityThree.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:

                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(ActivityThree.this, ActivityFour.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });


        mySpinner= (Spinner) findViewById(R.id.mySpinner);

        TagListSimpleSearch tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("0");
        tagSpecific.setTagText(oneSpace);
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("1");
        tagSpecific.setTagText("select All Items");
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("2");
        tagSpecific.setTagText("remove All Items");
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("0");
        tagSpecific.setTagText("ADSL Error");
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("1");
        tagSpecific.setTagText("Peo Tv");
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("2");
        tagSpecific.setTagText("Other");
        tagsNames.add(tagSpecific);

        tagSpecific=new TagListSimpleSearch();
        tagSpecific.setTagId("3");
        tagSpecific.setTagText("Item 3");
        tagsNames.add(tagSpecific);

        final AdapterTagSpinnerItem adapterTagSpinnerItem = new AdapterTagSpinnerItem(this, 0, tagsNames,mySpinner);
        mySpinner.setAdapter(adapterTagSpinnerItem);


////////////////////////////////////////////////////////////////////////
    }










    public class AdapterTagSpinnerItem extends ArrayAdapter<TagListSimpleSearch>
    {
        private LayoutInflater mInflater;
        private List<TagListSimpleSearch> listState;
        public Spinner mySpinner = null;

        public AdapterTagSpinnerItem(Context context, int resource, List<TagListSimpleSearch> objects, Spinner mySpinner)
        {
            super(context, resource, objects);
            this.listState = objects;
            this.mySpinner = mySpinner;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView, ViewGroup parent)
        {
            String text = "";
            final ViewHolder holder;
            if (convertView == null)
            {
                holder = new ViewHolder();
                mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.spinnertxt, null, false);
                holder.mTextView = convertView.findViewById(R.id.sptxt);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            /**
             * check position , if position is zero we put space on top of list of spinner
             */
            if ((position == 0))
                text = oneSpace;
            /**
             * check position , if position is one we put cross mark before text to show that position used to be for clear all selected items on spinner
             */
            else if ((position == 1))
                text = "  " + String.valueOf((char) crossMarkAroundBox) + " " + listState.get(position).getTagText();
            /**
             * check position , if position is two we put check mark before text to show that position used to be for select all items on spinner
             */
            else if ((position == 2))
                text = "  " + String.valueOf((char) tikMarkAroundBox) + " " + listState.get(position).getTagText();
            /**
             * check position , if position is bigger than two we have to check that position is selected before or not and put check mark or dash before text
             */
            else
            {
                if (listState.get(position).isSelected())
                {
                    text = "  " + String.valueOf((char) tikMark) + " " + listState.get(position).getTagText();
                }
                else
                {
                    text = "  " + String.valueOf(dash) + " " + listState.get(position).getTagText();
                }
            }
            holder.mTextView.setText(text);
            holder.mTextView.setTag(position);
            holder.mTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    /**
                     * if you want open spinner after click on text for first time we have to open spinner programmatically
                     */
                    mySpinner.performClick();
                    int getPosition = (Integer) v.getTag();

                    listState.get(getPosition).setSelected(!listState.get(getPosition).isSelected());
                    notifyDataSetChanged();

                    /**
                     * if clicked position is one
                     * that means you want clear all select item in list
                     */
                    if (getPosition == 1)
                    {
                        clearList();
                    }
                    /**
                     * if clicked position is two
                     * that means you want select all item in list
                     */
                    else if (getPosition == 2)
                    {
                        fillList();
                    }

                    if (getPosition == 3)
                    {

                        //if is clicked yes, no
                        ADSLcol  = "#6982";
                        jokeList.add(listState.get(3).getTagText().toString());



                    }

                    if (getPosition == 4)
                    {
                        ADSLcol = "#8000000";
                        jokeList.add(listState.get(4).getTagText().toString());



                    }



                }
            });
            return convertView;
        }


        /**
         * clear all items in list
         */
        public void clearList()
        {
            for (TagListSimpleSearch items : listState)
            {
                items.setSelected(false);
            }
            notifyDataSetChanged();
        }

        /**
         * select all items in list
         */
        public void fillList()
        {
            for (TagListSimpleSearch items : listState)
            {
                items.setSelected(true);
            }
            notifyDataSetChanged();
        }

        /**
         * view holder
         */
        private class ViewHolder
        {
            private TextView mTextView;
        }
    }

    public class PostDataTask extends AsyncTask<String, Void, String> {

        // String uuname = user.getText().toString();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            StringBuilder listString = new StringBuilder();
            for (String s : jokeList)
                listString.append(s+".");

             descr = listString.toString();



            progressDialog = new ProgressDialog(ActivityThree.this);
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

            Toast.makeText(ActivityThree.this," New complain added", Toast.LENGTH_SHORT).show();



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
                dataToSend.put("status", state);
                dataToSend.put("name",common.fname);
                dataToSend.put("cid",common.iid);
                dataToSend.put("email",common.email);
                dataToSend.put("description",descr);
                dataToSend.put("subarea",common.sub);
                dataToSend.put("color",ADSLcol);
                dataToSend.put("address",address);


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



    public class GetDataTask extends AsyncTask<String, Void, String> {



        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            // namee = username.getText().toString();
            // acno = username.getText().toString();


            progressDialog = new ProgressDialog(ActivityThree.this);
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


            Toast.makeText(ActivityThree.this," id fetched", Toast.LENGTH_SHORT).show();
            Toast.makeText(ActivityThree.this,common.compid, Toast.LENGTH_SHORT).show();





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

                      //  singleParse = (String) jo.get("firstname");
                        //+ jo.get("likes") + jo.get("_id");

                        if(jo.get("name").toString().equals(nn)){

                            common.compid = (String) jo.get("_id");


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