package edu.ups.est.frank.appeventos.entidades;

/**
 * Created by frank on 19/07/17.
 */

public class Amigo {

    private int ami_codigo;
    private Usuario usuario_pr;
    private Usuario usuario_se;
    private String ami_estado;
    private  String ami_eliminado;

    public int getAmi_codigo() {
        return ami_codigo;
    }

    public void setAmi_codigo(int ami_codigo) {
        this.ami_codigo = ami_codigo;
    }

    public Usuario getUsuario_pr() {
        return usuario_pr;
    }

    public void setUsuario_pr(Usuario usuario_pr) {
        this.usuario_pr = usuario_pr;
    }

    public Usuario getUsuario_se() {
        return usuario_se;
    }

    public void setUsuario_se(Usuario usuario_se) {
        this.usuario_se = usuario_se;
    }

    public String getAmi_estado() {
        return ami_estado;
    }

    public void setAmi_estado(String ami_estado) {
        this.ami_estado = ami_estado;
    }

    public String getAmi_eliminado() {
        return ami_eliminado;
    }

    public void setAmi_eliminado(String ami_eliminado) {
        this.ami_eliminado = ami_eliminado;
    }
}
