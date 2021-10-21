package com.example.riff2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.example.riff2.interfaces.EmpleadosAPI;
import com.example.riff2.models.Empleado;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Config_User extends Fragment implements View.OnClickListener{

    EditText et_legajo, et_nombre, et_apellido, et_clave, et_repClave;

    Button bt_cambiar;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_config_user, container, false);

        et_legajo = v.findViewById(R.id.cc_et_legajo);
        et_nombre = v.findViewById(R.id.cc_et_nombre);
        et_apellido = v.findViewById(R.id.cc_et_apellido);
        et_clave = v.findViewById(R.id.cc_et_clave_nueva);
        et_repClave = v.findViewById(R.id.cc_et_repetir);
        bt_cambiar = v.findViewById(R.id.cc_bt_cambiar);
        this.rellenarDatos();
        bt_cambiar.setOnClickListener(this);

        return v;
    }

    public void rellenarDatos(){

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        et_legajo.setText(preferences.getString("legajo",""));
        et_nombre.setText(preferences.getString("nombre",""));
        et_apellido.setText(preferences.getString("apellido",""));


    }

    public void cambiarClave(String clave){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.231:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmpleadosAPI empleadosAPI = retrofit.create(EmpleadosAPI.class);
        Call<Empleado> call = empleadosAPI.change(et_legajo.getText().toString(),clave);
        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Clave cambiada",Toast.LENGTH_LONG).show();
                    et_clave.setText("");
                    et_repClave.setText("");
                } else {

                    Toast.makeText(getContext(),"Surgió un error: "+response.message(),Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {

                Toast.makeText(getContext(),"Error al conectar: "+t.getMessage(),Toast.LENGTH_LONG).show();


            }
        });

    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.cc_bt_cambiar){

            if(et_clave.getText().length()<4){
                Toast.makeText(getContext(),"La clave debe ser superior a 4 caracteres",Toast.LENGTH_LONG).show();
            }else if(!et_clave.getText().equals(et_repClave.getText())){
                Toast.makeText(getContext(),"Las claves no coinciden",Toast.LENGTH_LONG).show();
            }else {

                this.cambiarClave(et_clave.getText().toString());

            }
        }

    }
}
