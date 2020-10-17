package com.fburaky.simpsonbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        Simpson homer = new Simpson("Homer Simpson", "Nuclear",R.drawable.homersimpson);
        Simpson lisa = new Simpson("Lisa Simpson","Stundet",R.drawable.lisasimpson);
        Simpson bart = new Simpson("Bart Simpson","Student",R.drawable.britsimpson);

        final ArrayList<Simpson> simpsonList = new ArrayList<>();
        simpsonList.add(homer);
        simpsonList.add(lisa);
        simpsonList.add(bart);

        CustomAdapter customAdapter = new CustomAdapter(simpsonList,MainActivity.this);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("selectedSimpson",simpsonList.get(i));
                startActivity(intent);


            }
        });

    }
}