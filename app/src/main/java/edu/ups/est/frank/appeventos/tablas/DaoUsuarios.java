package edu.ups.est.frank.appeventos.tablas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by frank on 19/07/17.
 */

public class DaoUsuarios {

    private SQLiteDatabase database;
    public static final String NOMBRE_TABLA = "USUARIOS";

    public DaoUsuarios(SQLiteDatabase database){
        this.database = database;
    }

    public static class AtributosUsuario implements BaseColumns{
        public static final String CODIGO = "USU_CODIGO";
        public static final String NOMBRE = "USU_NOMBRE";
        public static final String APELLIDO = "USU_APELLIDO";
        public static final String CORREO = "USU_CORREO";
        public static final String CLAVE = "USU_CLAVE";
        public static final String TELEFONO = "USU_TELEFONO";
        public static final String ELIMINADO = "USU_ELIMINADO";
    }

    public static final String columnas[] = new String[] {AtributosUsuario.CODIGO, AtributosUsuario.NOMBRE, AtributosUsuario.APELLIDO,
            AtributosUsuario.CORREO, AtributosUsuario.CLAVE, AtributosUsuario.TELEFONO, AtributosUsuario.ELIMINADO};

    public static final String CREAR_TABLA_USUARIOS = "create table if not exists " + NOMBRE_TABLA + " ( " +
            AtributosUsuario.CODIGO + " integer primary key autoincrement, " +
            AtributosUsuario.NOMBRE + " varchar(30) not null, " +
            AtributosUsuario.APELLIDO + " varchar(30) not null, " +
            AtributosUsuario.CORREO + " varchar(30) not null, " +
            AtributosUsuario.CLAVE + " varchar(15) not null, " +
            AtributosUsuario.TELEFONO + " varchar(10) not null, " +
            AtributosUsuario.ELIMINADO + " varchar(1) " +
            " );";

}
