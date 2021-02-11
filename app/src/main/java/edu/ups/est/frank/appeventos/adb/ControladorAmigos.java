package edu.ups.est.frank.appeventos.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.ups.est.frank.appeventos.db.DBHandler;
import edu.ups.est.frank.appeventos.entidades.Amigo;
import edu.ups.est.frank.appeventos.entidades.Usuario;
import edu.ups.est.frank.appeventos.tablas.DaoAmigos;

/**
 * Created by frank on 19/07/17.
 */

public class ControladorAmigos {

    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase database;

    public ControladorAmigos(Context context){
        dbHandler = new DBHandler(context);
        database = dbHandler.getWritableDatabase();
    }

    public void conectar(){
        database = dbHandler.getWritableDatabase();
    }

    public void desconectar(){
        database.close();
    }

    public void ingresarAmigo(Amigo amigo){
        ContentValues values = new ContentValues();
        values.put(DaoAmigos.AtributosAmigo.USU_CODIGO_P, amigo.getUsuario_pr().getUsu_codigo());
        values.put(DaoAmigos.AtributosAmigo.USU_CODIGO_S, amigo.getUsuario_se().getUsu_codigo());
        values.put(DaoAmigos.AtributosAmigo.ESTADO, amigo.getAmi_estado());
        values.put(DaoAmigos.AtributosAmigo.ELIMINADO, amigo.getAmi_eliminado());
        database.insert(DaoAmigos.NOMBRE_TABLA, null, values);
    }

    public void actualizarAmigo(Amigo amigo){
        ContentValues values = new ContentValues();
        values.put(DaoAmigos.AtributosAmigo.USU_CODIGO_P, amigo.getUsuario_pr().getUsu_codigo());
        values.put(DaoAmigos.AtributosAmigo.USU_CODIGO_S, amigo.getUsuario_se().getUsu_codigo());
        values.put(DaoAmigos.AtributosAmigo.ESTADO, amigo.getAmi_estado());
        values.put(DaoAmigos.AtributosAmigo.ELIMINADO, amigo.getAmi_eliminado());
        String where = DaoAmigos.AtributosAmigo.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(amigo.getAmi_codigo())};
        database.update(DaoAmigos.NOMBRE_TABLA, values, where, whereArgumentos);
    }

    public void eliminarAmigo(long id){
        String where = DaoAmigos.AtributosAmigo.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        database.delete(DaoAmigos.NOMBRE_TABLA, where, whereArgumentos);
    }

    public ArrayList<Amigo> listaSolicitudes(String estado){
        ArrayList<Amigo> lista = new ArrayList<Amigo>();
        String where = DaoAmigos.AtributosAmigo.ESTADO + " = ?";
        String whereArgumentos[] = new String[] {estado};
        Cursor cursor = database.query(DaoAmigos.NOMBRE_TABLA, DaoAmigos.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Amigo amigo = new Amigo();
                Usuario u_p = new Usuario();
                Usuario u_s = new Usuario();
                amigo.setAmi_codigo(cursor.getInt(0));
                u_p.setUsu_codigo(cursor.getInt(1));
                u_s.setUsu_codigo(cursor.getInt(2));
                amigo.setUsuario_pr(u_p);
                amigo.setUsuario_se(u_s);
                amigo.setAmi_estado(cursor.getString(3));
                amigo.setAmi_eliminado(cursor.getString(4));
                lista.add(amigo);
            }
        }
        return lista;
    }

}
