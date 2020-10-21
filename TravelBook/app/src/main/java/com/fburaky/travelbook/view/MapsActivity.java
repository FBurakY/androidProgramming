package com.fburaky.travelbook.view;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.fburaky.travelbook.R;
import com.fburaky.travelbook.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    SQLiteDatabase database;

    @Override
    public void onBackPressed() {

        // Telefondaki geri tuşuna basıldığında ne yapılacağını kodladığımız metot
        super.onBackPressed();
        Intent intentToMain = new Intent(this,MainActivity.class);
        startActivity(intentToMain);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.matches("new")){

            // Kullanıcı yeni bir yer kaydetmek istiyor ise ;
            locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    // moveCamera() metodunun bir kere çalışmasını istediğimiz için aşağıdaki gibi bir yol izleyelim ...
                    SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences("com.fburaky.travelbook",MODE_PRIVATE);
                    boolean tractBoolean = sharedPreferences.getBoolean("trackBoolean",false);

                    if(!tractBoolean){
                        // Konum değiştiğinde yapmak istediğimiz kısım
                        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                        sharedPreferences.edit().putBoolean("trackBoolean", true).apply();
                    }
                }
            };

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                // Yani izin önceden verilmemiş ise
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }
            else{
                // Kullanıcı bize izin vermiş ise ;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastLocation != null){
                    LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));

                }
            }
        }
        else{
            mMap.clear();
            // Eğer yeni bir yer kaydetmek istemiyorsa SQLite bulunan eski konum yerini açmak istiyor ;
            // Seçilen yer neresi ise burada göstereceğiz ...

            Place place = (Place) intent.getSerializableExtra("place");
            LatLng latLng = new LatLng(place.latitude,place.longtide);
            String placeName = place.name;

            mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Kullanıcı izini ilk verdiğinde yapılmasını istediğimiz işlemleri
        // onRequestPermissionsResul() metodunun altına yazıyoruz ....
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Izinler dizisinin ilk kontrollerini anlatalım ...
        // int[] grantResults dizisini kontrol etmek çünkü bu bana verilen cevaplar
        // if(grantResults.length>0) koşulu ile , burada gerçekten bize dönen cevaplar var ise ;
        if(grantResults.length>0){
            // if(requestCode == 1) koşulu ile daha önce yukarıda verdiğimiz requestCode kontrol ediyoruz.
            if(requestCode == 1){
                // Altta bulunan if koşulunda ise ; yani izin verildiyse ne yapılacağını yazıyoruz ...
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    // Kullanıcı uygulamayı açıp ilk izini verdiğin de ;
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Intent intent = getIntent();
                    String info = intent.getStringExtra("info");

                    if(info.matches("new")){

                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(lastLocation != null){
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));

                        }
                        else{
                            // SQLite Data && intent data
                            mMap.clear();
                            // Eğer yeni bir yer kaydetmek istemiyorsa SQLite bulunan eski konum yerini açmak istiyor ;
                            // Seçilen yer neresi ise burada göstereceğiz ...

                            Place place = (Place) intent.getSerializableExtra("place");
                            LatLng latLng = new LatLng(place.latitude,place.longtide);
                            String placeName = place.name;

                            mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                        }
                    }
                }
            }
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if (address !=null & addressList.size()>0){
                if (addressList.get(0).getThoroughfare() != null){
                    address += addressList.get(0).getThoroughfare();

                    if (addressList.get(0).getSubThoroughfare() != null){
                        address = " ";
                        address += addressList.get(0).getSubThoroughfare();
                    }
                }
            }else
            {
                address = "New Place";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.clear();
        mMap.addMarker(new MarkerOptions().title(address).position(latLng));

        Double latitude = latLng.latitude;
        Double longitude = latLng.longitude;

        final Place place = new Place(address,latitude,longitude);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
        // Kullanıcı bizim sunuduğumuz seçenekleri seçmeyip uyarı penceresinin dışına tıklamamasını istiyorsak aşağıdaki metodu kullanıyoruz.
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Are you sure ?");
        alertDialog.setMessage(place.name); // Kullanıcının adresi

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try{
                    // Adresi vs. bilgilerini kaydedelim
                    database = MapsActivity.this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS places (id INTEGER PRIMARY KEY ,name VARCHAR, latitude VARCHAR, longitude VARCHAR)");

                    String toCompile = "INSERT INTO places (name,latitude,longitude) VALUES (?,?,?)";
                    SQLiteStatement sqLiteStatement = database.compileStatement(toCompile);

                    // Verileri alıp SQLite kaydediyoruz.
                    sqLiteStatement.bindString(1,place.name);
                    sqLiteStatement.bindString(2,String.valueOf(place.latitude));
                    sqLiteStatement.bindString(3,String.valueOf(place.longtide));
                    sqLiteStatement.execute();

                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Canceles!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();

    }
}