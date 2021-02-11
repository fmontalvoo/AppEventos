package edu.ups.est.frank.appeventos.entidades;

/**
 * Created by frank on 19/07/17.
 */

public class Usuario {

    private int usu_codigo;
    private String usu_nombre;
    private String usu_apellido;
    private String usu_correo;
    private String usu_clave;
    private String usu_telefono;
    private String usu_eliminado;

    public int getUsu_codigo() {
        return usu_codigo;
    }

    public void setUsu_codigo(int usu_codigo) {
        this.usu_codigo = usu_codigo;
    }

    public String getUsu_nombre() {
        return usu_nombre;
    }

    public void setUsu_nombre(String usu_nombre) {
        this.usu_nombre = usu_nombre;
    }

    public String getUsu_apellido() {
        return usu_apellido;
    }

    public void setUsu_apellido(String usu_apellido) {
        this.usu_apellido = usu_apellido;
    }

    public String getUsu_correo() {
        return usu_correo;
    }

    public void setUsu_correo(String usu_correo) {
        this.usu_correo = usu_correo;
    }

    public String getUsu_clave() {
        return usu_clave;
    }

    public void setUsu_clave(String usu_clave) {
        this.usu_clave = usu_clave;
    }

    public String getUsu_telefono() {
        return usu_telefono;
    }

    public void setUsu_telefono(String usu_telefono) {
        this.usu_telefono = usu_telefono;
    }

    public String getUsu_eliminado() {
        return usu_eliminado;
    }

    public void setUsu_eliminado(String usu_eliminado) {
        this.usu_eliminado = usu_eliminado;
    }
}
