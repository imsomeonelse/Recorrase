package visualoctopus.recorrase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Created by Sue on 27/03/2016.
 */
public class RecorridoAsyncTask extends AsyncTask<String, String, JSONObject>{

    private ProgressDialog dialogo;
    private RecorridoActivity recorridoActivity;
    private String username = "";

    public RecorridoAsyncTask(RecorridoActivity recorridoActivity){
        this.recorridoActivity = recorridoActivity;
        dialogo = new ProgressDialog(this.recorridoActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle("Guardando datos");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String jsonFile = writeData(params[0], params[1]);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(jsonFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("SalidaRecorrido", values[0]);
    }

    @Override
    protected void onPostExecute(JSONObject res) {
        super.onPostExecute(res);
        dialogo.dismiss();

        int status = 0;
        String error = "";
        try {
            status = res.getInt("success");
            error = res.getString("detalles_error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(status == 0){
            Toast.makeText(recorridoActivity, error, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(recorridoActivity, "Recorrido almacenado.", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(recorridoActivity, RecorridoActivity.class);
            recorridoActivity.startActivity(intent);
            recorridoActivity.finish();*/
        }
    }

    private String writeData(String jsonURL, String query){
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
