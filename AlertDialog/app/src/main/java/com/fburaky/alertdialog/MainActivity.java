package com.fburaky.alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pop-up mesajını vermek için kullandığımız bir yapı ;
        Toast.makeText(MainActivity.this,"Toast Message",Toast.LENGTH_LONG).show();
    }

    // Projemizde kullanıcıya uyarı mesaj vermek için ilgili kodları yazmaktayız !
    public void save(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //Uyarı Mesajımın Başlığını yazıyoruz !
        alert.setTitle("Save");
        //Uyarı Mesajımın içeriğini yazıyoruz !
        alert.setMessage("Are you sure ?");
        // Yes - No Butonlarını oluşturmak içinse ;
        // Yes - Tıklandığında ;
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Listener bir şeye tıklandığında veya bir olay olduğunda ne olacağını ne yazacağımızı yazdığımız
                // Dinleyici arayüz olarak düşünebiliriz !

                // Yes 'e tıkladığında kaydedildi toast mesaj kutusunda bizi bilgilendirsin .
                Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();

                // .this anahtar kelimesi bulunuduğu sınıfı referans etmekte .
                // Toast.makeText(this , "Saved",Toast.LENGTH_LONG).show();
                // Yukarıda ki this MainActivity sınıfını referans etmektedir !

                //--------------------------------------------------------------
                // Toast.makeText(getApplicationConText(),"Saved",Toast.LENGTH_LONG).show();
                // MainActivity.this yerine getApplicationConText() metodunuda bu şekilde kullanabiliyoruz .
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // No tıklandığında kaydedilmedi toast mesaj kutusunda bizi bilgilendirsin .
               Toast.makeText(MainActivity.this,"Not Saved",Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }

    // ConText Nedir ?
    // App. içerisinde ne oluyor , kim kimle nasıl iletişime geçiyor , Nasıl arka planda işlemler dönüyor
    // Hepsinin bir arada tutulduğu işlem durumu şeklinde düşüne biliriz !

    // ConText 2 ye ayrılıyor :
    // 1) Activity Context ; - Aktivitelerin veya ekranların tutulduğu durumlar . Activity Context kullanmak için "this" veya
    // "MainActivity.this" kullanabiliyoruz .

    // 2) App Context      ; - Fakat uygulamanın genelinde ki aktivitelere ulaşmak istersek ! "getApplicationContext()"
    // metodunu kullanırız

    // ** NOT **
    // - Genel uygulama aktivitelerini erişmek ve kullanmak istediğimiz de getApplicationContext() metodunu kullanmaktayız .
    // - Ama tek bir ekranda veya birden fazla ekranların aktivitelerine erişmek ve kullanmak istersekde MainActivity.this" kullanırız .



}