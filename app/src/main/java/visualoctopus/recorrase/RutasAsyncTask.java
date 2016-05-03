package visualoctopus.recorrase;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuch_000 on 05/04/2016.
 */
public class RutasAsyncTask extends AsyncTask<String, String, List<Ruta>>{

    private ProgressDialog dialogo;
    private RutasActivity rutasActivity;

    public RutasAsyncTask(RutasActivity rutasActivity) {
        this.rutasActivity = rutasActivity;
        dialogo = new ProgressDialog(this.rutasActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle("Cargando datos...");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected List<Ruta> doInBackground(String... params) {
        String jsonFile = getJSON(params[0], params[1]);
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        List<Ruta> rutas = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(rutasActivity.getAssets().open("json/rutas"));
            bufferedReader= new BufferedReader(inputStreamReader);
            while(bufferedReader.ready()){
                stringBuffer.append(bufferedReader.readLine());
            }
            bufferedReader.close();
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArrayCamiones = jsonObject.getJSONArray("rutas");
            for(int i=0; i<jsonArrayCamiones.length(); i++){
                JSONObject ruta = jsonArrayCamiones.getJSONObject(i);
                String nombre = ruta.getString("ruta") + ": " + ruta.getString("ramal");
                ArrayList<Double> arrayList = new ArrayList<>();
                arrayList.add(ruta.getDouble("seguridad"));
                arrayList.add(ruta.getDouble("servicio"));
                arrayList.add(ruta.getDouble("eficiencia"));
                arrayList.add(ruta.getDouble("unidades"));
                arrayList.add(ruta.getDouble("disponibilidad"));
                RutaCamion r = new RutaCamion(nombre, ruta.getString("ruta"), ruta.getString("ramal"), ruta.getString("rutacompleta"), ruta.getString("letrero"), ruta.getDouble("calificacion"));
                r.setCalificaciones(arrayList);
                JSONArray jsonArrays = ruta.getJSONArray("puntos");
                ArrayList<Double> latitudes = new ArrayList<>();
                ArrayList<Double> longitudes = new ArrayList<>();
                for(int j = 0; j< jsonArrays.length(); j++){
                    JSONObject pto = jsonArrays.getJSONObject(j);
                    latitudes.add(pto.getDouble("latitud"));
                    longitudes.add(pto.getDouble("longitud"));
                }
                r.setLatitudes(latitudes);
                r.setLongitudes(longitudes);
                r.setId(ruta.getLong("id_ruta"));
                rutas.add(r);
            }

            JSONObject jsonObjecto = new JSONObject((stringBuffer.toString()));
            JSONArray jsonArrayMetro = jsonObjecto.getJSONArray("metro");
            for(int i=0; i<jsonArrayMetro.length(); i++){
                JSONObject ruta = jsonArrayMetro.getJSONObject(i);
                String nombre="Línea "+ruta.getString("linea")+" "+ruta.getString("estacion");
                RutaMetro r= new RutaMetro(nombre, ruta.getString("estacion"), ruta.getString("linea"), ruta.getDouble("calificacion"));
                rutas.add(r);
            }
            JSONArray jsonArrayMetrobus = jsonObjecto.getJSONArray("metrobus");
            for(int i=0; i<jsonArrayMetrobus.length(); i++){
                JSONObject ruta = jsonArrayMetrobus.getJSONObject(i);
                String nombre="Línea "+ruta.getString("linea")+" "+ruta.getString("estacion");
                RutaMetrobus r= new RutaMetrobus(nombre, ruta.getString("estacion"), ruta.getString("linea"), ruta.getDouble("calificacion"));
                rutas.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rutas;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("SalidaPerfil", values[0]);
    }

    @Override
    protected void onPostExecute(List<Ruta> rutas) {
        super.onPostExecute(rutas);

        RutaAdapter rutaAdapter = new RutaAdapter(rutasActivity.getApplicationContext(), rutas);
        rutasActivity.setListAdapter(rutaAdapter);

        dialogo.dismiss();
    }

    private String getJSON(String jsonURL){
        HttpURLConnection connection = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            URL url = new URL(jsonURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            int status = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        }catch (Exception ex){
            Log.e("Error", "No se pudo obtener el JSON. e: " + ex);
        }finally{
            connection.disconnect();
        }

        return builder.toString();
    }

    private String getJSON(String jsonURL, String query){
        HttpURLConnection connection = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            URL url = new URL(jsonURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            connection.connect();

            int status = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        }catch (Exception ex){
            Log.e("Error", "No se pudo obtener el JSON. e: " + ex);
        }finally{
            connection.disconnect();
        }

        return builder.toString();
    }
}
