package com.example.iat359_finalproject;

import android.view.View;
import android.view.ViewGroup;

public class UITool {
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
}
