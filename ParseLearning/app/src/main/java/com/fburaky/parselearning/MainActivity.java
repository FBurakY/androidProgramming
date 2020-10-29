package com.fburaky.parselearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        ParseObject object = new ParseObject("Fruits");

        object.put("name","banana");
        object.put("calories",150);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Object Saved",Toast.LENGTH_LONG).show();
                }
            }
        });
        */

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Fruits");
        query.getInBackground("peBcC3hDya", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    String objectName = object.getString("name");
                    int objectCalories = object.getInt("calories");

                    System.out.println("Object Name :" + objectName);
                    System.out.println("Object Calories :" +objectCalories);
                }

            }
        });
        */

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Fruits");

        // Sadece Muzu çekmek istesek ve queryId bilmesek şöyle kodluyor olacağız ;
        //query.whereEqualTo("name","banana");

        // Kalorisi 130 dan küçük olanı bize gösteriyor .
        // query.whereLessThan("calories",130);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{

                    if (objects.size() > 0){

                        for (ParseObject object: objects){

                            String objectName = object.getString("name");
                            int objectCalorie =object.getInt("calories");

                            System.out.println("Object Name :" + objectName);
                            System.out.println("Object Calories :" + objectCalorie);
                        }
                    }
                }
            }
        });
        */
        /*
        // Kullanıcı oluşturma kısımı
        ParseUser user = new ParseUser();
        user.setUsername("fadilBurak");
        user.setPassword("2024");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    e.printStackTrace();
                }
                else{
                    Toast.makeText(MainActivity.this,"User Signed Up !!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        */

        /*
        // Kullanıcı giriş bilgilerinin kontrol edildiği kısım .
        ParseUser.logInInBackground("fadilBurak", "2024", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Welcome : "+user.getUsername(),Toast.LENGTH_LONG).show();
                }
            }
        });
        */
    }
}