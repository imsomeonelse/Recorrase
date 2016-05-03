package visualoctopus.recorrase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/steelfish rg.ttf");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Button iniciaSesion=(Button) findViewById(R.id.btn_entrar);
        Button registra=(Button) findViewById(R.id.btn_registra);
        iniciaSesion.setTypeface(font);
        registra.setTypeface(font);
    }

    public void mainActivity(View v){
        Intent it =new  Intent(this, LogInActivity.class);
        startActivity(it);
    }

    public void creditosActivity(View v){
        Intent it =new  Intent(this, CreditosActivity.class);
        startActivity(it);
    }

    public void registroActivity(View v){
        Intent it =new  Intent(this, RegistroActivity.class);
        startActivity(it);
    }
}
