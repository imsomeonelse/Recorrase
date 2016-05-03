package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BuscarRutasActivity extends Activity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private Marker marker;

    private double latitud;
    private double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_rutas);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        marker = googleMap.addMarker(new MarkerOptions().
                position(latLng).title("Location").draggable(true));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                latitud = marker.getPosition().latitude;
                longitud = marker.getPosition().longitude;
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }

    public void buscarDireccion(View v) {
        InputMethodManager inputManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        EditText editText = (EditText) findViewById(R.id.addres_edit);
        String dir = editText.getText().toString();
        if(dir.length() == 0){
            Toast.makeText(this, "Dirección incompleta.", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(dir, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList.size() > 0) {
            Address address = addressList.get(0);

            double lat = address.getLatitude();
            double lon = address.getLongitude();

            LatLng posAdd = new LatLng(lat, lon);

            MarkerOptions options = new MarkerOptions().position(posAdd).draggable(true);

            if (marker != null) {
                marker.remove();
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(posAdd));
            marker = googleMap.addMarker(options);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posAdd, 18.0f));
        }else{
            Toast.makeText(this, "No se encuentra la dirección", Toast.LENGTH_SHORT).show();
        }
    }

    public void rutasActivity(View v) {
        Intent it = new Intent(this, RutasActivity.class);
        it.putExtra("latitud", latitud);
        it.putExtra("longitud", longitud);
        startActivity(it);
    }
}
