package edu.ups.est.frank.appeventos.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import edu.ups.est.frank.appeventos.adb.ControladorAmigos;
import edu.ups.est.frank.appeventos.adb.ControladorUsuarios;
import edu.ups.est.frank.appeventos.entidades.Amigo;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class AmigosActivity extends AppCompatActivity {

    private ListView lstAmigos;
    private ArrayAdapter adaptador;
    private List lista;
    private int opcion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        lstAmigos = (ListView) findViewById(R.id.lstAmigos);
        lstAmigos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String  itemValue    = (String) lstAmigos.getItemAtPosition(position);
                String codigo[] = itemValue.split("-");
                if(opcion == 1)
                    solicitud(Integer.parseInt(codigo[0]));
                if(opcion == 2)
                    eliminar(Integer.parseInt(codigo[0]), Integer.parseInt(codigo[2]));
                if(opcion == 3)
                    agregar(Integer.parseInt(codigo[0]), Integer.parseInt(codigo[2]));
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_amigos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnUsuarios:
                opcion = 1;
                listaUsuario();
                break;

            case R.id.mnAmigos:
                opcion = 2;
                listaAmigos();
                break;

            case R.id.mnSolicitudes:
                opcion = 3;
                listaSolicitudes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listaUsuario(){
        lista = new ArrayList();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usuarios = control.listaUsuarios(LoginActivity.usu.getUsu_codigo());
        control.desconectar();
        for (int i = 0; i < usuarios.size(); i++) {
            lista.add(usuarios.get(i).getUsu_codigo()+"-"+usuarios.get(i).getUsu_nombre());
        }
        adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAmigos.setAdapter(adaptador);
    }

    public void listaAmigos(){
        lista = new ArrayList();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usuarios = control.listaUsuarios("A", LoginActivity.usu.getUsu_codigo());
        control.desconectar();
        for (int i = 0; i < usuarios.size(); i++) {
            lista.add(usuarios.get(i).getUsu_codigo()+"-"+usuarios.get(i).getUsu_nombre()+"-"+usuarios.get(i).getUsu_telefono());
        }
        adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAmigos.setAdapter(adaptador);
    }

    public void listaSolicitudes(){
        lista = new ArrayList();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usuarios = control.listaUsuarios("P", LoginActivity.usu.getUsu_codigo());
        control.desconectar();
        for (int i = 0; i < usuarios.size(); i++) {
            lista.add(usuarios.get(i).getUsu_codigo()+"-"+usuarios.get(i).getUsu_nombre()+"-"+usuarios.get(i).getUsu_telefono());
        }
        adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAmigos.setAdapter(adaptador);
    }

    public void solicitud(final int codigo){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Agregar a Amigos")
                .setTitle("Desea Agregar a un Amigo Nuevo");
        alerta.setPositiveButton("Enviar Solicitud", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ControladorAmigos control = new ControladorAmigos(AmigosActivity.this);
                Amigo amigo = new Amigo();
                Usuario usuario = new Usuario();
                usuario.setUsu_codigo(codigo);
                control.conectar();
                amigo.setUsuario_pr(LoginActivity.usu);
                amigo.setUsuario_se(usuario);
                amigo.setAmi_estado("P");
                control.ingresarAmigo(amigo);
                control.desconectar();
                Toast.makeText(AmigosActivity.this, "Solicitud Enviada", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

    public void eliminar(final int codigo, final int ami_codigo){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Eliminar Amigos")
                .setTitle("Desea Eliminar a un Amigo");
        alerta.setPositiveButton("Eliminar Amigo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                actualizar(codigo, ami_codigo, "E");
                Toast.makeText(AmigosActivity.this, "Amigo Eliminado", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

    public void agregar(final int codigo, final int ami_codigo){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Agregar Amigos")
                .setTitle("Desea Agregar a un Amigo");
        alerta.setPositiveButton("Aceptar Solicitud", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                actualizar(codigo, ami_codigo, "A");
                Toast.makeText(AmigosActivity.this, "Amigo Agregado", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alerta.show();
    }


    public void actualizar(int codigo, int ami_codigo, String estado){
        ControladorAmigos control = new ControladorAmigos(AmigosActivity.this);
        Amigo amigo = new Amigo();
        Usuario usuario = new Usuario();
        usuario.setUsu_codigo(codigo);
        control.conectar();
        amigo.setAmi_codigo(ami_codigo);
        amigo.setUsuario_pr(LoginActivity.usu);
        amigo.setUsuario_se(usuario);
        amigo.setAmi_estado(estado);
        control.actualizarAmigo(amigo);
        control.desconectar();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}
