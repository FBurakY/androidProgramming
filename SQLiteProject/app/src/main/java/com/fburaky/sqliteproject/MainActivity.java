package com.fburaky.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase database = this.openOrCreateDatabase("Musicians",MODE_PRIVATE,null);
            // Yukarıda veritabanımızı açtık . Ve aşağıda ilk tablomuzu açtık .
            database.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY ,name VARCHAR, age INT)");

            // Verilerimizi ekliyoruz ...
            //database.execSQL("INSERT INTO musicians (name,age) VALUES ('James',50)");
            //database.execSQL("INSERT INTO musicians (name,age) VALUES ('Lars',60)");
            //database.execSQL("INSERT INTO musicians (name,age) VALUES ('Kirk',30)");

            // Verimizi Güncellemek için ...
            //database.execSQL("UPDATE musicians SET name='Kirk Hammet' WHERE id =3");

            // Verimizi Silmek içim ...
            //database.execSQL("DELETE FROM musicians WHERE id=2");

            // Filtreleme istemediğimiz için selectionArgs null yazdık !
            Cursor cursor = database.rawQuery("SELECT * FROM musicians",null);

            // Like gibi anlamını taşırken %K yazdığımızda içinde K bulunanları bize dönecektir.
            // '%s' Sonu s ile biten 'K%' K ile başlayan ....
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name like '%s'",null);

            int nameIx = cursor.getColumnIndex("name");
            int ageIx = cursor.getColumnIndex("age");
            int idIx = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){

                System.out.println("Name :"+ cursor.getString(nameIx));
                System.out.println("Age : "+ cursor.getInt(ageIx));
                System.out.println("Id : "+ cursor.getInt(idIx));

            }

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}