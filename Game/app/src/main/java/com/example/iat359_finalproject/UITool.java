package com.example.iat359_finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;

public class UITool {
    public static final int THEME_FIRE = 0;
    public static final int THEME_WATER = 1;
    public static final int THEME_ELECTRIC = 2;
    public static final int THEME_WIND = 3;
    public static final int THEME_EARTH = 4;
    public static final int THEME_TYPE_SOLID = 0;
    public static final int THEME_TYPE_TRANS = 1;


    public static void setViewWidth(View v, int w) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = w;
        v.setLayoutParams(params);
    }

    public static void setViewHeight(View v, int h) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = h;
        v.setLayoutParams(params);
    }

    public static void setButtonThemeColor(View view, int type, Activity a) {
        SharedPreferences sharedPrefs = a.getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
        int currentColor = sharedPrefs.getInt("theme", 0);
        switch (currentColor) {
            case UITool.THEME_FIRE:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundColor(a.getResources().getColor(R.color.fire));
                } else {
                    view.setBackgroundColor(a.getResources().getColor(R.color.fire_trans));
                }
                break;
            case UITool.THEME_WATER:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundColor(a.getResources().getColor(R.color.water));
                } else {
                    view.setBackgroundColor(a.getResources().getColor(R.color.water_trans));
                }
                break;
            case UITool.THEME_ELECTRIC:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundColor(a.getResources().getColor(R.color.electric));
                } else {
                    view.setBackgroundColor(a.getResources().getColor(R.color.electric_trans));
                }
                break;
            case UITool.THEME_WIND:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundColor(a.getResources().getColor(R.color.wind));
                } else {
                    view.setBackgroundColor(a.getResources().getColor(R.color.wind_trans));
                }
                break;
            case UITool.THEME_EARTH:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundColor(a.getResources().getColor(R.color.earth));
                } else {
                    view.setBackgroundColor(a.getResources().getColor(R.color.earth_trans));
                }
                break;

        }

    }



    public static void setThemeColor(View view, int type, Activity a) {
        SharedPreferences sharedPrefs = a.getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
        int currentColor = sharedPrefs.getInt("theme", 0);
        switch (currentColor) {
            case UITool.THEME_FIRE:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundResource(R.color.fire);
                } else {
                    view.setBackgroundResource(R.color.fire_trans);
                }
                break;
            case UITool.THEME_WATER:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundResource(R.color.water);
                } else {
                    view.setBackgroundResource(R.color.water_trans);
                }
                break;
            case UITool.THEME_ELECTRIC:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundResource(R.color.electric);
                } else {
                    view.setBackgroundResource(R.color.electric_trans);
                }
                break;
            case UITool.THEME_WIND:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundResource(R.color.wind);
                } else {
                    view.setBackgroundResource(R.color.wind_trans);
                }
                break;
            case UITool.THEME_EARTH:
                if (type == THEME_TYPE_SOLID) {
                    view.setBackgroundResource(R.color.earth);
                } else {
                    view.setBackgroundResource(R.color.earth_trans);
                }
                break;

        }

    }
}
