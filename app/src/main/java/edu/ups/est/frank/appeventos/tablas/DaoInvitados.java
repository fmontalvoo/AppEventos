package edu.ups.est.frank.appeventos.tablas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by frank on 19/07/17.
 */

public class DaoInvitados {

    private SQLiteDatabase database;
    public static final String NOMBRE_TABLA = "INVITADOS";

    public DaoInvitados(SQLiteDatabase database){
        this.database = database;
    }

    public static class AtributosInvitado implements BaseColumns{
        public static final String CODIGO = "INV_CODIGO";
        public static final String USU_CODIGO = "USU_CODIGO";
        public static final String EVE_CODIGO = "EVE_CODIGO";
        public static final String ESTADO = "INV_ESTADO";
        public static final String ELIMINADO = "INV_ELIMINADO";
    }

    public static final String columnas[] = new String[] {AtributosInvitado.CODIGO, AtributosInvitado.USU_CODIGO,
            AtributosInvitado.EVE_CODIGO, AtributosInvitado.ESTADO, AtributosInvitado.ELIMINADO};

    public static final String CREAR_TABLA_INVITADOS = "create table if not exists " + NOMBRE_TABLA + " ( " +
            AtributosInvitado.CODIGO + " integer primary key autoincrement, " +
            AtributosInvitado.USU_CODIGO + " integer not null, " +
            AtributosInvitado.EVE_CODIGO + " integer not null, " +
            AtributosInvitado.ESTADO + " varchar(1), " +
            AtributosInvitado.ELIMINADO + " varchar(1), " +
            " foreign key ("+AtributosInvitado.USU_CODIGO+") references USUARIOS("+DaoUsuarios.AtributosUsuario.CODIGO+"), " +
            " foreign key ("+AtributosInvitado.EVE_CODIGO+") references EVENTOS("+DaoEventos.AtributosEvento.CODIGO+") " +
            " );";

}
