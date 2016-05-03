package visualoctopus.recorrase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends Activity {
    EditText tbuser, tbpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tbuser = (EditText) findViewById(R.id.lguser);
        tbpass = (EditText) findViewById(R.id.lgpass);

        Button entrar = (Button) findViewById(R.id.btnentrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    public void logIn(){
        String username=tbuser.getText().toString();
        String password=tbpass.getText().toString();

        String query="usuario="+username+"&password=" + password;
        LogInAsyncTask logInAsyncTask = new LogInAsyncTask(this);
        logInAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/login.php", query, username);
    }
}
