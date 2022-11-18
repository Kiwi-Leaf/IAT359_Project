package com.example.iat359_finalproject;

import android.view.View;
import android.view.ViewGroup;

public class UITool {
    public static final int THEME_FIRE=0;
    public static final int THEME_WATER=1;
    public static final int THEME_ELECTRIC=2;
    public static final int THEME_WIND=3;
    public static final int THEME_EARTH=4;



    public static void setViewWidth(View v,int w){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width= w;
        v.setLayoutParams(params);
    }
    public static void setViewHeight(View v, int h){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height= h;
        v.setLayoutParams(params);
    }

    public static void setThemeColor(){

    }
}
