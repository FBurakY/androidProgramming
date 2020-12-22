package com.fburaky.javafragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToFirst(View view){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FirstFragment firstFragment = new FirstFragment();
        //fragmentTransaction.add(R.id.frame_layout,firstFragment).commit();
        fragmentTransaction.replace(R.id.frame_layout,firstFragment).commit();
    }

    public void goToSecond(View view){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SecondFragment secondFragment = new SecondFragment();
        fragmentTransaction.replace(R.id.frame_layout,secondFragment).commit();

    }

    /*
    * Fragment Nedir ?
    * - Türkçe anlamı "Parça" demektir . Aslında aktiviteler arasında barındırılan ve bizim daha
    *   verimli ce kompak şekilde ekranlarımızı yönetmemizi sağlayan bazı araçlardır. Birden fazla
    *   parçayı aktivitemiz içerisinde kullanabiliyoruz (M.P.U.I ) . Eğer bir aktivite kapanırsa
    *   fragment kapanmak zorundadır . Ama Fragment kapandığında aktivite kapanmak zorunda değildir!
    * */
}