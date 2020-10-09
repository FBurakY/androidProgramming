package com.fburaky.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long milisUntilFinished) {
                // Her bir saniyede bir ne yapmak istersek buraya yazıyoruz.
                // Biz her saniyede bir textView Saniyeyi yazmasını istiyoruz !
                textView.setText("Left : " + milisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                // Bitince ne yapmak istersek buraya yazıyoruz !
                Toast.makeText(getApplicationContext() , "Done ! " , Toast.LENGTH_LONG).show();
                textView.setText("Finished !");
            }
        }.start();
    }
}