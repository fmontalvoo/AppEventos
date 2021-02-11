package edu.ups.est.frank.appeventos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ups.est.frank.appeventos.tablas.DaoAmigos;
import edu.ups.est.frank.appeventos.tablas.DaoEventos;
import edu.ups.est.frank.appeventos.tablas.DaoInvitados;
import edu.ups.est.frank.appeventos.tablas.DaoUsuarios;

/**
 * Created by frank on 19/07/17.
 */

public class DBHandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Eventos.db";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DaoUsuarios.CREAR_TABLA_USUARIOS);
        db.execSQL(DaoEventos.CREAR_TABLA_EVENTOS);
        db.execSQL(DaoInvitados.CREAR_TABLA_INVITADOS);
        db.execSQL(DaoAmigos.CREAR_TABLA_AMIGOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist "+ DaoEventos.NOMBRE_TABLA);
        db.execSQL("drop table if exist "+ DaoInvitados.NOMBRE_TABLA);
        db.execSQL("drop table if exist "+ DaoUsuarios.NOMBRE_TABLA);
        db.execSQL("drop table if exist "+ DaoAmigos.NOMBRE_TABLA);
    }

}
