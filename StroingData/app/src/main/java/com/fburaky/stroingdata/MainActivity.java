package com.fburaky.stroingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;
    Button   button;

    // DB gerektirmeyen , saklamak istediğim küçük verileri
    // SharedPreferences objesini kullanarak kaydediyorum !
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.fburaky.stroingdata", Context.MODE_PRIVATE);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        int storedAge = sharedPreferences.getInt("storedAge",0);

        if ( storedAge == 0) {
            editText2.setText("Your Age :");
        }
        else
            editText2.setText("Your Age :"+ storedAge);
    }


    public void save(View view){

        if (!editText.getText().toString().matches("")){

                int userAge = Integer.parseInt(editText.getText().toString());
                editText2.setText("Your Age:"+ userAge);

                // putInt Metodu aynı HashMap gibi Key-Value prensibine göre çalışmaktadır !
                // Obje üzerinden yaş bilgisini kayıt altına aldık !
                sharedPreferences.edit().putInt("storedAge",userAge).apply();

        }

    }

    public void  delete (View view ){

        int storedData = sharedPreferences.getInt("storedAge",0);

        if(  storedData!=0 ){
            // Aşağıda sharedPreferences objesinin kaydettiği veriyi silmiş olduk !
            sharedPreferences.edit().remove("storedAge").apply();
            editText2.setText("Your Age :");
        }


    }

}