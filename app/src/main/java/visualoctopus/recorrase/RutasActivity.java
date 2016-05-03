package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RutasActivity extends ListActivity {

    private LocationManager locationManager;

    public RutasActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Log.i("Cosas", getIntent().getDoubleExtra("latitud", 0) + "");
            double latitud = bundle.getDouble("latitud", location.getLatitude());
            double longitud = bundle.getDouble("longitud", location.getLatitude());
            RutasAsyncTask rutasAsyncTask = new RutasAsyncTask(this);
            String query = "latitud=" + latitud + "&longitud=" + longitud;
            rutasAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/rutaspunto.php", query);
        }else {
            RutasAsyncTask rutasAsyncTask = new RutasAsyncTask(this);
            String query = "latitud=" + location.getLatitude() + "&longitud=" + location.getLongitude();
            rutasAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/rutaspunto.php", query);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Ruta r = (Ruta) getListAdapter().getItem(position);

        if(r instanceof RutaCamion){
            Intent it = new Intent(this, RutaDescActivity.class);
            it.putExtra("ruta", r);

            startActivity(it);
        } else {
            Intent it = new Intent(this, MetroDescActivity.class);
            it.putExtra("ruta", r);

            startActivity(it);
        }
    }
}
