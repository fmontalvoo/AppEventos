package edu.ups.est.frank.appeventos.tablas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by frank on 19/07/17.
 */

public class DaoEventos {

    private SQLiteDatabase database;
    public static final String NOMBRE_TABLA = "EVENTOS";

    public DaoEventos(SQLiteDatabase database){
        this.database = database;
    }

    public static class AtributosEvento implements BaseColumns{
        public static final String CODIGO = "EVE_CODIGO";
        public static final String USU_CODIGO = "USU_CODIGO";
        public static final String NOMBRE = "EVE_NOMBRE";
        public static final String DESCRIPCION = "EVE_DESCRIPCION";
        public static final String FECHA = "EVE_FECHA";
        public static final String LATITUD = "EVE_LATITUD";
        public static final String LONGITUD = "EVE_LONGITUD";
        public static final String ESTADO = "EVE_ESTADO";
        public static final String RECORDATORIO = "EVE_RECORDATORIO";
        public static final String ACCESO = "EVE_ACCESO";
        public static final String ELIMINADO = "EVE_ELIMINADO";
    }

    public static final String columnas[] = new String[] {AtributosEvento.CODIGO, AtributosEvento.USU_CODIGO, AtributosEvento.NOMBRE,
            AtributosEvento.DESCRIPCION, AtributosEvento.FECHA, AtributosEvento.LATITUD, AtributosEvento.LONGITUD,
            AtributosEvento.ESTADO, AtributosEvento.RECORDATORIO, AtributosEvento.ACCESO, AtributosEvento.ELIMINADO};

    public static final String CREAR_TABLA_EVENTOS = "create table if not exists " + NOMBRE_TABLA + " ( " +
            AtributosEvento.CODIGO + " integer primary key autoincrement, " +
            AtributosEvento.USU_CODIGO + " integer not null, " +
            AtributosEvento.NOMBRE + " varchar(30) not null, " +
            AtributosEvento.DESCRIPCION + " varchar(100) not null, " +
            AtributosEvento.FECHA + " varchar(10) not null, " +
            AtributosEvento.LATITUD + " double not null, " +
            AtributosEvento.LONGITUD + " double not null, " +
            AtributosEvento.ESTADO + " varchar(1) not null, " +
            AtributosEvento.RECORDATORIO + " integer not null, " +
            AtributosEvento.ACCESO + " varchar(1) not null, " +
            AtributosEvento.ELIMINADO + " varchar(1), " +
            " foreign key("+AtributosEvento.USU_CODIGO+") references USUARIOS("+DaoUsuarios.AtributosUsuario.CODIGO+") " +
            " );";


}
