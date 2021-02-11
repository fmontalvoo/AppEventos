package edu.ups.est.frank.appeventos.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import edu.ups.est.frank.appeventos.db.DBHandler;
import edu.ups.est.frank.appeventos.entidades.Usuario;
import edu.ups.est.frank.appeventos.tablas.DaoAmigos;
import edu.ups.est.frank.appeventos.tablas.DaoUsuarios;

/**
 * Created by frank on 19/07/17.
 */

public class ControladorUsuarios {

    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase database;

    public ControladorUsuarios(Context context){
        dbHandler = new DBHandler(context);
        database = dbHandler.getWritableDatabase();
    }

    public void conectar(){
        database = dbHandler.getWritableDatabase();
    }

    public void desconectar(){
        database.close();
    }

    public void ingresarUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put(DaoUsuarios.AtributosUsuario.NOMBRE, usuario.getUsu_nombre());
        values.put(DaoUsuarios.AtributosUsuario.APELLIDO, usuario.getUsu_apellido());
        values.put(DaoUsuarios.AtributosUsuario.CORREO, usuario.getUsu_correo());
        values.put(DaoUsuarios.AtributosUsuario.CLAVE, usuario.getUsu_clave());
        values.put(DaoUsuarios.AtributosUsuario.TELEFONO, usuario.getUsu_telefono());
        values.put(DaoUsuarios.AtributosUsuario.ELIMINADO, usuario.getUsu_eliminado());
        database.insert(DaoUsuarios.NOMBRE_TABLA, null, values);
    }

    public void actualizarUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put(DaoUsuarios.AtributosUsuario.NOMBRE, usuario.getUsu_nombre());
        values.put(DaoUsuarios.AtributosUsuario.APELLIDO, usuario.getUsu_apellido());
        values.put(DaoUsuarios.AtributosUsuario.CORREO, usuario.getUsu_correo());
        values.put(DaoUsuarios.AtributosUsuario.CLAVE, usuario.getUsu_clave());
        values.put(DaoUsuarios.AtributosUsuario.TELEFONO, usuario.getUsu_telefono());
        values.put(DaoUsuarios.AtributosUsuario.ELIMINADO, usuario.getUsu_eliminado());
        String where = DaoUsuarios.AtributosUsuario.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(usuario.getUsu_codigo())};
        database.update(DaoUsuarios.NOMBRE_TABLA, values, where, whereArgumentos);
    }

    public void eliminarUsuario(long id){
        String where = DaoUsuarios.AtributosUsuario.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        database.delete(DaoUsuarios.NOMBRE_TABLA, where, whereArgumentos);
    }

    public Usuario buscarUsuario(long id){
        Usuario usuario = new Usuario();
        String where = DaoUsuarios.AtributosUsuario.CODIGO + " = ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(DaoUsuarios.NOMBRE_TABLA, DaoUsuarios.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            usuario.setUsu_codigo(cursor.getInt(0));
            usuario.setUsu_nombre(cursor.getString(1));
            usuario.setUsu_apellido(cursor.getString(2));
            usuario.setUsu_correo(cursor.getString(3));
            usuario.setUsu_clave(cursor.getString(4));
            usuario.setUsu_telefono(cursor.getString(5));
            usuario.setUsu_eliminado(cursor.getString(6));
        }
        return usuario;
    }

    public Usuario buscarUsuario(String correo){
        Usuario usuario = new Usuario();
        String where = DaoUsuarios.AtributosUsuario.CORREO + " = ?";
        String whereArgumentos[] = new String[] {correo};
        Cursor cursor = database.query(DaoUsuarios.NOMBRE_TABLA, DaoUsuarios.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            usuario.setUsu_codigo(Integer.parseInt(cursor.getString(0)));
            usuario.setUsu_nombre(cursor.getString(1));
            usuario.setUsu_apellido(cursor.getString(2));
            usuario.setUsu_correo(cursor.getString(3));
            usuario.setUsu_clave(cursor.getString(4));
            usuario.setUsu_telefono(cursor.getString(5));
            usuario.setUsu_eliminado(cursor.getString(6));
        }
        return usuario;
    }

    public Usuario verificarUsuario(String correo, String clave){
        Usuario usuario = new Usuario();
        String where = DaoUsuarios.AtributosUsuario.CORREO + " = ? AND " + DaoUsuarios.AtributosUsuario.CLAVE + " = ?";
        String whereArgumentos[] = new String[] {correo, clave};
        Cursor cursor = database.query(DaoUsuarios.NOMBRE_TABLA, DaoUsuarios.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            if((cursor.getString(3).compareTo(correo)) == 0 && (cursor.getString(4).compareTo(clave)) == 0){
                if(cursor != null){
                    cursor.moveToFirst();
                    usuario.setUsu_codigo(Integer.parseInt(cursor.getString(0)));
                    usuario.setUsu_nombre(cursor.getString(1));
                    usuario.setUsu_apellido(cursor.getString(2));
                    usuario.setUsu_correo(cursor.getString(3));
                    usuario.setUsu_clave(cursor.getString(4));
                    usuario.setUsu_telefono(cursor.getString(5));
                    usuario.setUsu_eliminado(cursor.getString(6));
                }
                return usuario;
            }
        }
        return null;
    }

    public ArrayList<Usuario> listaUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        Cursor cursor = database.query(DaoUsuarios.NOMBRE_TABLA, DaoUsuarios.columnas, null, null, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Usuario usuario = new Usuario();
                usuario.setUsu_codigo(Integer.parseInt(cursor.getString(0)));
                usuario.setUsu_nombre(cursor.getString(1));
                usuario.setUsu_apellido(cursor.getString(2));
                usuario.setUsu_correo(cursor.getString(3));
                usuario.setUsu_clave(cursor.getString(4));
                usuario.setUsu_telefono(cursor.getString(5));
                usuario.setUsu_eliminado(cursor.getString(6));
                lista.add(usuario);
            }
        }
        return lista;
    }

    public ArrayList<Usuario> listaUsuarios(long id){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String where = DaoUsuarios.AtributosUsuario.CODIGO + " != ?";
        String whereArgumentos[] = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(DaoUsuarios.NOMBRE_TABLA, DaoUsuarios.columnas, where, whereArgumentos, null, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Usuario usuario = new Usuario();
                usuario.setUsu_codigo(Integer.parseInt(cursor.getString(0)));
                usuario.setUsu_nombre(cursor.getString(1));
                usuario.setUsu_apellido(cursor.getString(2));
                usuario.setUsu_correo(cursor.getString(3));
                usuario.setUsu_clave(cursor.getString(4));
                usuario.setUsu_telefono(cursor.getString(5));
                usuario.setUsu_eliminado(cursor.getString(6));
                lista.add(usuario);
            }
        }
        return lista;
    }

    public ArrayList<Usuario> listaUsuarios(String estado, long codigo){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String sql = "SELECT * FROM "+DaoUsuarios.NOMBRE_TABLA+", "+ DaoAmigos.NOMBRE_TABLA+" "+
                "WHERE "+DaoUsuarios.NOMBRE_TABLA+"."+DaoUsuarios.AtributosUsuario.CODIGO+" = "
                +DaoAmigos.NOMBRE_TABLA+"."+DaoAmigos.AtributosAmigo.USU_CODIGO_P+
                " AND "+DaoAmigos.NOMBRE_TABLA+"."+DaoAmigos.AtributosAmigo.USU_CODIGO_S+" = "+codigo+
                " AND "+DaoAmigos.NOMBRE_TABLA+"."+DaoAmigos.AtributosAmigo.ESTADO+" LIKE '"+estado+"'";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Usuario usuario = new Usuario();
                usuario.setUsu_codigo(Integer.parseInt(cursor.getString(0)));
                usuario.setUsu_nombre(cursor.getString(1));
                usuario.setUsu_apellido(cursor.getString(2));
                usuario.setUsu_telefono(cursor.getString(7));
                lista.add(usuario);
            }
        }
        return lista;
    }
}
