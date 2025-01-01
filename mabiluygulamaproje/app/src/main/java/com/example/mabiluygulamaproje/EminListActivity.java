package com.example.mabiluygulamaproje;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class EminListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emin_list);

        ListView listView = findViewById(R.id.eminListView);

        // Özel adapter'ı ayarla
        CustomListAdapter adapter = new CustomListAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                Intent intent = new Intent(EminListActivity.this, DetailActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("EminListActivity", "Error starting DetailActivity: " + e.getMessage());
                e.printStackTrace();
                Toast.makeText(this, "Bir hata oluştu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CustomListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public CustomListAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 1; // Sadece bir öğe gösteriyoruz
        }

        @Override
        public Object getItem(int position) {
            return "İstanbul";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.itemImage);
            TextView textView = convertView.findViewById(R.id.itemText);

            // Resim ve metni ayarla
            imageView.setImageResource(R.drawable.galata);
            textView.setText("İstanbul");

            return convertView;
        }
    }
}