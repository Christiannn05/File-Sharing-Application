package com.e.smartdoks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView smartDoks;
    private TextView ftp;
    private TextView proponents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        smartDoks = (TextView) findViewById(R.id.smartDoks);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.transition);
        smartDoks.startAnimation(myanim);
        ftp = (TextView) findViewById(R.id.ftp);
        ftp.startAnimation(myanim);
        proponents = (TextView) findViewById(R.id.proponents);
        proponents.startAnimation(myanim);
         final Intent mainIntent = new Intent(this,MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try{
                    sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
