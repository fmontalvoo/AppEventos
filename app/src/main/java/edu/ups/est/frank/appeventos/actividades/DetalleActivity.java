package edu.ups.est.frank.appeventos.actividades;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.adb.ControladorEventos;
import edu.ups.est.frank.appeventos.adb.ControladorInvitados;
import edu.ups.est.frank.appeventos.entidades.Evento;
import edu.ups.est.frank.appeventos.entidades.Invitado;
import edu.ups.est.frank.appeventos.entidades.Usuario;

public class DetalleActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private int codigo;
    private int inv_codigo = 0;
    private double latitud = 0.0;
    private double longitud = 0.0;

    private TextView txtNombre;
    private TextView txtDescripcion;
    private TextView txtFecha;
    private TextView txtEstado;
    private Button btnAsistire;
    private Button btnInteresa;
    private Button btnNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtEstado = (TextView) findViewById(R.id.txtEstado);

        btnAsistire = (Button) findViewById(R.id.btnAsistire);
        btnAsistire.setOnClickListener(this);
        btnInteresa = (Button) findViewById(R.id.btnInteresa);
        btnInteresa.setOnClickListener(this);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle datos = intent.getExtras();

        if(datos != null){
            codigo = datos.getInt("eve_codigo");
            inv_codigo = datos.getInt("inv_codigo");
            cargarDatos();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latlng = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(latlng).title(txtNombre.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));


        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(16.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }

    public void cargarDatos(){
        ControladorEventos control = new ControladorEventos(this);
        Evento evento = new Evento();
        control.conectar();
        evento = control.buscarEvento(codigo);
        txtNombre.setText(evento.getEve_nombre());
        txtDescripcion.setText(evento.getEve_descripcion());
        txtFecha.setText(evento.getEve_fecha());
        latitud = evento.getEve_latitud();
        longitud = evento.getEve_longitud();
        txtEstado.setText(evento.getEve_estado());
        control.desconectar();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAsistire:
                if(inv_codigo != 0)
                    responder("C");
                else
                    interes("C");
                break;

            case R.id.btnInteresa:
                if(inv_codigo != 0)
                    responder("I");
                else
                    interes("I");
                break;

            case R.id.btnNo:
                if(inv_codigo != 0)
                    responder("N");
                else
                    interes("N");
                break;
        }
    }

    public void interes(String interes){
        ControladorInvitados control = new ControladorInvitados(this);
        Invitado invitado = new Invitado();
        control.conectar();
        Evento evento = new Evento();
        evento.setEve_codigo(codigo);
        invitado.setUsuario(LoginActivity.usu);
        invitado.setEvento(evento);
        invitado.setInv_estado(interes);
        control.ingreasarInvitado(invitado);
        Toast.makeText(this, "Realizado", Toast.LENGTH_SHORT).show();
        control.desconectar();
    }

    public void responder(String interes){
        ControladorInvitados control = new ControladorInvitados(this);
        Invitado invitado = new Invitado();
        control.conectar();
        Evento evento = new Evento();
        evento.setEve_codigo(codigo);
        invitado.setInv_codigo(inv_codigo);
        invitado.setUsuario(LoginActivity.usu);
        invitado.setEvento(evento);
        invitado.setInv_estado(interes);
        control.actualizarInvitado(invitado);
        Toast.makeText(this, "Respondido", Toast.LENGTH_SHORT).show();
        control.desconectar();
    }

}
