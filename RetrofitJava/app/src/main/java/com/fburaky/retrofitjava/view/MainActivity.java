package com.fburaky.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fburaky.retrofitjava.R;
import com.fburaky.retrofitjava.adapter.RecyclerViewAdapter;
import com.fburaky.retrofitjava.model.CryptoModel;
import com.fburaky.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //https://api.nomics.com/v1/prices?key=64a79c547165987af3eb2b12c290541b
    // Datayı indirmek için ;
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL ="https://api.nomics.com/v1/";
    Retrofit retrofit;

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Retrofit && Json
        // Bizim için Json oluşturmaktadır .
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        getDataFromAPI();
    }


    private  void getDataFromAPI(){
        // Aşağıdaki kodumuz ile birlikte biz servisimizi oluşturmuş oluyoruz .
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        // Verilerimi çekelim ;
        Call<List<CryptoModel>> call = cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                // Bize dönen cevapta yapmak istediğimiz .
                if (response.isSuccessful()){

                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    // RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                // Hata verdiğinde yapılmasını istediklerimizi
                t.printStackTrace();
            }
        });
    }
}