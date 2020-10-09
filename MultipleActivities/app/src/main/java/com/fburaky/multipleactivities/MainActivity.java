package com.fburaky.multipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String userName;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        userName = " ";

    }

    public void changeActivity(View view){

        // Birinci Ekranımızdan kullanıcının adını aldık !
        userName = editText.getText().toString();

        // Aktiviteler arası geçiş yapmak için Intent Kullanıyoruz
        // Bulunduğu "MainActivity.this" ->  Sınıf geçiş yapacağı Sınıf "MainActivity2.class"
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        // putExtra Metodu ile kullancıdan aldığım veriyi , ikinci aktivitiye gönderiyorum !
        intent.putExtra("usperInput",userName);

        // Geçişleri başlatmak içinse aşağıdaki metodu yazıyoruz ;
        startActivity(intent);



    }

}