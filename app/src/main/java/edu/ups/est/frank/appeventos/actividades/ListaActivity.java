package edu.ups.est.frank.appeventos.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.adb.ControladorEventos;
import edu.ups.est.frank.appeventos.adb.ControladorUsuarios;
import edu.ups.est.frank.appeventos.entidades.Evento;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class ListaActivity extends AppCompatActivity {

    private ListView lstLista;
    private ArrayAdapter adaptador;
    private List lista;
    private boolean acceso = true;

    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lstLista = (ListView) findViewById(R.id.lstLista);
        lstLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaActivity.this, DetalleActivity.class);
                String  itemValue    = (String) lstLista.getItemAtPosition(position);
                String codigo[] = itemValue.split("-");
                intent.putExtra("eve_codigo", Integer.valueOf(codigo[0]));
                if(!acceso)
                    intent.putExtra("inv_codigo", Integer.valueOf(codigo[2]));
                startActivity(intent);
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnCrear:
                Intent intent = new Intent(this, EventosActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.mnListarP:
                listaEventos("P");
                acceso = true;
                break;

            case R.id.mnListarV:
                listaEventos(LoginActivity.usu.getUsu_codigo(), "V");
                acceso = false;
                break;

            case R.id.mnListarM:
                listaEventos(LoginActivity.usu.getUsu_codigo(), "P");
                acceso = false;
                break;

            case R.id.mnAmigos:
                Intent intent1 = new Intent(this, AmigosActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.mnCerrar:
                cerrar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listaEventos(String acceso){
        lista = new ArrayList();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        ControladorEventos control = new ControladorEventos(this);
        control.conectar();
        eventos = control.listaEventos(acceso);
        control.desconectar();
        for (int i = 0; i < eventos.size(); i++) {
            lista.add(eventos.get(i).getEve_codigo()+"-"+eventos.get(i).getEve_nombre());
        }
        adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstLista.setAdapter(adaptador);
    }

    public void listaEventos(long codigo, String acceso){
        lista = new ArrayList();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        ControladorEventos control = new ControladorEventos(this);
        control.conectar();
        eventos = control.listaEventos_Inv(codigo, acceso);
        control.desconectar();
        for (int i = 0; i < eventos.size(); i++) {
            lista.add(eventos.get(i).getEve_codigo()+"-"+eventos.get(i).getEve_nombre()+"-"+eventos.get(i).getEve_recordatorio());
        }
        adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstLista.setAdapter(adaptador);
    }

    public void cerrar(){
        Toast.makeText(this,"Adios!!",Toast.LENGTH_LONG).show();
        SharedPreferences preferencias = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("estado",false);
        editor.commit();
        editor.getClass().getMethods();
        Intent intento = new Intent(this, LoginActivity.class);
        startActivity(intento);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

}
