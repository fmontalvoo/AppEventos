package edu.ups.est.frank.appeventos.actividades;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.ups.est.frank.appeventos.R;
import edu.ups.est.frank.appeventos.actividades.EventosActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Button btnAceptar;
    private double latitud;
    private double longitud;
    private int codigo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle datos = intent.getExtras();

        if(datos != null){
            codigo = datos.getInt("eve_codigo");
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

        LatLng cuenca = new LatLng(-2.9005500, -79.0045300);
        mMap.addMarker(new MarkerOptions().position(cuenca).title("Marker in Cuenca"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cuenca));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(cuenca).zoom(11.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker))
                        .anchor(0.0f, 1.0f)
                        .position(latLng)
                        .title(latLng.latitude + ", " + latLng.longitude));
                latitud = latLng.latitude;
                longitud = latLng.longitude;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAceptar:
                Intent intent = new Intent(this, EventosActivity.class);
                if(codigo != -1) {
                    intent.putExtra("latitud", latitud);
                    intent.putExtra("longitud", longitud);
                    intent.putExtra("eve_codigo", codigo);
                    startActivity(intent);
                }else{
                    intent.putExtra("latitud", latitud);
                    intent.putExtra("longitud", longitud);
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
        }
    }
}
