package com.fburaky.artbook;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Tıklanınca ne olmasını istiyorsak buraya yazıyoruz .

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                // Aktivitemiz çalışmadan önce diğer aktivitemize verilerimizi göndereceğiz
                // Hangi id tıkladığımızın indexini alıyoruz . position !!!
                intent.putExtra("artId",idArray.get(position));
                intent.putExtra("info","old");
                startActivity(intent);
            }
        });

        getData();

    }

    public void getData(){

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts",null);
            int nameIx = cursor.getColumnIndex("artname");
            int idIx = cursor.getColumnIndex("id");


            while(cursor.moveToNext()){

                nameArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));
            }
            // Dizilerimize yeni bir veri ekledikten sonra bunu listende göster !!!
            arrayAdapter.notifyDataSetChanged();

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hangi menüyü göstermek istiyorsak onu burada belirliyoruz .
        // Not : Bir xml yaptığımızda onu aktivitemizin içerisinde gösterebilmemiz için Inflater kullanıyoruz .
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_art,menu);
        // Yukarıdaki kodumuz da kendimi oluşturduğumuz menu'yü aktivitemize bağlamış olduk.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Kullanıcı herhangi bir itemı seçtiğinde ne yapacağımızı belirtiyoruz .

        // Oluşturduğumuz menu'de herhangi bir şey seçilirse yapmak istediğimiz yapıyı burada kodluyoruz .
        if(item.getItemId() == R.id.add_art_item){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}