package edu.ups.est.frank.appeventos.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ups.est.frank.appeventos.db.DBHandler;
import edu.ups.est.frank.appeventos.entidades.Invitado;
import edu.ups.est.frank.appeventos.tablas.DaoInvitados;

/**
 * Created by frank on 19/07/17.
 */

public class ControladorInvitados {

    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase database;

    public ControladorInvitados(Context context){
        dbHandler = new DBHandler(context);
        database = dbHandler.getWritableDatabase();
    }

    public void conectar(){
        database = dbHandler.getWritableDatabase();
    }

    public void desconectar(){
        database.close();
    }

    public void ingreasarInvitado(Invitado invitado){
        ContentValues values = new ContentValues();
        values.put(DaoInvitados.AtributosInvitado.USU_CODIGO, invitado.getUsuario().getUsu_codigo());
        values.put(DaoInvitados.AtributosInvitado.EVE_CODIGO, invitado.getEvento().getEve_codigo());
        values.put(DaoInvitados.AtributosInvitado.ESTADO, invitado.getInv_estado());
        values.put(DaoInvitados.AtributosInvitado.ELIMINADO, invitado.getInv_eliminado());
        database.insert(DaoInvitados.NOMBRE_TABLA, null, values);
    }

    public void actualizarInvitado(Invitado invitado){
        ContentValues values = new ContentValues();
        values.put(DaoInvitados.AtributosInvitado.USU_CODIGO, invitado.getUsuario().getUsu_codigo());
        values.put(DaoInvitados.AtributosInvitado.EVE_CODIGO, invitado.getEvento().getEve_codigo());
        values.put(DaoInvitados.AtributosInvitado.ESTADO, invitado.getInv_estado());
        values.put(DaoInvitados.AtributosInvitado.ELIMINADO, invitado.getInv_eliminado());
        String where = DaoInvitados.AtributosInvitado.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(invitado.getInv_codigo())};
        database.update(DaoInvitados.NOMBRE_TABLA, values, where, whereArgumentos);
    }

    public void eliminarEvento(long id){
        String where = DaoInvitados.AtributosInvitado.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        database.delete(DaoInvitados.NOMBRE_TABLA, where, whereArgumentos);
    }

}
