package com.example.riff2.models;

public class Evento {

    private String legajo_tecnico,calle,numero,piso,depto,comentario,fecha_creacion;
    private double latitud,longitud;
    private int id_tipo_falla;
    private int id_evento;

    public String getDesc_estado() {
        return desc_estado;
    }

    public void setDesc_estado(String desc_estado) {
        this.desc_estado = desc_estado;
    }

    private String desc_estado;

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public int getId_tipo_falla() {
        return id_tipo_falla;
    }

    public void setId_tipo_falla(int id_tipo_falla) {
        this.id_tipo_falla = id_tipo_falla;
    }


    public void setLegajo_tecnico(String legajo_tecnico) {
        this.legajo_tecnico = legajo_tecnico;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }



    public String getLegajo_tecnico() {
        return legajo_tecnico;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getPiso() {
        return piso;
    }

    public String getDepto() {
        return depto;
    }

    public String getComentario() {
        return comentario;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
