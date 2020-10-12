package com.fburaky.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Global olarak objelerimizi tanımlıyoruz !
    TextView timeText;
    TextView scoreText;
    int score ;

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray ;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         // Initialize
         timeText = (TextView) findViewById(R.id.timeText);
         scoreText = (TextView) findViewById(R.id.scoreText);

         imageView = findViewById(R.id.imageView1);
         imageView2 = findViewById(R.id.imageView2);
         imageView3 = findViewById(R.id.imageView3);
         imageView4 = findViewById(R.id.imageView4);
         imageView5 = findViewById(R.id.imageView5);
         imageView6 = findViewById(R.id.imageView6);
         imageView7 = findViewById(R.id.imageView7);
         imageView8 = findViewById(R.id.imageView8);
         imageView9 = findViewById(R.id.imageView9);

         imageArray = new ImageView[] { imageView,imageView2,imageView3 ,
                                            imageView4,imageView5,imageView6,
                                                imageView7,imageView8,imageView9 };

         // Resimlerimizin görünmez olmasını sağladığımız metot !
         hideImages();

         score = 0;

         // Geri sayımı yapmak için ;
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long milisUntilFineshed) {

                // Her bir saniyede yapmak istediğimiz kısım .
                timeText.setText("Time : " + milisUntilFineshed/1000);
            }


            @Override
            public void onFinish() {
                // Zamanımız bittikten sonra ne yapacağımızı yazıyoruz .
                timeText.setText("Time Off ");

                // Runnableyi durdurmak için aşağıdaki kodu yazıyoruz .
                handler.removeCallbacks(runnable);

                // Resimlerimizi Tekrar görünmez olmasını istediğimiz için ;
                for(ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                // Uyarı mesajını vermek için ;
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart ?");
                alert.setMessage("Are you sure to restart game ?");
                // Tekrar oynamak istersek "Yes" tıklandığında çalışan kodlar
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Restart
                        // Bir aktiviteyi baştan çalıştırmak için , aşağıdaki gibi bir yöntemde bulunmaktadır.
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        // Yukarıda güncel aktiviteyi bitirecek ve tekrar başlatacaktır .
                    }
                });

                // Eğer Tekrar oynamak istemez ise "No"
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Game Over !",Toast.LENGTH_LONG).show();
                    }
                });

                // Uyarı ekranımızı göstermek için ;
                alert.show();
            }
        }.start();


    }

    // NOT ;
    // Runnable ; Belirli periyotlarda yaptırmak istediğimiz bir takım işlemler için kullanıyoruz .
    // Handler ; Runnable kullanmamız için gerekli olan bir sınıftır .

    public void increaseScore(View view){

        score++;
        scoreText.setText("Score :  " + score);
    }

    public void hideImages(){

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                // Rastgele bir resimi görünür kılmak için Random kullanıyoruz .

                for(ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                // 9 Yazmamızın sebebi bize 0-8 arası rast gele bir sayı getirmesini istememiz
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                // Runnable çalıştırırken ve bunu bizim yazdığımız periyor diliminde düzenli olarak çalıştır demekteyiz .
                // This yazdığımız da Runnable() 'yi göstermektedir .
                handler.postDelayed(this,500);
            }
        };

            handler.post(runnable);
    }
}