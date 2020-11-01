package com.fburaky.instagramcloneparse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {

    // Bu sınıfı yazmamızın amacı bir arrayAdapter yazmak .
    // Yani FeedActivity de listView ile bağlayabileceğimiz bir arrayAdapter sınıfı oluşturmak .

    private final ArrayList<String> userName;
    private final ArrayList<String> userComment;
    private final ArrayList<Bitmap> userImage;
    private final Activity context;

    /*
    * ÖNEMLİ : Pars'dan alınan verileri FeedActivity içerisine indireceğiz .
    *           indirme işlemi bittikten sonra PostClass sınıfını kullanarak FeedActivity'deki verileri PostClass'a yollicam .
    *           PostClass sınıfı içerisinde aldığımız verileri custom_view.xml'e tek tek listview deki ilgili yere atıyacağım !
    *
    * */

    public PostClass(ArrayList<String> userName , ArrayList<String> userComment , ArrayList<Bitmap> userImage , Activity context){

        super(context, R.layout.custom_view,userName);
        this.userName = userName;
        this.userComment =userComment;
        this.userImage = userImage;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);
        TextView userNameText = customView.findViewById(R.id.custom_view_username_text);
        TextView commentText = customView.findViewById(R.id.custom_view_comment_text);
        ImageView imageView = customView.findViewById(R.id.custom_view_imageview);

        userNameText.setText(userName.get(position));
        imageView.setImageBitmap(userImage.get(position));
        commentText.setText(userComment.get(position));

        return customView;
    }
}
