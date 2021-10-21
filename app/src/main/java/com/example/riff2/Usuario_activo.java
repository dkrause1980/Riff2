package com.example.riff2;

import android.content.Context;
import android.content.SharedPreferences;

public class Usuario_activo {

    SharedPreferences preferences;
    Context context;
    String legajo, nombre, apellido;



    public Usuario_activo(String legajo, String nombre, String apellido) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        preferences = context.getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
