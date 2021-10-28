package com.example.riff2.models;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {

    final static String BASE_URL = "http://192.168.0.78:5000/";

    public static retrofit2.Retrofit get_retrofit(){

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}
