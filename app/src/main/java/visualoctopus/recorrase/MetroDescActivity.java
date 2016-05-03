package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MetroDescActivity extends Activity implements OnMapReadyCallback {

    private Ruta ruta;
    private GoogleMap googleMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_desc);

        ruta = (Ruta) getIntent().getSerializableExtra("ruta");

        TextView textView = (TextView) findViewById(R.id.desc_estacion);
        textView.setText(ruta.getNombre());

        ImageView imageView = (ImageView) findViewById(R.id.btn_ratem);
        Double calif = new Double(ruta.getCalificacion());
        String sCalif = "rate_" + calif.intValue();
        int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageView.setImageResource(idCalif);

        imageView = (ImageView) findViewById(R.id.img_estacion);
        int letrero = getResources().getIdentifier(ruta
                .getNombre()
                .trim()
                .replace(" ", "")
                .replace("í", "i")
                .toLowerCase(), "drawable", getPackageName());
        imageView.setImageResource(letrero);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapsFragment);

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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
        this.googleMap.addCircle(circleOptions);
    }
}
