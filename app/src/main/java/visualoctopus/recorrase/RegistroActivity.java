package visualoctopus.recorrase;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegistroActivity extends Activity {
    RoundImage roundedImage;
    private static final int RESULT_LOAD_IMAGE=1;
    ImageView foto;
    EditText tbnombre, tbuser, tbpass, tbpassconf, tbmail;
    String nombre, password, passwordc, username, mail;
    Bitmap bm;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        foto=(ImageView) findViewById(R.id.foto);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/steelfish rg.ttf");

        TextView lblnombre = (TextView) findViewById(R.id.lblnombre);
        TextView lbluser = (TextView) findViewById(R.id.lbluser);
        TextView lblpass = (TextView) findViewById(R.id.lblpass);
        TextView lblpassconf = (TextView) findViewById(R.id.lblpassconf);
        TextView lblmail = (TextView) findViewById(R.id.lblmail);

        tbnombre = (EditText) findViewById(R.id.tbnombre);
        tbuser = (EditText) findViewById(R.id.tbuser);
        tbpass = (EditText) findViewById(R.id.tbpass);
        tbpassconf = (EditText) findViewById(R.id.tbpassconf);
        tbmail = (EditText) findViewById(R.id.tbmail);

        Button enviar = (Button) findViewById(R.id.btnregistro);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarRegistro();
            }
        });

        lblnombre.setTypeface(font);
        lbluser.setTypeface(font);
        lblpass.setTypeface(font);
        lblpassconf.setTypeface(font);
        lblmail.setTypeface(font);
        //tbnombre.setTypeface(font);
        //tbuser.setTypeface(font);
        //tbmail.setTypeface(font);
        enviar.setTypeface(font);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
    }

    public void enviarRegistro(){
        /*ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);*/
        nombre=tbnombre.getText().toString();
        username=tbuser.getText().toString();
        password=tbpass.getText().toString();
        passwordc=tbpassconf.getText().toString();
        mail=tbmail.getText().toString();
        String query="usuario="+username+"&nombre="+nombre+"&password=" + password+"&cpassword=" + passwordc+"&image=" +path+"&correo=" + mail;
        RegistroAsyncTask registroAsyncTask = new RegistroAsyncTask(this);
        registroAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/nuevoUsuario.php", query, username);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage=data.getData();
            path=getRealPathFromURI(selectedImage);
            Log.i("PATH", path);
            bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            roundedImage = new RoundImage(bm);
            foto.setImageDrawable(roundedImage);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
