package com.jcode.onboarding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvNext;
    private ViewPager viewPager;
    private LinearLayout layoutDots;
    private  IntroPref introPref;
    private  int layouts[] = new int[]{R.layout.intro_one,R.layout.intro_two,R.layout.intro_three};
    private TextView dots[];
    private MyViewPagerAdapter myViewPagerAdapter;

    ViewPager.OnPageChangeListener onPageChangeListener  = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position == layouts.length-1){
                tvNext.setText("Start");
            }else {
                tvNext.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNext = findViewById(R.id.tvNext);
        viewPager = findViewById(R.id.view_pager);
        layoutDots = findViewById(R.id.layoutDots);



        introPref = new IntroPref(this);
       if(introPref.isFirstTimeLaunch()){
           Toast.makeText(getApplicationContext(), ""+introPref.isFirstTimeLaunch(), Toast.LENGTH_SHORT).show();
           launchHomeScreen();
           finish();
       }




       tvNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                int current  = getItem();
                if(current < layouts.length){
                    viewPager.setCurrentItem(current);
                }else {
                    launchHomeScreen();
                }
           }
       });

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        addBottomDots(0);

    }

    private void addBottomDots(int cuttentPage){
        dots = new TextView[layouts.length];
        int activeColors []= getResources().getIntArray(R.array.active);
        int inactiveColors []= getResources().getIntArray(R.array.inactive);

        layoutDots.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(i+"");
            dots[i].setTextSize(5);
            dots[i].setTextColor(inactiveColors[cuttentPage]);
            layoutDots.addView(dots[i]);
        }


        if(dots.length>0){
            dots[cuttentPage].setTextColor(activeColors[cuttentPage]);
        }

    }

    private int getItem(){
        Toast.makeText(getApplicationContext(), ""+viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
        return viewPager.getCurrentItem()+1;
    }

    private void launchHomeScreen() {
        introPref.setIsFirstTimeLaunch(false);
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(intent);
        finish();
    }




    public class MyViewPagerAdapter extends PagerAdapter{

        LayoutInflater layoutInflater;




        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view  = layoutInflater.inflate(layouts[position],  container,false);
            ( container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view= (View) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }
}