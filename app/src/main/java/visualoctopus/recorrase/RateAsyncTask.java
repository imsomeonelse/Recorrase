package visualoctopus.recorrase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sue on 11/04/2016.
 */
public class RateAsyncTask extends AsyncTask<String, String, JSONObject> {

    RutaCamion ruta;

    private ProgressDialog dialogo;
    private RateActivity rateActivity;

    public RateAsyncTask(RateActivity rateActivity, RutaCamion ruta){
        this.rateActivity = rateActivity;
        dialogo = new ProgressDialog(this.rateActivity);
        this.ruta=ruta;
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
        Log.i("SalidaRate", values[0]);
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
            Toast.makeText(rateActivity, error, Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(rateActivity, RateActivity.class);
            intent.putExtra("ruta", ruta);
            rateActivity.startActivity(intent);
            //rateActivity.finish();
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
