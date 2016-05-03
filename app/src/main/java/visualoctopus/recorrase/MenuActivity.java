package visualoctopus.recorrase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        username = getIntent().getStringExtra("usuario");
    }

    public void buscarRutasActivity(View v) {
        Intent it = new Intent(this, BuscarRutasActivity.class);
        startActivity(it);
    }

    public void rutasActivity(View v) {
        Intent it = new Intent(this, RutasActivity.class);
        startActivity(it);
    }

    public void nuevoRecorridoActivity(View v) {
        Intent it = new Intent(this, RecorridoActivity.class);
        startActivity(it);
    }

    public void profileActivity(View v) {
        Intent it = new Intent(this, ProfileActivity.class);
        it.putExtra("usuario", username);
        startActivity(it);
    }

    public void logout(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged", false);
        editor.commit();
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
    }
}
