package visualoctopus.recorrase;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class JSONAsyncTask extends AsyncTask<String, String, Perfil>{

    private ProgressDialog dialogo;
    private ProfileActivity profileActivity;


    public JSONAsyncTask(ProfileActivity profileActivity){
        this.profileActivity = profileActivity;
        dialogo = new ProgressDialog(this.profileActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle("Cargando datos...");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected Perfil doInBackground(String... params) {
        String jsonFile = getJSON(params[0], params[1]);
        Perfil perfil = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            String usuario = jsonObject.getString("usuario");
            String nombre = jsonObject.getString("nombre");
            String correo = jsonObject.getString("correo");
            String foto = jsonObject.getString("fotoperfil");
            String nivel = jsonObject.getString("nivel");

            List<Logro> logros = new ArrayList<>();
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("logros");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String descripcion = jsonObject1.getString("descripcion");
                    String imagen = jsonObject1.getString("imagen");
                    logros.add(new Logro(descripcion, imagen));
                }
            }catch (JSONException e1){

            }

            List<Comentario> comentarios = new ArrayList<>();
            try{
                JSONArray jsonArray1 = jsonObject.getJSONArray("comentarios");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String texto = jsonObject1.getString("texto");
                    String hora = jsonObject1.getString("hora");
                    comentarios.add(new Comentario(texto, hora));
                }
            }catch (JSONException e1) {

            }
            perfil = new Perfil(usuario, nombre, correo, foto, nivel, comentarios, logros);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return perfil;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("SalidaPerfil", values[0]);
    }

    @Override
    protected void onPostExecute(Perfil perfil) {
        super.onPostExecute(perfil);
        profileActivity.setPerfil(perfil);

        TextView usuario = (TextView) profileActivity.findViewById(R.id.txt_username);
        usuario.setText(perfil.getUsuario());

        TextView nombre = (TextView) profileActivity.findViewById(R.id.txt_name);
        nombre.setText(perfil.getNombre());

        TextView correo = (TextView) profileActivity.findViewById(R.id.txt_correo);
        correo.setText(perfil.getCorreo());

        TextView nivel = (TextView) profileActivity.findViewById(R.id.txt_nivel);
        nivel.setText("Nivel: " + perfil.getNivel() + " de 10000");

        ImageView fotoperfil = (ImageView) profileActivity.findViewById(R.id.img_profile);
        //int resID = profileActivity.getResources().getIdentifier(perfil.getFoto() , "drawable", profileActivity.getPackageName());
        /*Bitmap bm = BitmapFactory.decodeResource(profileActivity.getResources(),
                R.drawable.foto);*/
        Log.i("STORED IMAGE PATH", perfil.getFoto());
        Uri image=Uri.fromFile(new File(perfil.getFoto()));
        //Uri image=Uri.fromFile(new File("storage/sdcard0/Pictures/squarequick/squarequick_2016314211047940.jpg"));
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(profileActivity.getContentResolver(), image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoundImage roundImage=new RoundImage(bm);
        fotoperfil.setImageDrawable(roundImage);

        LogrosFragment fragment=new LogrosFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("perfil", perfil);
        fragment.setArguments(bundle);
        profileActivity.getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, fragment).commit();
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
