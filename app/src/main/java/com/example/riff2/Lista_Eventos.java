package com.example.riff2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.riff2.interfaces.EventosAPI;
import com.example.riff2.models.Evento;
import com.example.riff2.models.Retro;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Lista_Eventos extends Fragment {

    RecyclerView recyclerView;
    View view;
    RecyclerAdapter recyclerAdapter;
    List<Evento> eventList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lista_eventos, container, false);
        recyclerView = view.findViewById(R.id.lista_eventos);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        recyclerAdapter = new RecyclerAdapter(getContext(),eventList);
        recyclerView.setAdapter(recyclerAdapter);

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        String legajo = preferences.getString("legajo","");
        Retrofit retrofit = Retro.get_retrofit();
        EventosAPI eventosAPI = retrofit.create(EventosAPI.class);
        Call<List<Evento>> call = eventosAPI.find(legajo);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                eventList = response.body();
                Collections.sort(eventList, new Comparator<Evento>() {
                    @Override
                    public int compare(Evento o1, Evento o2) {
                        return new Integer(o2.getId_evento()).compareTo(new Integer(o1.getId_evento()));
                    }
                });
                recyclerAdapter.setEventList(eventList);
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {

            }
        });





        return view;
    }


}
