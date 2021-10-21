package com.example.riff2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.riff2.interfaces.EventosAPI;
import com.example.riff2.models.Evento;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lista_Eventos extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Evento> lista_eventos;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lista_eventos, container, false);
        recyclerView = view.findViewById(R.id.lista_eventos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Context context = getContext();
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    public List<Evento> getMy_eventos() {

        //SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String legajo = "604154";//preferences.getString("legajo","000000");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.231:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EventosAPI eventosAPI = retrofit.create(EventosAPI.class);
        Call<List<Evento>> call = eventosAPI.find(legajo);
        System.out.println(call.toString());
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                try {
                    if (response.isSuccessful()) {
                        lista_eventos = response.body();
                        for (Evento e : lista_eventos) {

                            System.out.println(e.getId_evento() + "-->" + e.getCalle() + "-->" + e.getNumero() + "-->" + e.getDesc_estado());

                        }
                    } else {

                        System.out.println("Error: " + response.code());
                    }
                }catch (Exception ex){
                    Log.d("Excepcion","Excepcion:"+ex.getMessage());
                }


            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {

                System.out.println("Error on Failure: "+t.getMessage());

            }
        });

        return lista_eventos;
    }


}
