package com.example.riff2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.riff2.interfaces.EventosAPI;
import com.example.riff2.models.Evento;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.EventosViewHolder> {

    private List<Evento> my_eventos,lista_eventos;
    private Context context;


    class EventosViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id, tv_calle, tv_estado, tv_altura;

        public EventosViewHolder(View itemView){
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_calle = itemView.findViewById(R.id.tv_calle);
            tv_altura = itemView.findViewById(R.id.tv_altura);
            tv_estado = itemView.findViewById(R.id.tv_estado);


        }

    }



    @NonNull
    @NotNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_evento,parent,false);


        return new EventosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventosViewHolder holder, int position) {

        setMy_eventos();

        List<Evento> lista = lista_eventos;
        holder.tv_id.setText(lista.get(position).getId_evento());
        holder.tv_calle.setText(lista.get(position).getCalle());
        holder.tv_altura.setText(lista.get(position).getNumero());
        holder.tv_estado.setText(lista.get(position).getDesc_estado());




    }

    @Override
    public int getItemCount() {

        setMy_eventos();

        return lista_eventos.size();
    }





    public void setMy_eventos() {
        String legajo = "604154";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.231:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EventosAPI eventosAPI = retrofit.create(EventosAPI.class);
        Call<List<Evento>> call = eventosAPI.find(legajo);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if(response.isSuccessful()){
                    lista_eventos = response.body();
                    EventosViewHolder eventosViewHolder ;

                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {

            }
        });



    }
}
