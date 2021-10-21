package com.example.riff2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.riff2.interfaces.EventosAPI;
import com.example.riff2.models.Evento;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;



public class Nuevo_Evento extends Fragment implements View.OnClickListener{

    ImageButton bt_tipo_evento, bt_direccion, bt_ubicacion, bt_comentario;
    LocationManager locationManager;
    EditText pt_longitud,pt_latitud,pt_tipo_evento,pt_calle,pt_altura,pt_piso,pt_depto,pt_comentario;
    Button bt_enviar;
    Evento evento;
    int id_evento;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nuevo_evento, container, false);

        bt_tipo_evento = v.findViewById(R.id.bt_tipo_evento);
        bt_direccion = v.findViewById(R.id.bt_direccion);
        bt_ubicacion = v.findViewById(R.id.bt_ubicacion);
        bt_comentario = v.findViewById(R.id.bt_comentario);
        bt_tipo_evento.setOnClickListener(this);
        bt_direccion.setOnClickListener(this);
        bt_ubicacion.setOnClickListener(this);
        bt_comentario.setOnClickListener(this);

        pt_latitud = v.findViewById(R.id.et_latitud);
        pt_longitud = v.findViewById(R.id.et_longitud);
        pt_tipo_evento = v.findViewById(R.id.et_tipo_evento);
        pt_calle = v.findViewById(R.id.et_calle);
        pt_altura = v.findViewById(R.id.et_altura);
        pt_piso = v.findViewById(R.id.et_piso);
        pt_depto = v.findViewById(R.id.et_depto);
        pt_comentario = v.findViewById(R.id.et_comentario);
        bt_enviar = v.findViewById(R.id.bt_enviar);
        bt_enviar.setOnClickListener(this);






        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_tipo_evento:

                // creo el dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("TIPO DE EVENTO");


                //creo adaptador
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listaCodigos());
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String seleccion = adapter.getItem(which);
                        String[] valor = seleccion.split("-");
                        Toast.makeText(getContext(),"Seleccionó: "+valor[0],Toast.LENGTH_LONG).show();
                        pt_tipo_evento.setText(valor[1]+"-"+valor[2]);
                        id_evento = Integer.parseInt(valor[0]);
                        Log.d("id_evento",String.valueOf(id_evento));
                    }
                });
                //builder.show();
                AlertDialog dialog = builder.create();
                dialog.show();



                break;
            case R.id.bt_direccion:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                final View vista = getLayoutInflater().inflate(R.layout.custom_dialog_direccion,null);

                builder1.setView(vista);
                builder1.setTitle("DIRECCION DEL EVENTO");
                EditText et_calle,et_altura,et_piso,et_depto;
                et_calle = vista.findViewById(R.id.et_calle);
                et_altura = vista.findViewById(R.id.et_altura);
                et_piso = vista.findViewById(R.id.et_piso);
                et_depto = vista.findViewById(R.id.et_depto);
                et_calle.requestFocus();
                builder1.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                });
                builder1.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        boolean listo = false;
                        if(et_calle.getText().toString().isEmpty() || et_altura.getText().toString().isEmpty()){
                            Toast.makeText(getContext(),"Calle y Altura son campos obligatorios",Toast.LENGTH_LONG).show();
                        }else{
                            if(et_piso.getText().toString().isEmpty()) {
                                et_piso.setText("-");
                            }
                            if(et_depto.getText().toString().isEmpty()){
                                et_depto.setText("-");
                            }
                            pt_calle.setText(et_calle.getText());
                            pt_altura.setText(et_altura.getText());
                            pt_piso.setText(et_piso.getText());
                            pt_depto.setText(et_depto.getText());
                            listo = true;
                        }
                        if(listo){
                            dialog1.dismiss();
                        }

                    }
                });

                break;

            case R.id.bt_ubicacion:

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                            1000
                    );
                }

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(locationManager!=null){

                    pt_latitud.setText(String.valueOf(location.getLatitude()));
                    pt_longitud.setText(String.valueOf(location.getLongitude()));

                }

                break;

            case R.id.bt_comentario:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                builder2.setTitle("COMENTARIO");
                final EditText comment = new EditText(getContext());

                builder2.setView(comment);
                comment.requestFocus();
                builder2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        pt_comentario.setText(comment.getText().toString());
                    }
                });
                builder2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder2.show();

                break;

            case R.id.bt_enviar:

                evento = new Evento();
                Calendar calendar = Calendar.getInstance();
                String fecha_creacion = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE);
                evento.setFecha_creacion(fecha_creacion);
                SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String legajo_tecnico = preferences.getString("legajo","xxxxxx");
                evento.setLegajo_tecnico(legajo_tecnico);


                if(!pt_tipo_evento.getText().toString().isEmpty()){

                    evento.setId_tipo_falla(id_evento);

                    if (!pt_calle.getText().toString().isEmpty()){
                        evento.setCalle(pt_calle.getText().toString().trim());
                        evento.setNumero(pt_altura.getText().toString().trim());
                        evento.setPiso(pt_piso.getText().toString().trim());
                        evento.setDepto(pt_depto.getText().toString().trim());

                        if(!pt_latitud.getText().toString().equals("")){

                            double lat = Double.parseDouble(pt_latitud.getText().toString());
                            double longi = Double.parseDouble(pt_longitud.getText().toString());

                            evento.setLatitud(lat);
                            evento.setLongitud(longi);

                            if(!pt_comentario.getText().toString().isEmpty()){

                                evento.setComentario(pt_comentario.getText().toString());


                                postEv(evento);


                            }else {
                                Toast.makeText(getContext(),"Ingrese un comentario",Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Toast.makeText(getContext(), "Ingrese ubicación", Toast.LENGTH_LONG).show();
                        }

                    } else{

                        Toast.makeText(getContext(),"Ingrese dirección ",Toast.LENGTH_LONG).show();

                    }


                }else{

                    Toast.makeText(getContext(),"Ingrese tipo de evento",Toast.LENGTH_LONG).show();

                }

                break;
        }

    }

    public ArrayList<String> listaCodigos(){
        BufferedReader br = null;
        ArrayList<String> datos = new ArrayList<>();
        String linea;
        try {
            br = new BufferedReader(new FileReader("/data/user/0/com.example.riff2/files/codigos.csv"));

            while ((linea = br.readLine())!=null){
                int i = 0;
                String val= linea.replace( ",","-");
                datos.add(i,val);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(datos);

        return datos;


    }

    public void postEv(Evento evento){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.231:5000/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        EventosAPI eventosAPI = retrofit.create(EventosAPI.class);
        Call<Evento> call = eventosAPI.postEvento(evento);
        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                try {
                    if (response.isSuccessful()) {
                        Evento ev = response.body();
                        Toast.makeText(getContext(), "EVENTO ENVIADO", Toast.LENGTH_LONG).show();
                        pt_tipo_evento.setText("");
                        pt_calle.setText("");
                        pt_altura.setText("");
                        pt_piso.setText("");
                        pt_depto.setText("");
                        pt_latitud.setText("");
                        pt_longitud.setText("");
                        pt_comentario.setText("");
                        pt_tipo_evento.setHint("TIPO DE EVENTO");
                        pt_calle.setHint("CALLE");
                        pt_altura.setHint("ALTURA");
                        pt_piso.setHint("PISO");
                        pt_depto.setHint("DEPTO");
                        pt_latitud.setHint("LATITUD");
                        pt_longitud.setHint("LONGITUD");
                        pt_comentario.setHint("COMENTARIO");

                    } else {
                        Toast.makeText(getContext(), "ERROR, INTENTE MAS TARDE " + response.code(), Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "ERROR, INTENTE MAS TARDE " + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                Toast.makeText(getContext(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


}
