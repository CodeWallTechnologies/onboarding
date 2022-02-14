package com.jcode.onboarding;


import android.content.Context;
import android.content.SharedPreferences;

//Create by Thura Linn on 14,Feb,2022
public class IntroPref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
  //  private static final String IS_FIRST_TIME_LAUNCH = "firstTime";


    public IntroPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("file name", Context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();

    }

    public void setIsFirstTimeLaunch(boolean firstTimeLaunch){
        editor.putBoolean("firstTime",firstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return  sharedPreferences.getBoolean("firstTime",true);
    }


}
