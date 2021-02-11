package edu.ups.est.frank.appeventos.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import edu.ups.est.frank.appeventos.adb.ControladorUsuarios;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spnCodigo;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtCorreo;
    private EditText txtClave;
    private EditText txtTelefono;
    private Button btnRegistrar;
    private Button btnEliminar;
    private Button btnCancelar;

    private final static int REQUEST_CODE = 1;
    private long codigo = 0;

    private  ControladorUsuarios control;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        control = new ControladorUsuarios(this);
        usuario = new Usuario();

        spnCodigo = (Spinner) findViewById(R.id.spnCodigo);
        List lista = new ArrayList();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usuarios = control.listaUsuarios();
        control.desconectar();
        lista.add("");
        for (int i = 0; i < usuarios.size(); i++) {
            lista.add(usuarios.get(i).getUsu_codigo());
        }
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCodigo.setAdapter(adaptador);
        spnCodigo.setOnItemSelectedListener(this);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                registrar();
                break;

            case R.id.btnCancelar:
                Intent intent = new Intent(this, LoginActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
                break;

            case R.id.btnEliminar:
                eliminar();
                break;
        }
    }

    public void registrar(){
        if(txtNombre.length() > 0 && txtApellido.length() > 0 && txtCorreo.length() > 0 && txtClave.length() > 0){
            control.conectar();
            usuario.setUsu_nombre(txtNombre.getText().toString());
            usuario.setUsu_apellido(txtApellido.getText().toString());
            usuario.setUsu_correo(txtCorreo.getText().toString());
            usuario.setUsu_telefono(txtTelefono.getText().toString());
            usuario.setUsu_clave(txtClave.getText().toString());
                if(spnCodigo.getSelectedItem().toString().isEmpty()){
                    Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    control.ingresarUsuario(usuario);
                } else {
                    usuario.setUsu_codigo((int)codigo);
                    Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                    control.actualizarUsuario(usuario);
                }
                control.desconectar();
                limpiar();
            }else{
                Toast.makeText(this, "No Debe Dejar Ningun Campo Sin Llenar!!", Toast.LENGTH_SHORT).show();
            }
    }

    public void eliminar(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Desea Eliminar al Usuario " + txtNombre.getText())
                .setTitle("Eliminar Usuario");
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                control.conectar();
                control.eliminarUsuario(codigo);
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

    public void limpiar(){
        spnCodigo.setSelection(0);
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtClave.setText("");
        txtTelefono.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!spnCodigo.getSelectedItem().toString().isEmpty()) {
            codigo  = Long.parseLong(spnCodigo.getSelectedItem().toString());
            control.conectar();
            usuario = control.buscarUsuario(codigo);
            txtNombre.setText(usuario.getUsu_nombre());
            txtApellido.setText(usuario.getUsu_apellido());
            txtCorreo.setText(usuario.getUsu_correo());
            txtClave.setText(usuario.getUsu_clave());
            txtTelefono.setText(usuario.getUsu_telefono());
            control.desconectar();
        }else {
        limpiar();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
