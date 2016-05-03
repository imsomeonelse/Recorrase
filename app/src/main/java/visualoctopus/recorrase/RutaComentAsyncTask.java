package visualoctopus.recorrase;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by chuch_000 on 23/04/2016.
 */
public class RutaComentAsyncTask extends AsyncTask<String, String, ArrayList<Comentario>> {

    private ProgressDialog dialogo;
    private RutaDescActivity rutaDescActivity;


    public RutaComentAsyncTask(RutaDescActivity rutaDescActivity){
        this.rutaDescActivity = rutaDescActivity;
        dialogo = new ProgressDialog(this.rutaDescActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle("Cargando datos...");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected ArrayList<Comentario> doInBackground(String... params) {
        String jsonFile = getJSON(params[0], params[1]);
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);

            try{
                JSONArray jsonArray1 = jsonObject.getJSONArray("comentarios");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String texto = jsonObject1.getString("texto");
                    String hora = jsonObject1.getString("hora");
                    String fecha = jsonObject1.getString("fecha");
                    String usuario = jsonObject1.getString("usuario");

                    Comentario comentario = new Comentario(texto, hora);
                    comentario.setFecha(fecha);
                    comentario.setUsuario(usuario);

                    comentarios.add(comentario);
                }
            }catch (JSONException e1) {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comentarios;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("SalidaPerfil", values[0]);
    }

    @Override
        protected void onPostExecute(ArrayList<Comentario> comentarios) {
        super.onPostExecute(comentarios);

        ComentariosRFragment fragment = new ComentariosRFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("comentarios", comentarios);
        fragment.setArguments(bundle);
        rutaDescActivity.getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.comment_container, fragment).commit();
        dialogo.dismiss();
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
