package com.fburaky.artbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

    Bitmap selectedImage;
    ImageView imageView;
    EditText artNameText , painterNameText , yearText;
    Button saveButton;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imageView = findViewById(R.id.imageView);
        artNameText = findViewById(R.id.artNameText);
        painterNameText = findViewById(R.id.painterText);
        yearText = findViewById(R.id.yearText);
        saveButton = findViewById(R.id.saveButton);

        database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if (info.matches("new")){

            // Yeni bir resim ekleyecek ise ;
            // Verileri girdiğimiz yerleri boş getirelim ...
            artNameText.setText("");
            painterNameText.setText("");
            yearText.setText("");
            saveButton.setVisibility(View.VISIBLE);

            Bitmap selectImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.selectimage);
            imageView.setImageBitmap(selectImage);
        }
        else{
            // Eski resimi açıyor ise ;
            int artId = intent.getIntExtra("artId",1);
            saveButton.setVisibility(View.INVISIBLE);

            // SQLite Verilerimizi çekelim ...
            // Tek bir id üzerinden verimizi çekeceğiz .

            try {
                // Seçim Argümanını aşağıda ki kodda yazıldığı givi yapmaktayız .
                Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?",new String[] {String.valueOf(artId)});

                int artNameIx = cursor.getColumnIndex("artname");
                int painterNameIx = cursor.getColumnIndex("paintername");
                int yearIx = cursor.getColumnIndex("year");
                int imageIx = cursor.getColumnIndex("image");

                while(cursor.moveToNext()){
                    artNameText.setText(cursor.getString(artNameIx));
                    painterNameText.setText(cursor.getString(painterNameIx));
                    yearText.setText(cursor.getString(yearIx));

                    // Resimimizi de aldık
                    byte[] bytes = cursor.getBlob(imageIx);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                    imageView.setImageBitmap(bitmap);

                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }


    }


    public void selectImage(View view){

        // Uygulamayı kullanan kişiden izin alamabilmemiz için aşağıdaki kodları yazıyoruz .
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // ContextCompat ; API-23 Öncesi ve sonrasındanki yapılan değişiklikleri kullanıcının hissetmememi için geliştirilen bir yöntemdir.
            // API-23 Öncesi kullanıcıdan izin istememize gerek yoktu . Erişmek istediğimiz kısım için Manifests'e xml kodunu yazıyorduk.
            // Ama API-23 sonrası için kullanıcıdan erişim izni almamız gerekiyor .
            // Fakat kullanılan telefon API-23 öncesindeyse eğer uygulamamızda sıkıntılar çıkmasına vesile olacaktır . Kod çalışmayacaktır .
            // ContextCompat'i kullanıyoruz ki API-23 öncesine aitse telefon hiç bulaşmayalım
            // Kullanılan telefon API-23 ve sonrası ise erişim iznini kullanıcıdan isteyelim .
            // ContextCompat tam olarak bunu sağlamaktadır . Bizlere ...

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            // Yukarıdaki kodumuzda 1 yazmamızın sebebi ; izin verildiyse eğer dünüş yaptığımız rakam önemli buradaki 1 e göre kodlama yapacağız.

        }// if bloğunda kullanıcıdan izin isteyeceğiz .
        else{
            // Kullanıcıdan erişim izni aldıysak eğer , galeriye gideceğiz .

            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // startActivityForResult() Metodu , bir sonuç için bir aksiyom başlatıyoruz.
            // başlatılar bu aksiyomda bize bir sonuç dönecek bize bu sonucu gösterecek metot gerekmektedir.
            // Bunun için startActivityForResult() Metodunu kullanıyoruz .
            startActivityForResult(intentToGallery,2);

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
                // startActivityForResult() Metodunu bir sonuç için başlatıyoruz
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            Uri imageData = data.getData();

            try {

                if (Build.VERSION.SDK_INT>=28){
                    // Yeni sınıf
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                }
                else{
                    // API-23 öncesi telefonlar için galeriden resim almak için kodlamasını yapıyoruz .
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    imageView.setImageBitmap(selectedImage);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void save(View view){

        String artName = artNameText.getText().toString();
        String painter = painterNameText.getText().toString();
        String year = yearText.getText().toString();

        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        // Aşağıda bulunan kodda aldığımız resimi byte çevriyoruz ...
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();

        try {

            database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY , artname VARCHAR, paintername VARCHAR, year VARCHAR, image BLOB)");

            String sqlString = "INSERT INTO arts (artname,paintername,year,image) VALUES (?,?,?,?)";
            // Aşağıda yazdığımız kodumuzda , bir stringi sql de sql gibi çalıştırmaya yarıyor .
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,artName);
            sqLiteStatement.bindString(2,painter);
            sqLiteStatement.bindString(3,year);
            sqLiteStatement.bindBlob(4,byteArray);
            // Yukarıda aldığımız dinamik verileri SQLite kaydediyoruz .
            sqLiteStatement.execute();


        }catch (Exception e){

        }

        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        // Daha önceki bütün aktiviteleri kapatmak için Flag kullanıyoruz .
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        // finish() Metodu , aktiviteyi komple kapatırken bir önceki aktiviteye bizi geri döndürecek.
        //finish();

    }

    // Resimimlerimizi daha küçük verilere dönüştürmek için aşağıdaki metodumuzu oluşturalım
    public Bitmap makeSmallerImage(Bitmap image, int maximumSize){

        int width = image.getWidth(); // Resimimizin genişliğini alıyoruz
        int height = image.getHeight(); // Resimimizin yüksekliğini alıyoruz

        float bitmapRatio = (float) width / (float) height;
        // Yatay veya dikey mi olduğunu kontrol ediyoruz .

        if (bitmapRatio > 1 ){
            // Resim yataysa
            // Yatak kısmını uzun yaptık
            width = maximumSize;
            // Diğer kısmını aynı oranda küçülttük
            height = (int) ( width / bitmapRatio);
        }else{
            // Resim dikeyse
            // Dikey kısmını uzun yaptık
            height = maximumSize;
            // Diğer kısmını aynı oranda küçülttük
            width = (int) (height * bitmapRatio);
        }
        return  Bitmap.createScaledBitmap(image,width,height,true);
    }


}