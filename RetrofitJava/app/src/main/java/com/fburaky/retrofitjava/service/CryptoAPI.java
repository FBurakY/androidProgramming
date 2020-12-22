package com.fburaky.retrofitjava.service;

import com.fburaky.retrofitjava.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    // Get , Post , Update , Delete ... işlemlerini hangisini yapacaksak
    // Get  ; Sunucudan veriyi almak için kullandığımız bir işlemdir.
    // Post ; Sunucuya bir veri yazmak için kullandığımız bir işlemdir.

    // Url Base -> www.website.com
    // Get -> price?key=xxxx

    //https://api.nomics.com/v1/prices?key=64a79c547165987af3eb2b12c290541b

    @GET("prices?key=64a79c547165987af3eb2b12c290541b")
    Call<List<CryptoModel>> getData();

}
