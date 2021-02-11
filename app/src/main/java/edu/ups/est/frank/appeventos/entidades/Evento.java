package edu.ups.est.frank.appeventos.entidades;

import java.util.Date;

/**
 * Created by frank on 19/07/17.
 */

public class Evento {

    private int eve_codigo;
    private Usuario usuario;
    private String eve_nombre;
    private String eve_descripcion;
    private String eve_fecha;
    private double eve_latitud;
    private double eve_longitud;
    private String eve_estado;
    private int eve_recordatorio;
    private String eve_acceso;
    private String eve_eliminado;

    public int getEve_codigo() {
        return eve_codigo;
    }

    public void setEve_codigo(int eve_codigo) {
        this.eve_codigo = eve_codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEve_nombre() {
        return eve_nombre;
    }

    public void setEve_nombre(String eve_nombre) {
        this.eve_nombre = eve_nombre;
    }

    public String getEve_descripcion() {
        return eve_descripcion;
    }

    public void setEve_descripcion(String eve_descripcion) {
        this.eve_descripcion = eve_descripcion;
    }

    public String getEve_fecha() {
        return eve_fecha;
    }

    public void setEve_fecha(String eve_fecha) {
        this.eve_fecha = eve_fecha;
    }

    public double getEve_latitud() {
        return eve_latitud;
    }

    public void setEve_latitud(double eve_latitud) {
        this.eve_latitud = eve_latitud;
    }

    public double getEve_longitud() {
        return eve_longitud;
    }

    public void setEve_longitud(double eve_longitud) {
        this.eve_longitud = eve_longitud;
    }

    public String getEve_estado() {
        return eve_estado;
    }

    public void setEve_estado(String eve_estado) {
        this.eve_estado = eve_estado;
    }

    public int getEve_recordatorio() {
        return eve_recordatorio;
    }

    public void setEve_recordatorio(int eve_recordatorio) {
        this.eve_recordatorio = eve_recordatorio;
    }

    public String getEve_acceso() {
        return eve_acceso;
    }

    public void setEve_acceso(String eve_acceso) {
        this.eve_acceso = eve_acceso;
    }

    public String getEve_eliminado() {
        return eve_eliminado;
    }

    public void setEve_eliminado(String eve_eliminado) {
        this.eve_eliminado = eve_eliminado;
    }


}
