package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class RecorridoActivity extends Activity {
    private int puntos;
    private float km;
    private float dinero;
    private float huella;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean empezar=true;
    LatLng s, f;
    ImageView play;
    TextView puntosLbl, kmLbl, dineroLbl, huellaLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/steelfish rg.ttf");

        play=(ImageView) findViewById(R.id.play);

        TextView titleLbl = (TextView) findViewById(R.id.title);
        puntosLbl = (TextView) findViewById(R.id.puntos);
        kmLbl = (TextView) findViewById(R.id.km);
        dineroLbl = (TextView) findViewById(R.id.dinero);
        huellaLbl = (TextView) findViewById(R.id.huella);

        titleLbl.setTypeface(font);
        puntosLbl.setTypeface(font);
        kmLbl.setTypeface(font);
        dineroLbl.setTypeface(font);
        huellaLbl.setTypeface(font);

        //SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        //empezar = sharedPreferences.getBoolean("empezar", true);
    }

    public void pushea(View view) {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(empezar){
            s=latLng;
            //s=new LatLng(19.284707, -99.138085);
            empezar=false;
            play.setImageResource(R.drawable.stop);
        }else if(!empezar){
            f=latLng;
            empezar = true;
            //f=new LatLng(19.334289, -99.081179);;
            play.setImageResource(R.drawable.play);
            float distancia=(float) haversine(s, f);
            calcula(distancia);
            enviarRecorrido();
        }
    }

    public double haversine(LatLng start, LatLng finish){
        final int R = 6371;
        Double latDistance = Math.toRadians(finish.latitude-start.latitude);
        Double lonDistance = Math.toRadians(finish.longitude-start.longitude);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(start.latitude)) * Math.cos(Math.toRadians(finish.latitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;
    }

    public void calcula(float distancia){
        double GASOLINA=13.95;
        dinero= (float) (GASOLINA*(0.08333*distancia));
        km=distancia;
        puntos=(int) (100*distancia);
        puntosLbl.setText("+"+puntos+" puntos");
        dineroLbl.setText("$"+dinero+" ahorrados");
        kmLbl.setText(km+" km recorridos");
        huella=(float) (0.3*distancia);
        huellaLbl.setText("-"+huella+"kg de CO2");
    }

    public void enviarRecorrido(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        String users = sharedPreferences.getString("username", "chuu24");
        String query="usuario="+users+"&recorridototal="+km+"&ahorrototal=" +dinero;
        RecorridoAsyncTask registroAsyncTask = new RecorridoAsyncTask(this);
        registroAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/nuevoRecorrido.php", query);
    }

    /*protected  void onStart(){
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        empezar = sharedPreferences.getBoolean("empezar", true);
        if(empezar){
            play.setImageResource(R.drawable.play);
            LatLng lng = new LatLng((double) sharedPreferences.getFloat("latitud", 19.284707f),
                    (double) sharedPreferences.getFloat("longitud", 19.284707f));
            s = lng;
        }else if(!empezar){
            play.setImageResource(R.drawable.stop);
        }
    }

    protected void onRestart(){
        super.onRestart();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        empezar = sharedPreferences.getBoolean("empezar", true);
        if(empezar){
            play.setImageResource(R.drawable.play);
            LatLng lng = new LatLng((double) sharedPreferences.getFloat("latitud", 19.284707f),
                    (double) sharedPreferences.getFloat("longitud", 19.284707f));
            s = lng;
        }else if(!empezar){
            play.setImageResource(R.drawable.stop);
        }
    }

    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        empezar = sharedPreferences.getBoolean("empezar", true);
        if(empezar){
            play.setImageResource(R.drawable.play);
            LatLng lng = new LatLng((double) sharedPreferences.getFloat("latitud", 19.284707f),
                    (double) sharedPreferences.getFloat("longitud", 19.284707f));
            s = lng;
        }else if(!empezar){
            play.setImageResource(R.drawable.stop);
        }
    }

    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("empezar", empezar);
        editor.putFloat("latitud", (float) latLng.latitude);
        editor.putFloat("longitud", (float) latLng.longitude);
        editor.commit();
    }

    protected void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("empezar", empezar);
        editor.putFloat("latitud", (float) latLng.latitude);
        editor.putFloat("longitud", (float) latLng.longitude);
        editor.commit();
    }

    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("empezar", empezar);
        editor.putFloat("latitud", (float) latLng.latitude);
        editor.putFloat("longitud", (float) latLng.longitude);
        editor.commit();
    }*/
}
