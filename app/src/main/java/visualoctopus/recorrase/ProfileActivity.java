package visualoctopus.recorrase;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class ProfileActivity extends Activity {

    private Perfil perfil;
    private TextView usuario, nombre, correo, nivel;
    private ImageView fotoperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        String users = sharedPreferences.getString("username", "chuu24");

        JSONAsyncTask jsonAsyncTask = new JSONAsyncTask(this);
        String query="usuario="+users;
        jsonAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/perfilUsuario.php",
                query);
    }

    public void mostrarLogros(View v){
        LogrosFragment fragment = new LogrosFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("perfil", perfil);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container, fragment).commit();
    }

    public void mostrarComentarios(View v){
        ComentariosFragment fragment = new ComentariosFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("perfil", perfil);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container, fragment).commit();
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
