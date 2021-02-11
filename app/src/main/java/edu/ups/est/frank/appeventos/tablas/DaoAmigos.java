package edu.ups.est.frank.appeventos.tablas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by frank on 19/07/17.
 */

public class DaoAmigos {

    private SQLiteDatabase database;
    public static final String NOMBRE_TABLA = "AMIGOS";

    public DaoAmigos(SQLiteDatabase database){
        this.database = database;
    }

    public static class AtributosAmigo implements BaseColumns{
        public static final String CODIGO = "AMI_CODIGO";
        public static final String USU_CODIGO_P = "USU_CODIGO_PR";
        public static final String USU_CODIGO_S = "USU_CODIGO_SE";
        public static final String ESTADO = "AMI_ESTADO";
        public static final String ELIMINADO = "AMI_ELIMINADO";
    }

    public static final String columnas[] = new String[] {AtributosAmigo.CODIGO, AtributosAmigo.USU_CODIGO_P,
            AtributosAmigo.USU_CODIGO_S, AtributosAmigo.ESTADO, AtributosAmigo.ELIMINADO};

    public static final String CREAR_TABLA_AMIGOS = "create table if not exists " + NOMBRE_TABLA + " ( " +
            AtributosAmigo.CODIGO + " integer primary key autoincrement, " +
            AtributosAmigo.USU_CODIGO_P + " integer not null, " +
            AtributosAmigo.USU_CODIGO_S + " integer not null, " +
            AtributosAmigo.ESTADO + " varchar(1) not null, " +
            AtributosAmigo.ELIMINADO + " varchar(1), " +
            " foreign key ("+ AtributosAmigo.USU_CODIGO_P+") references USUARIOS("+DaoUsuarios.AtributosUsuario.CODIGO+"), " +
            " foreign key ("+ AtributosAmigo.USU_CODIGO_S+") references USUARIOS("+DaoUsuarios.AtributosUsuario.CODIGO+") " +
            " );";

}
