package com.example.mabiluygulamaproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import android.webkit.WebView;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private YouTubePlayerView youtubePlayerView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_detail);
            
            // WebView'i etkinleştir
            WebView.setWebContentsDebuggingEnabled(true);
            
            // View'ları başlat
            initializeViews();
            
            // Verileri yükle
            loadDataFromDatabase();
            
            // YouTube Player'ı başlat
            if (youtubePlayerView != null) {
                initializeYouTubePlayer();
            } else {
                throw new Exception("YouTube Player view bulunamadı");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "onCreate hatası: " + e.getMessage());
            Toast.makeText(this, "Bir hata oluştu, lütfen tekrar deneyin", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeViews() {
        try {
            titleTextView = findViewById(R.id.titleTextView);
            descriptionTextView = findViewById(R.id.descriptionTextView);
            youtubePlayerView = findViewById(R.id.youtubePlayerView);
            dbHelper = new DatabaseHelper(this);
            
            if (youtubePlayerView != null) {
                getLifecycle().addObserver(youtubePlayerView);
            }
        } catch (Exception e) {
            Log.e(TAG, "View başlatma hatası: " + e.getMessage());
            throw e;
        }
    }

    private void initializeYouTubePlayer() {
        try {
            // Basit başlatma deneyelim
            youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    try {
                        // Veritabanından video ID'yi al
                        Cursor cursor = dbHelper.getIstanbulDetails();
                        String videoId = "eV6lTEY95yY"; // Varsayılan video ID
                        
                        if (cursor != null && cursor.moveToFirst()) {
                            int videoUrlIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_YOUTUBE_URL);
                            String dbVideoId = cursor.getString(videoUrlIndex);
                            if (dbVideoId != null && !dbVideoId.isEmpty()) {
                                videoId = dbVideoId;
                            }
                            cursor.close();
                        }
                        
                        youTubePlayer.cueVideo(videoId, 0);
                        Log.d(TAG, "Video hazırlanıyor: " + videoId);
                    } catch (Exception e) {
                        Log.e(TAG, "Video hazırlama hatası: " + e.getMessage());
                        Toast.makeText(DetailActivity.this, 
                            "Video hazırlanamadı, lütfen tekrar deneyin", 
                            Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "YouTube Player başlatma hatası: " + e.getMessage());
            Toast.makeText(this, 
                "Video oynatıcı başlatılamadı, lütfen tekrar deneyin", 
                Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void loadDataFromDatabase() {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getIstanbulDetails();
            if (cursor != null && cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION);
                
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);

                if (titleTextView != null) titleTextView.setText(title);
                if (descriptionTextView != null) descriptionTextView.setText(description);
            }
        } catch (Exception e) {
            Log.e(TAG, "Veri yükleme hatası: " + e.getMessage());
            Toast.makeText(this, 
                "Veriler yüklenemedi, lütfen tekrar deneyin", 
                Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (youtubePlayerView != null) {
                youtubePlayerView.release();
            }
            super.onDestroy();
        } catch (Exception e) {
            Log.e(TAG, "onDestroy hatası: " + e.getMessage());
            super.onDestroy();
        }
    }
}