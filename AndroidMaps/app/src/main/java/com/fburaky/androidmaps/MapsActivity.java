package com.fburaky.androidmaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

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

        // Harita hazırken çalıştırılan bir metottur . Ve Google map initiazlize edilmektedir.
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        // Konum yönetici , sistemin konum bilgilerine erişmemize yaramaktadır.
        // Sistem Servislerini kullanmamıza yaramaktadır .
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //  LocationManager kullanarak kullanıcının güncel konumunu alacağız .
        // Birde arayüzümüz var .

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


                /*
                // onLocationChanged() metodunda olduğu için , konum değiştiğinde bu metot çalışmaktadır ...

                // Her konum çalıştığında marker siliniyoru ...
                mMap.clear();

                LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                // Yukarıda ki kodumuzda kullanıcının enlem-boylam konumunu alıyorum c(.
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Locaiton"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
               */

                // ---------- Adres Bilgilerini almak için ----------------
                /*
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address>  addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addressList != null && addressList.size() > 0){
                        System.out.println("Address : " +addressList.get(0).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                // --------------------------------------------------------
            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            // İzin verilmediyse ..
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            // Yukarıda ki kodumuz da kullanıcadan konum iznini istedik .

        }else{

            // İzin verildiyse location işlemleri yapılacak ..
            // İzin verildiyse kullanıcın konumunu alalım ...
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

            // Uygulamamız açıldığında ilk başta neredeyse orayı göstermek istersek .
            // Son bilinen konum konsepti var bunun içinde ;

               Location lastLocation =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

               // İlk konum eğer null ise bunu kontrol etmek için
               if(lastLocation != null){
                   LatLng userLastLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                   mMap.addMarker(new MarkerOptions().position(userLastLocation).title("Your Location"));
                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,10));
               }
        }


        // Add a marker in Sydney and move the camera
        // LatLng , enlem ve boylam sınıfıdır.
        // Latitude(Enlem)  - Langitude(Boylam)

        //LatLng arpacay = new LatLng(40.838235, 43.326123);
        // addMarker() metodu , kırmızı işaret butonudur.
        //mMap.addMarker(new MarkerOptions().position(arpacay).title("Kars Arpacay"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arpacay,15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Kullanıcıdan izni aldıktan sonra ne yapacağımı yazmak için onRequestPermissionsResult() metodunu kullanıyoruz ...

        if (grantResults.length >0){

            if(requestCode == 1){

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    // izin verildiyse yapacağımız işlemi yazalım

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();

        // Kullanıcının haritada üzerinde tıkladığı yerin adres bilgisini göstermek için ;
        // Haritaya uzun tıklandığında ne olacağını yazmak istiyoruz ...

        // Kullanıcının haritaya uzun süre tıklamasıyla adres bilgisini almak için aşağıdaki kodu yazıyoruz.
        Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
        String address = " ";
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addressList != null && addressList.size() >0) {
                if (addressList.get(0).getThoroughfare() != null){
                    // Adress bilgisini alıyoruz ...
                    // Adres stringime adress cadde ismini ekliyoruz .
                    address += addressList.get(0).getThoroughfare();
                    // Burada her şeyi ekleye bilirim posta kodu , kaçıncı cadde , kaçıncı numara vs.

                    if(addressList.get(0).getSubThoroughfare() != null){
                        address += addressList.get(0).getSubThoroughfare();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Kullanıcının uzunsa haritada tıkladığı yerin markera bilgileri atıyoruz .
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

    }
}