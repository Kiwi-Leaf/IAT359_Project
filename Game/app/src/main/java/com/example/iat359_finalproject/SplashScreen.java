package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    ImageView backgroundIV;
    private GestureDetector myDetector;
    final String DEBUG= "Splash debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        myDetector = new GestureDetector(this, gestureListener);
        backgroundIV=findViewById(R.id.backgroundIV);

    }

    GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener()
    {

        @Override
        public boolean onDown(MotionEvent e) {
            startGame();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e1) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public void onShowPress(MotionEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // TODO Auto-generated method stub
            return false;
        }

    };



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //call the GestureDetector's onTouchEvent method
        return myDetector.onTouchEvent(event);
    }

    public void startGame(){
        Intent start =new Intent(this, MainActivity.class);
        startActivity(start);
    }
}