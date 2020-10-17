package com.fburaky.simpsonbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Simpson> {


    private ArrayList<Simpson> simpsons;
    private Activity context;

    public CustomAdapter(ArrayList<Simpson> simpsons, Activity context){

        super(context,R.layout.custom_view,simpsons);
        this.simpsons = simpsons;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // getView() metodu ile , hangi view hangi iş yapacağını burada yazabiliyoruz .
        // 1. Öncelikle custom view buraya bağlıyacağız
        // 2. Custom içerisinde oluşturduğumuz textView burada tanımlayacağız
        // 3. TextView da ise buradan gelen simpsons listesi içerisinde ki elemanları tek tek al ve bunların listesini al
        // Yukarıdakileri bu metotta yapacağız .

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);
        // Yukarıdaki kodda , custom_view birebir bir değişken olarak tanımlamama olanak sağlıyor.
        TextView nameView =  customView.findViewById(R.id.customTextView);
        nameView.setText(simpsons.get(position).getName());

        return customView;
    }
}
