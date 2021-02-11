package edu.ups.est.frank.appeventos.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.adb.ControladorUsuarios;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtCorreo;
    private EditText txtClave;
    private CheckBox checkRecordar;
    private Button btnLogin;
    private Button btnCancelar;
    private Button btnRegistrar_Usu;
    private Button btnRecordar;
    public static Usuario usu;
    private SharedPreferences preferencias;

    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencias = getSharedPreferences("Datos", MODE_PRIVATE);

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);

        checkRecordar = (CheckBox) findViewById(R.id.checkRecordar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        btnRegistrar_Usu = (Button) findViewById(R.id.btnRegistrar_Usu);
        btnRegistrar_Usu.setOnClickListener(this);
        btnRecordar = (Button) findViewById(R.id.btnRecordar);
        btnRecordar.setOnClickListener(this);
        estado();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login(txtCorreo.getText().toString(), txtClave.getText().toString());
                break;

            case R.id.btnCancelar:
                finish();
                break;

            case R.id.btnRegistrar_Usu:
                Intent intent = new Intent(this, RegistroActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btnRecordar:
                if(!txtCorreo.getText().toString().isEmpty()) {
                   sms();
                }
                else
                    Toast.makeText(this, "Ingrese su Correo", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void login(String correo, String clave){
        ControladorUsuarios control = new ControladorUsuarios(this);
        control.conectar();
        usu = control.verificarUsuario(correo, clave);
        control.desconectar();
        if(usu != null) {
            if(checkRecordar.isChecked())
                preferencias();
            Intent intent = new Intent(this, ListaActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "Usuario No Existe", Toast.LENGTH_SHORT).show();
    }

    public void sms(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Desea Enviar el Mensaje")
                .setTitle("Enviar Mensaje");
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SmsManager sms = SmsManager.getDefault();
                ControladorUsuarios control = new ControladorUsuarios(LoginActivity.this);
                control.conectar();
                Usuario usuario = control.buscarUsuario(txtCorreo.getText().toString());
                control.desconectar();
                sms.sendTextMessage(usuario.getUsu_telefono(), null, "Correo: "+usuario.getUsu_correo()+"\nClave: "+usuario.getUsu_clave(), null, null);
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

    protected void preferencias(){
        String correo = txtCorreo.getText().toString();
        String clave = txtClave.getText().toString();
        SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("correo", correo);
            editor.putString("clave", clave);
            editor.putBoolean("estado", checkRecordar.isChecked());
            editor.commit();
            editor.getClass().getMethods();
    }

    protected void estado(){
        if(preferencias.getBoolean("estado",false)){
           login(preferencias.getString("correo", null), preferencias.getString("clave", null));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

}
