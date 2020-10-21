package com.fburaky.travelbook.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fburaky.travelbook.R;
import com.fburaky.travelbook.adapter.CustomAdapter;
import com.fburaky.travelbook.model.Place;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    SQLiteDatabase database;

    ArrayList<Place> placeList = new ArrayList<>();

    ListView listView;

    CustomAdapter customAdapter;

    // Oluşturduğumuz add_place.xml menusünü bağlamak için ;
    // Aşağıda bulunan onCreateOptionsMenu() , onOptionsItemSelected() metotları ,
    // onCreateOptionsMenu() oluşturduğumuz xml menüsünün bağlama işlemlerini burada yazıyoruz .
    // onOptionsItemSelected() metodu ilede oluşturduğumuz menüden bir şey seçilirse burada yazıyoruz .
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Herhangi bir xml dosyasını kodla bağlamamız gerekirse aklımıza gelecek sınıfımız MenuInflater

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_place,menu);





        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Oluşturduğumuz xml menüsünde her hangi bir yer seçilirse

        if(item.getItemId() == R.id.add_place){

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        getData();
    }

    public void getData(){

        customAdapter = new CustomAdapter(this,placeList);

        try {

            database = this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM places",null);

            int nameIx = cursor.getColumnIndex("name");
            int latitudeIx = cursor.getColumnIndex("latitude");
            int longitudeIx = cursor.getColumnIndex("longitude");

            while(cursor.moveToNext()){
                // Verilerimizi çekiyoruz .
                String nameFromDatabase = cursor.getString(nameIx);
                String latitudeFromDatabase = cursor.getString(latitudeIx);
                String longitudeFromDatabase = cursor.getString(longitudeIx);

                Double latitude = Double.parseDouble(latitudeFromDatabase);
                Double longitude = Double.parseDouble(longitudeFromDatabase);

                Place place = new Place(nameFromDatabase,latitude,longitude);

                System.out.println(place.name);
                placeList.add(place);

            }
            // Bir veri değişikliği olduğunu adapter söylüyoruz.
            customAdapter.notifyDataSetChanged();
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("place",placeList.get(position));
                startActivity(intent);
            }
        });

    }



}