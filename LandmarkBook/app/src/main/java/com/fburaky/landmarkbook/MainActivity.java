package com.fburaky.landmarkbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // static Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = findViewById(R.id.listView);

        //Data
        final ArrayList<String> landmarkNames = new ArrayList<>();
        landmarkNames.add("Pisa"); // Index 0 -> Itali
        landmarkNames.add("Eiffel"); // Index 1 -> France
        landmarkNames.add("Colleseo"); // Index 2 -> Italy
        landmarkNames.add("London Bridge"); // Index 3 -> United Kingdom
        // Yukarıda tanımladığımız dizimizi listView ' de göstermek istiyorum bunun için şu adımları yapıyorum .
        // Not : İlişkili olmasını istediğimiz verilerin indexleri eşit olmak zorundadır !!!
        final ArrayList<String> countryNames = new ArrayList();
        countryNames.add("Itali"); // Index 0
        countryNames.add("France"); // Index 1
        countryNames.add("Italy"); // Index 2
        countryNames.add("United Kingdom"); // Index 3

        // ----------------------------------------------------------------------------------------------------
        // Görselleri bir değişkene atmamız için obje olarak atmamız gerekmetedir.
        // Görsellerimizi obje olarak tanımlayabilmemiz için bitmap sınıfını kullanıyoruz !!!
        // Bitmap ile ürettiğimiz objeler ile görsellerimizi ; Tanımlaya biliyoruz.
        // Sadece dravabledekileri değil internetten indirdiklerimizide tanımlaya biliyoruz .

        // Dravable deki resimlerimi almak istediğimiz için ; BitMapFactory sınıfını kullanıyoruz .
        Bitmap pisa = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.pisa);
        // Artık pisa resminin objesini oluşturduk . Bunu artık dizimize aktara biliriz .

        Bitmap eiffel = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.eiffel);
        Bitmap colesseo = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.colosseo);
        Bitmap londonBridge = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.londonbridge);
        // ----------------------------------------------------------------------------------------------------

        final ArrayList<Bitmap> landmarkImages = new ArrayList<>();
        landmarkImages.add(pisa); // Index 0 -> Pisa -> Italy
        landmarkImages.add(eiffel); // Index 0 -> Eiffel -> France
        landmarkImages.add(colesseo); // Index 0 -> Colleseo -> Italy
        landmarkImages.add(londonBridge); // Index 0 -> London Bridge -> United Kingdom
        // ----------------------------------------------------------------------------------------------------

        // ListView
        // ArrayAdapter'i kullanıyoruz
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,landmarkNames);
        // simple_list_item_1 kullandığımız da ; Liste içerisinde sadece metin göstereceksek eğer kullanırız .
        // ListView ile ArrayAdapteri birbirine bağlamak içinde aşağıada ki kodumuzu yazıyoruz .
        listView.setAdapter(arrayAdapter);

        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Kullanıcımız kaçınıcı indexe tıkladığında int i tıklanılan yerin index sayısına göre değişmektedir.
                Intent intent =new Intent(MainActivity.this,DetailActivity.class);

                // Burada bilgi aktarmak için putExtra() yazdığımız da farklı bir aktivasyona göndermek istediğimiz veriyi gönderiyoruz .
                intent.putExtra("name",landmarkNames.get(i));
                intent.putExtra("country",countryNames.get(i));

                Singleton singleton = Singleton.getInstance();
                singleton.setChosenImage(landmarkImages.get(i));

                startActivity(intent);
            }
        });

    }
}