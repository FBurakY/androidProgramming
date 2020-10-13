package com.fburaky.landmarkbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;



public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Aktivitede ki tasarımlarımızı tanımladık ...
        ImageView imageView = findViewById(R.id.imageView);
        TextView landmarkNameText = findViewById(R.id.landmarkNameText);
        TextView countryNameText = findViewById(R.id.countryNameText);

        // Main Aktivitesinden gelen verileri çekmek için aşağıdaki kodları yazıyoruz ...
        Intent intent = getIntent();
        // Main Aktivitesinde name yazdığımız için alt kısıma da name yazıyoruz ...
        String landmarkName =  intent.getStringExtra("name");
        landmarkNameText.setText(landmarkName);

        // Şehir Isimlerini alıyoruz .
        String countryName = intent.getStringExtra("country");
        countryNameText.setText(countryName);

        // Bitmapimizi static olarak yazdıktan sonra farklı bir aktiviteden seçilen resmi seçebiliyoruz .
        // imageView.setImageBitmap(selectedImage);

        Singleton singleton = Singleton.getInstance();
        imageView.setImageBitmap(singleton.getChosenImage());
    }
}