package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class RutaDescActivity extends Activity implements OnMapReadyCallback {

    private RutaCamion rutaCamion;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private ImageView letreroView;
    private ProgressDialog progressDialog;

    private Intent intent;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            actualiza(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_desc);

        intent = new Intent(this, ComentarioServicio.class);

        rutaCamion = (RutaCamion) getIntent().getSerializableExtra("ruta");

        TextView textView = (TextView) findViewById(R.id.desc_ruta);
        textView.setText(rutaCamion.getNombre());

        ImageView imageView = (ImageView) findViewById(R.id.btn_rate);
        Double calif = new Double(rutaCamion.getCalificacion());
        String sCalif = "rate_" + calif.intValue();
        int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageView.setImageResource(idCalif);

        progressDialog = new ProgressDialog(this);
        letreroView = (ImageView) findViewById(R.id.img_banner);
        cargaLetrero();

        RutaComentAsyncTask rutaComentAsyncTask = new RutaComentAsyncTask(this);
        String query = "idTrail=" + rutaCamion.getId();
        rutaComentAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/comentarios_ruta.php", query);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("You are here")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(50)
                .fillColor(0x44ff0000)
                .strokeColor(0xffff0000)
                .strokeWidth(2);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(5.0f);

        for(int i = 0; i < rutaCamion.getLatitudes().size(); i++){
            LatLng lng = new LatLng(rutaCamion.getLatitudes().get(i),
                    rutaCamion.getLongitudes().get(i));
            polylineOptions.add(lng);
            this.googleMap.addMarker(new MarkerOptions()
                    .position(lng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markers)));
        }

        this.googleMap.addCircle(circleOptions);
        this.googleMap.addPolyline(polylineOptions);
    }

    public void rateActivity(View v){
        Intent it =new  Intent(this, RateActivity.class);
        it.putExtra("ruta", rutaCamion);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent.putExtra("ruta", rutaCamion);
        startService(intent);
        registerReceiver(broadcastReceiver,
                new IntentFilter(ComentarioServicio._ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    boolean regresa = false;

    public void actualiza(Intent it){

        regresa = it.getBooleanExtra("activado", false);
        if(regresa) {
            RutaComentAsyncTask rutaComentAsyncTask = new RutaComentAsyncTask(this);
            String query = "idTrail=" + rutaCamion.getId();
            rutaComentAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/comentarios_ruta.php", query);
            regresa = false;
        }

    }

    public void cargaLetrero(){
        progressDialog.show();
        String s = rutaCamion.getLetrero();
        ImageRequest imageRequest = new ImageRequest(s, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                letreroView.setImageBitmap(response);
                progressDialog.dismiss();
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(imageRequest);
    }
}
