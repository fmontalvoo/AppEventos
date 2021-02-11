package edu.ups.est.frank.appeventos.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.adb.ControladorEventos;
import edu.ups.est.frank.appeventos.entidades.Evento;

public class EventosActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static final int REQUEST_CODE = 1;
    private Spinner spnCodigo;
    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtFecha;
    private EditText txtLatitud;
    private EditText txtLongitud;
    private Spinner spnEstado;
    private EditText txtRecordatorio;
    private Spinner spnAcceso;
    private Button btnMaps;
    private Button btnInvitar;
    private Button btnGuardar;
    private Button btnEliminar;
    private Button btnCancelar;
    private Button btnLimpiar;

    private ControladorEventos control;
    private Evento evento;
    private long codigo = 0;
    private List lista;
    private List list;
    private List list1;
    private boolean ban = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        control = new ControladorEventos(this);

        spnCodigo = (Spinner) findViewById(R.id.spnCodigo);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtLatitud = (EditText) findViewById(R.id.txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.txtLongitud);
        spnEstado = (Spinner) findViewById(R.id.spnEstado);
        txtRecordatorio = (EditText) findViewById(R.id.txtRecordatorio);
        spnAcceso = (Spinner) findViewById(R.id.spnAcceso);

        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(this);
        btnInvitar = (Button) findViewById(R.id.btnInvitar);
        btnInvitar.setOnClickListener(this);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(this);

        list = new ArrayList();
        list.add("");
        list.add("Activo");
        list.add("Cancelado");
        list.add("Postergado");
        list.add("Iniciado");
        list.add("Finalizado");
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adaptador);

        list1 = new ArrayList();
        list1.add("");
        list1.add("Publico");
        list1.add("Privado");
        ArrayAdapter adaptador1 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list1);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAcceso.setAdapter(adaptador1);

        listaCodigo();
        maps();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMaps:
                Intent intent = new Intent(this, MapsActivity.class);
                if(!spnCodigo.getSelectedItem().toString().isEmpty()) {
                    intent.putExtra("eve_codigo", (int) codigo);
                    startActivity(intent);
                }
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btnInvitar:
                Intent intent1 = new Intent(this, InvitarActivity.class);
                startActivity(intent1);
                break;

            case R.id.btnGuardar:
                    guardar();
                break;

            case R.id.btnEliminar:
                eliminar();
                break;

            case R.id.btnCancelar:
                Intent intent2 = new Intent(this, ListaActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.btnLimpiar:
                limpiar();
                break;
        }
    }

    public void guardar() {
        if(txtNombre.length() > 0 && txtDescripcion.length() > 0 && txtFecha.length() > 0
                && txtLatitud.length() > 0 && txtLongitud.length() > 0 && !spnEstado.getSelectedItem().toString().isEmpty()
                && txtRecordatorio.length() > 0 && !spnAcceso.getSelectedItem().toString().isEmpty()){
            control.conectar();
            Evento evento = new Evento();
            evento.setUsuario(LoginActivity.usu);
            evento.setEve_nombre(txtNombre.getText().toString());
            evento.setEve_descripcion(txtDescripcion.getText().toString());
            if(fechaValida())
                evento.setEve_fecha(txtFecha.getText().toString());
            else
                Toast.makeText(this, "Fecha NO Valida!!", Toast.LENGTH_SHORT).show();
            evento.setEve_latitud(Double.valueOf(txtLatitud.getText().toString()));
            evento.setEve_longitud(Double.valueOf(txtLongitud.getText().toString()));
            evento.setEve_estado(estado(spnEstado.getSelectedItem().toString()));
            evento.setEve_recordatorio(Integer.valueOf(txtRecordatorio.getText().toString()));
            evento.setEve_acceso(acceso(spnAcceso.getSelectedItem().toString()));
            if(spnCodigo.getSelectedItem().toString().isEmpty()) {
                control.ingresarEvento(evento);
                Toast.makeText(this, "Evento Guardado", Toast.LENGTH_SHORT).show();
            }else{
                evento.setEve_codigo((int) codigo);
                control.actualizarEvento(evento);
                Toast.makeText(this, "Evento Actualizado", Toast.LENGTH_SHORT).show();
            }
            control.desconectar();
        }else{
            Toast.makeText(this, "No Debe Dejar Ningun Campo Sin Llenar!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void listaCodigo(){
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

    public void eliminar(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Desea Eliminar el Evento " + txtNombre.getText())
                .setTitle("Eliminar Evento");
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                control.conectar();
                control.eliminarEvento(codigo);
                control.desconectar();
                limpiar();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

    public void cargarDatos(){
        codigo  = Long.parseLong(spnCodigo.getSelectedItem().toString());
        control.conectar();
        evento = control.buscarEvento(codigo);
        txtNombre.setText(evento.getEve_nombre());
        txtDescripcion.setText(evento.getEve_descripcion());
        txtFecha.setText(evento.getEve_fecha());
        txtLatitud.setText(String.valueOf(evento.getEve_latitud()));
        txtLongitud.setText(String.valueOf(evento.getEve_longitud()));
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i).toString().equals(estado(evento.getEve_estado()))){
                spnEstado.setSelection(i);
            }
        }
        txtRecordatorio.setText(String.valueOf(evento.getEve_recordatorio()));
        for (int i = 1; i < list1.size(); i++) {
            if(list1.get(i).toString().equals(acceso(evento.getEve_acceso()))){
                spnAcceso.setSelection(i);
            }
        }
        control.desconectar();
    }

    public void cargarData(){
        for (int i = 1; i < lista.size(); i++) {
            if(Long.valueOf(lista.get(i).toString()) == codigo){
                spnCodigo.setSelection(i);
            }
        }
        control.conectar();
        evento = control.buscarEvento(codigo);
        txtNombre.setText(evento.getEve_nombre());
        txtDescripcion.setText(evento.getEve_descripcion());
        txtFecha.setText(evento.getEve_fecha());
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i).toString().equals(estado(evento.getEve_estado()))){
                spnEstado.setSelection(i);
            }
        }
        txtRecordatorio.setText(String.valueOf(evento.getEve_recordatorio()));
        for (int i = 1; i < list1.size(); i++) {
            if(list1.get(i).toString().equals(acceso(evento.getEve_acceso()))){
                spnAcceso.setSelection(i);
            }
        }
        control.desconectar();
    }

    public void maps(){
        Intent intent = getIntent();
        Bundle datos = intent.getExtras();

        if(datos != null){
            txtLatitud.setText(""+datos.getDouble(("latitud")));
            txtLongitud.setText(""+datos.getDouble(("longitud")));
            codigo = datos.getInt("eve_codigo");
            if(codigo != -1) {
                ban = true;
                cargarData();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!spnCodigo.getSelectedItem().toString().isEmpty()) {
            if(ban) {
                cargarData();
                ban = false;
            } else
                cargarDatos();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void limpiar(){
        spnCodigo.setSelection(0);
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        spnEstado.setSelection(0);
        txtRecordatorio.setText("");
        spnAcceso.setSelection(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(REQUEST_CODE == requestCode){
            if(resultCode == RESULT_OK) {
                Bundle datos = data.getExtras();
                txtLatitud.setText(""+datos.getDouble(("latitud")));
                txtLongitud.setText(""+datos.getDouble(("longitud")));
            }
        }
    }

    public boolean fechaValida(){
        Date fecha = null;
        try {
            fecha = new SimpleDateFormat("dd/mm/yyyy").parse(txtFecha.getText().toString());
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String estado(String estado){
        String est = "";
        switch (estado){
            case "Activo":
                est = "A";
                break;
            case "Cancelado":
                est = "C";
                break;
            case "Postergado":
                est = "P";
                break;
            case "Iniciado":
                est = "I";
                break;
            case "Finalizado":
                est = "F";
                break;

            case "A":
                est = "Activo";
                break;
            case "C":
                est = "Cancelado";
                break;
            case "P":
                est = "Postergado";
                break;
            case "I":
                est = "Iniciado";
                break;
            case "F":
                est = "Finalizado";
                break;
        }
        return est;
    }

    public String acceso(String acceso) {
        String acc = "";
        switch (acceso){
            case "Publico":
                acc = "P";
                break;
            case "Privado":
                acc = "V";
                break;

            case "P":
                acc = "Publico";
                break;
            case "V":
                acc = "Privado";
                break;
        }
        return acc;
    }

}
