package com.example.riff2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.riff2.models.Evento;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.EventosViewHolder> {

    Context context;
    List<Evento> eventList;

    public RecyclerAdapter(Context context,List<Evento> eventList){

        this.context = context;
        this.eventList = eventList;
    }

    public void setEventList(List<Evento> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }



    class EventosViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id,tv_calle,tv_altura,tv_estado,tv_fecha;

        public EventosViewHolder(View itemView){
            super(itemView);

            tv_id = (TextView) itemView.findViewById(R.id.mytv_id);
            tv_calle = (TextView) itemView.findViewById(R.id.tv_calle);
            tv_altura = (TextView) itemView.findViewById(R.id.tv_altura);
            tv_estado = (TextView) itemView.findViewById(R.id.tv_estado);
            tv_fecha = (TextView)  itemView.findViewById(R.id.tv_fecha);

        }

    }



    @NonNull
    @NotNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new RecyclerAdapter.EventosViewHolder(LayoutInflater.from(context).inflate(R.layout.detalle_evento,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventosViewHolder holder, int position) {

        holder.tv_id.setText(String.valueOf(eventList.get(position).getId_evento()));
        holder.tv_fecha.setText(eventList.get(position).getFecha_creacion().toString());
        holder.tv_calle.setText(eventList.get(position).getCalle().toString());
        holder.tv_altura.setText(eventList.get(position).getNumero().toString());
        holder.tv_estado.setText(eventList.get(position).getDesc_estado().toString());

    }

    @Override
    public int getItemCount() {

        if(eventList != null){
            return eventList.size();
        }
        return 0;
    }

}



