package com.example.acer.slt_lite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.slt_lite.common.common;

public class store_main extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListView;


    String[] countryNames = {"SLT fax machine", "LandPhone", "SLT Router" ,"SLT Peo Tv adapter","Prolink PRS1841 \"ac\" type ADSL Router"};

            /*, "SLT mini Router", "SLT Peo TV", "SLT Modem", "SLT 3G router", "SLT CLI receiver"
            , "SLT Peo Tv adapter", "SLT Smart Phone", "SLT Double Rosette Box", "SLT PEO TV Remote Controller", "Prolink PRS1841 \"ac\" type ADSL Router"}; */
    int[] countryFlags = {
            /*
            R.drawable.abc,
            R.drawable.land1,
            R.drawable.router,

            */
            R.drawable.adsl,
            R.drawable.adapter,
            R.drawable.mini,
            R.drawable.tab,
            R.drawable.modem
          /*  R.drawable.three,
            R.drawable.cli,
            R.drawable.adapter,
            R.drawable.tab,
            R.drawable.box,
            R.drawable.remote, */
            };
    String[] details = {             "SLT Fax Machine\n" +
            "Price: LKR 5000.00\n" +
            "Availability:Available","SLT\n LandPhone\n" +
            "Price: LKR 4500.00\n" +
            "Availability:Available","SLT Router\n" +
            "Price: LKR 3900.00\n"

            +
            "Availability:Available","SLT mini Router\n" +
            "Price: LKR 6000.00\n"
            +
            "Availability:Available","SLT Peo TV\n" +
            "Price: LKR 5000.00\n"
            /*
            +
            "Availability:Available","SLT Modem\n" +
            "Price: LKR 1500.00\n" +
            "Availability:Available","SLT 3G router\n" +
            "Price: LKR 3000.00\n" +
            "Availability:Available","SLT CLI receiver\n" +
            "Price: LKR 4500.00\n" +
            "Availability:Available","SLT Peo Tv adapter\n" +
            "Price: LKR 900.00\n" +
            "Availability:Available","SLT Smart Phone\n" +
            "Price: LKR 15000.00\n" +
            "Availability:Available","SLT Double Rosette Box\n" +
            "Price: LKR 50.00\n" +
            "Availability:Available","SLT PEO TV Remote Controller\n" +
            "Price: LKR 800.00\n" +
            "Availability:Available","Prolink PRS1841 \"ac\" type ADSL Router\n" +
            "Price: LKR 3000.00\n" +
            "Availability:Available"
            */

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);

      //  mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // mToolbar.setTitle(getResources().getString(R.string.app_name));
        mListView = (ListView) findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(store_main.this, countryNames, countryFlags,details);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(store_main.this, DetailActivity.class);
                mIntent.putExtra("countryName", countryNames[i]);
                mIntent.putExtra("countryFlag", countryFlags[i]);
                mIntent.putExtra("details", details[i]);
                startActivity(mIntent);
            }
        });

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

                            Intent intent0 = new Intent(store_main.this, MainActivity.class);
                            startActivity(intent0);
                            break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(store_main.this, qrcoder.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:

                        break;

                    case R.id.ic_center_focus:

                            Intent intent2 = new Intent(store_main.this, ActivityThree.class);
                            startActivity(intent2);
                            break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(store_main.this, MapsActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }
}
