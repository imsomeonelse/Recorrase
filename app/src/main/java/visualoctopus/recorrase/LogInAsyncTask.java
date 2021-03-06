package visualoctopus.recorrase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by chuch_000 on 03/04/2016.
 */
public class LogInAsyncTask extends AsyncTask<String, String, JSONObject> {

    private ProgressDialog dialogo;
    private LogInActivity logInActivity;
    private String username = "";

    public LogInAsyncTask(LogInActivity logInActivity){
        this.logInActivity = logInActivity;
        dialogo = new ProgressDialog(this.logInActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle("Accesando");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String jsonFile = writeData(params[0], params[1]);
        username = params[2];
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
        Log.i("SalidaRegistro", values[0]);
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
            Toast.makeText(logInActivity, error, Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences sharedPreferences = logInActivity.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.putBoolean("logged", true);
            editor.commit();
            Intent intent = new Intent(logInActivity, MenuActivity.class);
            logInActivity.startActivity(intent);
            logInActivity.finish();
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
