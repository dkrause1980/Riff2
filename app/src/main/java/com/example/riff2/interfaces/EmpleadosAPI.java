package com.example.riff2.interfaces;

import com.example.riff2.models.Empleado;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmpleadosAPI {

    @GET("/login/{legajo}")
    Call<Empleado> find(@Path("legajo") String legajo);

    @PATCH("/login/{legajo}")
    Call<Empleado> change(@Path("legajo") String legajo, @Body Empleado empleado);
}
