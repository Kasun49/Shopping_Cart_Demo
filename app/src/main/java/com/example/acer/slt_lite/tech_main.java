package com.example.acer.slt_lite;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class tech_main extends AppCompatActivity
     {


    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    SliderLayout sliderLayout ;

    HashMap<String, String> HashMapForURL ;

    HashMap<String, Integer> HashMapForLocalRes ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_main);

        //sliderLayout = (SliderLayout)findViewById(R.id.slider);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_assignment);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_autorenew);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_attach_file);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:

                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(tech_main.this, qrcoder.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(tech_main.this, store_main.class);
                        startActivity(intent2);
                        break;


                    case R.id.ic_backup:
                        Intent intent4 = new Intent(tech_main.this, ActivityFour.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });


/*
        //Call this method if you want to add images from URL .
        AddImagesUrlOnline();

        //Call this method to add images from local drawable folder .
        //AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();


        for(String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(tech_main.this);

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

        sliderLayout.addOnPageChangeListener(tech_main.this);


        */
    }





    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        viewPager.setAdapter(adapter);
    }

    /*

    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {


        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("hiii","yep");
        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("CupCake", "https://adeect.files.wordpress.com/2011/04/ad-1.jpg");
        HashMapForURL.put("Donut", "https://nerdtechy.com/wp-content/uploads/bfi_thumb/Linksys-Max-Stream-EA7300-Review-32i3w6dudeqn41hqx0upe2.jpg");
        HashMapForURL.put("Eclair", "https://i.ytimg.com/vi/kGRACAiXVUU/maxresdefault.jpg");
        HashMapForURL.put("Froyo", "https://images.idgesg.net/images/article/2018/04/mac-antivirus-hub-100754948-orig.jpg");
        HashMapForURL.put("GingerBread", "https://pbs.twimg.com/media/DbsovMEX0AARDYG.jpg");
    }

    /*
    public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<String, Integer>();

        HashMapForLocalRes.put("CupCake", R.drawable.);
        HashMapForLocalRes.put("Donut", R.drawable.donut);
        HashMapForLocalRes.put("Eclair", R.drawable.eclair);
        HashMapForLocalRes.put("Froyo", R.drawable.froyo);
        HashMapForLocalRes.put("GingerBread", R.drawable.gingerbread);

    }
    */
}