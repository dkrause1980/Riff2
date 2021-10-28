package com.example.riff2;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.riff2.interfaces.CodigosAPI;
import com.example.riff2.interfaces.EmpleadosAPI;
import com.example.riff2.interfaces.EventosAPI;
import com.example.riff2.models.CodigoEvento;
import com.example.riff2.models.Empleado;
import com.example.riff2.models.Evento;
import com.example.riff2.models.Retro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;

public class Login extends AppCompatActivity {

    EditText user,password;
    Button btnIngresar, btnCancelar;
    public static Switch aSwitch;
    SharedPreferences preferences;
    private static final String STRING_PREFERENCE = "credenciales";
    final String CODIGOS = "codigos.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();
        user = (EditText)findViewById(R.id.editTextTextPersonName);
        password = (EditText)findViewById(R.id.editTextTextPassword);
        btnIngresar = findViewById(R.id.button);
        btnCancelar = findViewById(R.id.button2);
        aSwitch = (Switch) findViewById(R.id.switch1);

        if(obtenerSwitch()){
            System.out.println(preferences.getBoolean("sesion",false));
            Intent menu = new Intent(getApplicationContext(), MenuPrincipal.class);
            startActivity(menu);


        }

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logueo(user.getText().toString());

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    public boolean obtenerSwitch(){
        preferences = getSharedPreferences(STRING_PREFERENCE,Context.MODE_PRIVATE);
        return preferences.getBoolean("sesion",false);
    }

    public void guardarPreferences(String nombre, boolean sw, String legajo, String apellido){
        preferences = getSharedPreferences(STRING_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre",nombre);
        editor.putBoolean("sesion",sw);
        editor.putString("legajo",legajo);
        editor.putString("apellido",apellido);
        editor.commit();
    }

    /*public void pedirPermisos(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }*/



    public void logueo(String legajo){


        Retrofit retrofit= Retro.get_retrofit();

        EmpleadosAPI llamadasAPI = retrofit.create(EmpleadosAPI.class);
        Call<Empleado> call = llamadasAPI.find(legajo);
        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                try{
                    if(response.isSuccessful()){
                        Empleado e = response.body();
                        if(e.getContrasenia().equals(password.getText().toString())){
                            //Toast.makeText(MainActivity.this,"Ingresando",Toast.LENGTH_SHORT).show();
                            guardarPreferences(e.getNombre(),aSwitch.isChecked(),e.getLegajo(),e.getApellido());
                            System.out.println(e.getApellido());

                            listaCodigos();

                            Intent menu = new Intent(getApplicationContext(), MenuPrincipal.class);
                            startActivity(menu);


                        }else{
                            Toast.makeText(Login.this,"Verifique usuario y contraseña. Error: "+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception ex){
                    Toast.makeText(Login.this,"Verifique usuario y contraseña. Error: "+response.code(),Toast.LENGTH_SHORT).show();
                    System.out.println(ex.getMessage()+" "+password.getText().toString());
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                if(t instanceof IOException) {
                    Toast toast = Toast.makeText(Login.this, "Fallo en la conexion", Toast.LENGTH_SHORT);
                    Log.d("mensaje",t.getMessage());

                    toast.show();
                }
                else{
                    Toast.makeText(Login.this, "Error de conversión", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    public List<CodigoEvento> listaCodigos(){

        ArrayList<String> codes=new ArrayList<String>();
        Retrofit retrofit = Retro.get_retrofit();
        CodigosAPI codigosAPI = retrofit.create(CodigosAPI.class);
        Call<List<CodigoEvento>> call = codigosAPI.getCodigos();
        List<CodigoEvento> ce = null;
        call.enqueue(new Callback<List<CodigoEvento>>() {
            @Override
            public void onResponse(Call<List<CodigoEvento>> call, Response<List<CodigoEvento>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error "+response.code(),Toast.LENGTH_SHORT).show();

                }else {
                    List<CodigoEvento> ce = response.body();
                    File directory = getFilesDir();
                    File file = new File(directory,"codigos.csv");
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        //bw.write("Codigo,Descripcion");
                        bw.newLine();
                        for(int i=0;i<ce.size();i++){
                            bw.write(ce.get(i).getId_tipo_falla()+","+ce.get(i).getCodigo()+","+ce.get(i).getDescripcion());
                            bw.newLine();
                        }
                        Log.d("tag1","ARCHIVO ALMACENADO EN : "+file.getAbsolutePath());
                        bw.close();
                        fw.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<List<CodigoEvento>> call, Throwable t) {
                if(t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Fallo en la conexion", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error de conversión", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Collections.reverse(Arrays.asList(ce));

        return ce;

    }


}