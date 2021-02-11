package edu.ups.est.frank.appeventos.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.ups.est.frank.appeventos.actividades.LoginActivity;
import edu.ups.est.frank.appeventos.db.DBHandler;
import edu.ups.est.frank.appeventos.entidades.Evento;
import edu.ups.est.frank.appeventos.tablas.DaoEventos;
import edu.ups.est.frank.appeventos.tablas.DaoInvitados;

/**
 * Created by frank on 19/07/17.
 */

public class ControladorEventos {

    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase database;

    public ControladorEventos(Context context){
        dbHandler = new DBHandler(context);
        database = dbHandler.getWritableDatabase();
    }

    public void conectar(){
        database = dbHandler.getWritableDatabase();
    }

    public void desconectar(){
        database.close();
    }

    public void ingresarEvento(Evento evento){
        ContentValues values = new ContentValues();
        values.put(DaoEventos.AtributosEvento.USU_CODIGO, evento.getUsuario().getUsu_codigo());
        values.put(DaoEventos.AtributosEvento.NOMBRE, evento.getEve_nombre());
        values.put(DaoEventos.AtributosEvento.DESCRIPCION, evento.getEve_descripcion());
        values.put(DaoEventos.AtributosEvento.FECHA, String.valueOf(evento.getEve_fecha()));
        values.put(DaoEventos.AtributosEvento.LATITUD, evento.getEve_latitud());
        values.put(DaoEventos.AtributosEvento.LONGITUD, evento.getEve_longitud());
        values.put(DaoEventos.AtributosEvento.ESTADO, evento.getEve_estado());
        values.put(DaoEventos.AtributosEvento.RECORDATORIO, evento.getEve_recordatorio());
        values.put(DaoEventos.AtributosEvento.ACCESO, evento.getEve_acceso());
        database.insert(DaoEventos.NOMBRE_TABLA, null, values);
    }

    public void actualizarEvento(Evento evento){
        ContentValues values = new ContentValues();
        values.put(DaoEventos.AtributosEvento.USU_CODIGO, evento.getUsuario().getUsu_codigo());
        values.put(DaoEventos.AtributosEvento.NOMBRE, evento.getEve_nombre());
        values.put(DaoEventos.AtributosEvento.DESCRIPCION, evento.getEve_descripcion());
        values.put(DaoEventos.AtributosEvento.FECHA, String.valueOf(evento.getEve_fecha()));
        values.put(DaoEventos.AtributosEvento.LATITUD, evento.getEve_latitud());
        values.put(DaoEventos.AtributosEvento.LONGITUD, evento.getEve_longitud());
        values.put(DaoEventos.AtributosEvento.ESTADO, evento.getEve_estado());
        values.put(DaoEventos.AtributosEvento.RECORDATORIO, evento.getEve_recordatorio());
        values.put(DaoEventos.AtributosEvento.ACCESO, evento.getEve_acceso());
        String where = DaoEventos.AtributosEvento.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(evento.getEve_codigo())};
        database.update(DaoEventos.NOMBRE_TABLA, values, where, whereArgumentos);
    }

    public Evento buscarEvento(long id) {
        Evento evento = new Evento();
        String where = DaoEventos.AtributosEvento.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(DaoEventos.NOMBRE_TABLA, DaoEventos.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            evento.setEve_codigo(cursor.getInt(0));
            evento.setEve_nombre(cursor.getString(2));
            evento.setEve_descripcion(cursor.getString(3));
            evento.setEve_fecha(cursor.getString(4));
            evento.setEve_latitud(cursor.getDouble(5));
            evento.setEve_longitud(cursor.getDouble(6));
            evento.setEve_estado(cursor.getString(7));
            evento.setEve_recordatorio(cursor.getInt(8));
            evento.setEve_acceso(cursor.getString(9));
        }
        return evento;
    }

    public void eliminarEvento(long id){
        String where = DaoEventos.AtributosEvento.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        database.delete(DaoEventos.NOMBRE_TABLA, where, whereArgumentos);
    }

    public ArrayList<Evento> listaEventos(String acceso){
        ArrayList<Evento> lista = new ArrayList<Evento>();
        String where = DaoEventos.AtributosEvento.ACCESO + " = ?";
        String whereArgumentos[] = new String[] {acceso};
        Cursor cursor = database.query(DaoEventos.NOMBRE_TABLA, DaoEventos.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Evento evento = new Evento();
                evento.setEve_codigo(cursor.getInt(0));
                evento.setEve_nombre(cursor.getString(2));
                evento.setEve_descripcion(cursor.getString(3));
                evento.setEve_fecha(cursor.getString(4));
                evento.setEve_latitud(cursor.getDouble(5));
                evento.setEve_longitud(cursor.getDouble(6));
                evento.setEve_estado(cursor.getString(7));
                evento.setEve_recordatorio(cursor.getInt(8));
                evento.setEve_acceso(cursor.getString(9));
                lista.add(evento);
            }
        }
        return lista;
    }


    public ArrayList<Evento> listaEventos_Inv(long codigo, String acceso){
        ArrayList<Evento> lista = new ArrayList<Evento>();
        String sql = "SELECT * FROM "+DaoEventos.NOMBRE_TABLA+", "+DaoInvitados.NOMBRE_TABLA+" "+
                "WHERE "+DaoInvitados.NOMBRE_TABLA+"."+DaoEventos.AtributosEvento.CODIGO+" = "
                +DaoEventos.NOMBRE_TABLA+"."+DaoInvitados.AtributosInvitado.EVE_CODIGO+" " +
                "AND "+DaoEventos.NOMBRE_TABLA+"."+DaoEventos.AtributosEvento.ACCESO +" LIKE '"+acceso+"' AND "
                +DaoInvitados.NOMBRE_TABLA+"."+DaoInvitados.AtributosInvitado.USU_CODIGO + " = "+codigo+";";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Evento evento = new Evento();
                evento.setEve_codigo(cursor.getInt(0));
                evento.setEve_nombre(cursor.getString(2));
                evento.setEve_recordatorio(cursor.getInt(11));
                lista.add(evento);
            }
        }
        return lista;
    }

    public ArrayList<Evento> listaEventos(long id){
        ArrayList<Evento> lista = new ArrayList<Evento>();
        String where = DaoEventos.AtributosEvento.USU_CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(DaoEventos.NOMBRE_TABLA, DaoEventos.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Evento evento = new Evento();
                evento.setEve_codigo(cursor.getInt(0));
                evento.setEve_nombre(cursor.getString(2));
                evento.setEve_descripcion(cursor.getString(3));
                evento.setEve_fecha(cursor.getString(4));
                evento.setEve_latitud(cursor.getDouble(5));
                evento.setEve_longitud(cursor.getDouble(6));
                evento.setEve_estado(cursor.getString(7));
                evento.setEve_recordatorio(cursor.getInt(8));
                evento.setEve_acceso(cursor.getString(9));
                lista.add(evento);
            }
        }
        return lista;
    }


}