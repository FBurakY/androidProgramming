package com.fburaky.runnablehandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int number ;
    Runnable runnable;
    Handler handler;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        buttonStart = findViewById(R.id.buttonStart);
        number = 0;
    }

    public void start(View view){

            handler = new Handler();

            runnable = new Runnable() {
                @Override
                public void run() {

                    // Run metodunun içerisine yazdığımız kod , benim belirttiğim periyor içerisinde olacaktır.
                    textView.setText("Second : "+ number);
                    number++;
                    textView.setText("Second :" + number);
                    handler.postDelayed(runnable,1000);
                }
            };
                handler.post(runnable);
                // Aşağıdaki kod sayesinde star butonuna basılıp , start metodu çalıştıktan sonra
                // Tıkladığımız buton , tıklanılamaz duruma gelecektir !
                buttonStart.setEnabled(false);

    }

    public void stop(View view){

        buttonStart.setEnabled(true);
        // Arkada planda çalışan Runnable objesini kapatmak içinse ;
        handler.removeCallbacks(runnable);
        number = 0;
        textView.setText("Second :" + number);
    }

}