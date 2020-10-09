package com.fburaky.multipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {


    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = findViewById(R.id.textView);

        // Birinci aktiviteden gelen veriyi çekmek için ;
        // Aşağıda ki kodumuzu yazıyoruz
        Intent intent = getIntent();
        String userName = intent.getStringExtra("usperInput");
        // Birinci Aktiviteden aldığımız veriyi burada bulunan textView aktarıyorum aşağıdaki kod ile ;
        textView.setText(userName);
    }

    public void changeScreen(View view){

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}