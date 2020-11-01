package com.fburaky.instagramcloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText commentText;
    ImageView imageView;
    Bitmap chossenImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        commentText = findViewById(R.id.upload_activity_comment_text);
        imageView = findViewById(R.id.upload_activity_imageview);

    }

    public void upload(View view){

        String comment = commentText.getText().toString();

        // Resimleri eklerken bu imajları ParsFile eklememiz gerekmektedir.

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        chossenImage.compress(Bitmap.CompressFormat.PNG,50 , byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        ParseFile parseFile = new ParseFile("image.png" ,bytes);

        ParseObject object = new ParseObject("Posts");
        object.put("comment",comment);
        object.put("image",parseFile);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Post Uploaded !!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void  chooseImage(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // Kullanıcıdan izin almak için ;
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        else{
            // Zaten hali hazırda izinimiz varsa direk galeriye gideceğiz .
            Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent , 1);
        }
    }

    // Kullanıcı izini verdiğinde yapılacak işlemler ;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // İzin verildiyse kullanıcın mediasına girmek istiyoruz .
        if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent , 1);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Kullanıcı mediadan resimi seçtikten sonra gerçekleşek olan kısımı kodluyoruz ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri imageData = data.getData();
            try {

                if (Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    chossenImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(chossenImage);
                }
                else{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}