package com.example.mabiluygulamaproje;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.Display;
import android.view.WindowManager;
import android.graphics.Point;
import android.view.animation.LinearInterpolator;
import android.view.View;
import android.animation.ValueAnimator;
import android.view.animation.Animation;

public class MainActivity extends AppCompatActivity {
    private ImageView animationImage;
    private TextView internetStatus;
    private Handler handler = new Handler();
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Ekran boyutlarını al
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;

            // View'ları başlat
            animationImage = findViewById(R.id.animationImage);
            internetStatus = findViewById(R.id.internetStatus);
            ImageView backgroundImage = findViewById(R.id.backgroundImage);

            // Resimleri ayarla
            backgroundImage.setImageResource(R.drawable.istanbul_manzara);
            animationImage.setImageResource(R.drawable.marti);

            // Martı animasyonunu başlat
            startSeagullAnimation();

            // İnternet kontrolü yap
            checkInternetConnection();

        } catch (Exception e) {
            Log.e("MainActivity", "Error in onCreate: " + e.getMessage());
        }
    }

    private void startSeagullAnimation() {
        try {
            // Daire çapını ekran boyutuna göre ayarla
            final float radius = Math.min(screenWidth, screenHeight) / 3;
            final float centerX = screenWidth / 2;
            final float centerY = screenHeight / 2;

            // Animasyon değerini oluştur
            ValueAnimator animator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
            animator.setDuration(4000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    float x = centerX + radius * (float) Math.cos(angle);
                    float y = centerY + radius * (float) Math.sin(angle);
                    animationImage.setX(x - animationImage.getWidth() / 2);
                    animationImage.setY(y - animationImage.getHeight() / 2);
                    animationImage.setRotation(angle * 57.2958f); // Radyan'dan dereceye çevir
                }
            });

            // Animasyonu başlat
            animator.start();

        } catch (Exception e) {
            Log.e("MainActivity", "Animation error: " + e.getMessage());
        }
    }

    private void checkInternetConnection() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                internetStatus.setText("İnternet bağlantısı mevcut");
                // 5 saniye bekle ve liste aktivitesine geç
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, EminListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 5000);
            } else {
                internetStatus.setText("İnternet bağlantısı mevcut değil");
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error checking internet: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}