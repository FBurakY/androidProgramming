package com.fburaky.instaclonefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {



    private ArrayList<String> userEmailList;
    private ArrayList<String> userCommentList;
    private ArrayList<String> userImageList;

    public FeedRecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> userCommentList, ArrayList<String> userImageList) {
        this.userEmailList = userEmailList;
        this.userCommentList = userCommentList;
        this.userImageList = userImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // ViewHolder oluşturulunca ne yapacağımızı kodluyoruz .
        // Metotdan bize dönecek olan PostHolder ;
        // Bizim oluşturduğumuz recycler_row.xml ile bağlama işlemini yapıyoruz .

        // Ve bağlamak içinde LayaoutInflater kullanacağız !

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        // ViewHolder bağlanınca ne yapacağımızı kodluyoruz .
        // İçeriklerinde ne olacağını yazalım ;
        holder.userEmailText.setText(userEmailList.get(position));
        holder.commentText.setText(userCommentList.get(position));

        Picasso.get().load(userImageList.get(position)).into(holder.imageView);
        

    }

    @Override
    public int getItemCount() {

        // Bizim Recycler Row muz kaç item olacağını buraya yazıyoruz !
        // Listemiz ne varsa eleman varsa buraya yazıp .
        // Mevcutta olan eleman kadar row oluşturuyoruz .
        return  userEmailList.size();
    }

    class PostHolder extends  RecyclerView.ViewHolder{

        // recycler_row.xml'de oluşturuduğumuz itemlerı burada tanımlıyoruz !
        // "User Email Text - ImageView - Comment Text " gibi oluşturduğumuz tasarımları burada tanımlıyoruz !

        ImageView imageView;
        TextView userEmailText;
        TextView commentText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            // Yukarıda tanımladıklarımızı bize verilen itemView değişkenini kullanarak tanımlıyoruz
            // Tanımlamalarımızı aşağıdaki gibi yapıyoruz .
            imageView = itemView.findViewById(R.id.recycler_view_row_imageview);
            userEmailText = itemView.findViewById(R.id.recyclerview_row_useremail_text);
            commentText = itemView.findViewById(R.id.recycler_row_comment_text);

        }
    }


}
