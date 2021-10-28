package com.example.riff2.interfaces;

import com.example.riff2.models.CodigoEvento;
import com.example.riff2.models.Empleado;
import com.example.riff2.models.Evento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CodigosAPI {

    @GET("codigos_eventos")
    Call<List<CodigoEvento>> getCodigos();

}
