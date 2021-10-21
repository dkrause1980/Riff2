package com.example.riff2.interfaces;

import com.example.riff2.models.Evento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventosAPI {

    @POST("insert_evento")
    Call<Evento> postEvento(@Body Evento evento);

    @GET("/eventos/tecnico/{legajo}")
    Call<List<Evento>> find(@Path("legajo") String legajo);
}
