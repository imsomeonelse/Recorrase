package visualoctopus.recorrase;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by chuch_000 on 03/05/2016.
 */
public class ComentarioServicio extends Service {
    private static final String TAG = "InvocaServicio";
    public static final String _ACTION = "visualoctopus.recorrase.broadcast";
    private final Handler handler = new Handler();
    private Intent intent;

    private Runnable ejecutaAccion = new Runnable() {
        @Override
        public void run() {
            mensajeActivity();
            handler.postDelayed(this, 60000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(_ACTION);
    }

    RutaCamion n = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        n = (RutaCamion) intent.getSerializableExtra("ruta");
        handler.removeCallbacks(ejecutaAccion);
        handler.postDelayed(ejecutaAccion, 60000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(ejecutaAccion);
        super.onDestroy();
    }

    private void mensajeActivity(){
        intent.putExtra("activado", true);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
