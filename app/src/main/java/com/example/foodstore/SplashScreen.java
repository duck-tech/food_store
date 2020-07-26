package com.example.foodstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.foodstore.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DELAY = 2500;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setBackgroundDrawable(null);
        initView();
        animationLogo();
        gotoMainActivity();

    }

    private void initView() {
        imageView = findViewById(R.id.image);
    }

    private void animationLogo() {
        Animation animation = AnimationUtils.loadAnimation(this , R.anim.fade_in);
        animation.setDuration(SPLASH_DELAY);
        imageView.startAnimation(animation);
    }

    private void gotoMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }

}
