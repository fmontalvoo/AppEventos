package edu.ups.est.frank.appeventos.entidades;

/**
 * Created by frank on 19/07/17.
 */

public class Invitado {

    private int inv_codigo;
    private Usuario usuario;
    private Evento evento;
    private String inv_estado;
    private String inv_eliminado;

    public int getInv_codigo() {
        return inv_codigo;
    }

    public void setInv_codigo(int inv_codigo) {
        this.inv_codigo = inv_codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getInv_estado() {
        return inv_estado;
    }

    public void setInv_estado(String inv_estado) {
        this.inv_estado = inv_estado;
    }

    public String getInv_eliminado() {
        return inv_eliminado;
    }

    public void setInv_eliminado(String inv_eliminado) {
        this.inv_eliminado = inv_eliminado;
    }
}
