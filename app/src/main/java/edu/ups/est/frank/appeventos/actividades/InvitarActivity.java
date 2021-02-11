package edu.ups.est.frank.appeventos.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.adb.ControladorEventos;
import edu.ups.est.frank.appeventos.adb.ControladorInvitados;
import edu.ups.est.frank.appeventos.adb.ControladorUsuarios;
import edu.ups.est.frank.appeventos.entidades.Evento;
import edu.ups.est.frank.appeventos.entidades.Invitado;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class InvitarActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spnCodigo;
    private EditText txtNombre;
    private Spinner spnNombre;
    private Button btnInvitar;
    private Button btnCancelar;
    private ControladorEventos control;
    private Evento evento;
    private List lista;
    private long codigo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar);

        control = new ControladorEventos(this);

        btnInvitar = (Button) findViewById(R.id.btnInvitar);
        btnInvitar.setOnClickListener(this);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        spnCodigo = (Spinner) findViewById(R.id.spnCodigo);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        spnNombre = (Spinner) findViewById(R.id.spnNombre);
        listaCodigo_Eve();
        listaNombre();

    }

    public void listaCodigo_Eve(){
        lista = new ArrayList();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        control.conectar();
        eventos = control.listaEventos(LoginActivity.usu.getUsu_codigo());
        control.desconectar();
        lista.add("");
        for (int i = 0; i < eventos.size(); i++) {
            lista.add(eventos.get(i).getEve_codigo());
        }
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCodigo.setAdapter(adaptador);
        spnCodigo.setOnItemSelectedListener(this);
    }

    public void listaNombre(){
        List lista = new ArrayList();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usuarios = control.listaUsuarios(LoginActivity.usu.getUsu_codigo());
        control.desconectar();
        lista.add("");
        for (int i = 0; i < usuarios.size(); i++) {
            lista.add(usuarios.get(i).getUsu_codigo()+"    "+usuarios.get(i).getUsu_nombre());
        }
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNombre.setAdapter(adaptador);
        spnNombre.setOnItemSelectedListener(this);
    }

    public void cargarDatos(){
            codigo  = Long.parseLong(spnCodigo.getSelectedItem().toString());
            control.conectar();
            evento = control.buscarEvento(codigo);
            txtNombre.setText(evento.getEve_nombre());
            control.desconectar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInvitar:
                invitar();
                break;

            case R.id.btnCancelar:
                Intent intent = new Intent(this, EventosActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void invitar(){
        ControladorInvitados control = new ControladorInvitados(this);
        Invitado invitado = new Invitado();
        control.conectar();
        String codU[] = spnNombre.getSelectedItem().toString().split("  ");
        String codE[] = spnCodigo.getSelectedItem().toString().split("  ");
        Usuario usuario = new Usuario();
        Evento evento = new Evento();
        usuario.setUsu_codigo(Integer.valueOf(codU[0]));
        evento.setEve_codigo(Integer.valueOf(codE[0]));
        invitado.setUsuario(usuario);
        invitado.setEvento(evento);
        invitado.setInv_estado("I");
        control.ingreasarInvitado(invitado);
        Toast.makeText(this, "Invitacion Enviada", Toast.LENGTH_SHORT).show();
        control.desconectar();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!spnCodigo.getSelectedItem().toString().isEmpty()) {
                cargarDatos();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
