package visualoctopus.recorrase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RateActivity extends Activity {
    int newCalifSeg, newCalifSer, newCalifEfi, newCalifUni, newCalifDis;
    ImageView imageViewSeg, imageViewSer, imageViewEfi, imageViewUni, imageViewDis;
    Double califSeg, califSer, califEfi, califUni, califDis;
    Boolean addSeg, addSer, addEfi, addUni, addDis;
    ArrayList<Double> arrayList;
    RutaCamion ruta;
    long idRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/steelfish rg.ttf");

        TextView title=(TextView) findViewById(R.id.title);
        TextView seguridad=(TextView) findViewById(R.id.seguridad);
        TextView servicio=(TextView) findViewById(R.id.servicio);
        TextView eficiencia=(TextView) findViewById(R.id.eficiencia);
        TextView unidades=(TextView) findViewById(R.id.unidades);
        TextView disponibilidad=(TextView) findViewById(R.id.disponibilidad);
        TextView comentario=(TextView) findViewById(R.id.comentario);
        Button btnenviar=(Button) findViewById(R.id.btnenviar);

        servicio.setTypeface(font);
        title.setTypeface(font);
        seguridad.setTypeface(font);
        eficiencia.setTypeface(font);
        unidades.setTypeface(font);
        disponibilidad.setTypeface(font);
        comentario.setTypeface(font);
        btnenviar.setTypeface(font);

        ruta = (RutaCamion) getIntent().getSerializableExtra("ruta");
        arrayList = ruta.getCalificaciones();
        idRuta=ruta.getId();
        Log.i("Ruta", Long.toString(idRuta));

        imageViewSeg = (ImageView) findViewById(R.id.rate_seguridad);
        califSeg = new Double(arrayList.get(0));
        newCalifSeg=califSeg.intValue();
        if(newCalifSeg==5) {
            addSeg = false;
        }else if(newCalifSeg<5){
            addSeg=true;
        }
        String sCalif =  "rate_" + califSeg.intValue();
        int idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageViewSeg.setImageResource(idCalif);
        imageViewSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addSeg){
                    newCalifSeg++;
                    String sCalif =  "rate_" + newCalifSeg;
                    int idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewSeg.setImageResource(idCalif);
                    if(newCalifSeg==5) {
                        addSeg = false;
                    }else if(newCalifSeg<1){
                        addSeg=true;
                    }
                }else if(!addSeg){
                    newCalifSeg--;
                    String sCalif =  "rate_" + newCalifSeg;
                    int idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewSeg.setImageResource(idCalif);
                    if(newCalifSeg==5) {
                        addSeg = false;
                    }else if(newCalifSeg<1){
                        addSeg=true;
                    }
                }
            }
        });

        imageViewSer = (ImageView) findViewById(R.id.rate_servicio);
        califSer = new Double(arrayList.get(0));
        newCalifSer=califSer.intValue();
        if(newCalifSer==5) {
            addSer = false;
        }else if(newCalifSer<5){
            addSer=true;
        }
        sCalif =  "rate_" + califSer.intValue();
        idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageViewSer.setImageResource(idCalif);
        imageViewSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addSer) {
                    newCalifSer++;
                    String sCalif = "rate_" + newCalifSer;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewSer.setImageResource(idCalif);
                    if (newCalifSer == 5) {
                        addSer = false;
                    } else if (newCalifSer < 1) {
                        addSer = true;
                    }
                } else if (!addSer) {
                    newCalifSer--;
                    String sCalif = "rate_" + newCalifSer;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewSer.setImageResource(idCalif);
                    if (newCalifSer == 5) {
                        addSer = false;
                    } else if (newCalifSer < 1) {
                        addSer = true;
                    }
                }
            }
        });

        imageViewEfi = (ImageView) findViewById(R.id.rate_eficiencia);
        califEfi = new Double(arrayList.get(0));
        newCalifEfi=califEfi.intValue();
        if(newCalifEfi==5) {
            addEfi = false;
        }else if(newCalifEfi<5){
            addEfi=true;
        }
        sCalif =  "rate_" + califEfi.intValue();
        idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageViewEfi.setImageResource(idCalif);
        imageViewEfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addEfi) {
                    newCalifEfi++;
                    String sCalif = "rate_" + newCalifEfi;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewEfi.setImageResource(idCalif);
                    if (newCalifEfi == 5) {
                        addEfi = false;
                    } else if (newCalifEfi < 1) {
                        addEfi = true;
                    }
                } else if (!addEfi) {
                    newCalifEfi--;
                    String sCalif = "rate_" + newCalifEfi;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewEfi.setImageResource(idCalif);
                    if (newCalifEfi == 5) {
                        addEfi = false;
                    } else if (newCalifEfi < 1) {
                        addEfi = true;
                    }
                }
            }
        });

        imageViewUni = (ImageView) findViewById(R.id.rate_unidades);
        califUni = new Double(arrayList.get(0));
        newCalifUni=califUni.intValue();
        if(newCalifUni==5) {
            addUni = false;
        }else if(newCalifUni<5){
            addUni=true;
        }
        sCalif =  "rate_" + califUni.intValue();
        idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageViewUni.setImageResource(idCalif);
        imageViewUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addUni) {
                    newCalifUni++;
                    String sCalif = "rate_" + newCalifUni;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewUni.setImageResource(idCalif);
                    if (newCalifUni == 5) {
                        addUni = false;
                    } else if (newCalifUni < 1) {
                        addUni = true;
                    }
                } else if (!addUni) {
                    newCalifUni--;
                    String sCalif = "rate_" + newCalifUni;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewUni.setImageResource(idCalif);
                    if (newCalifUni == 5) {
                        addUni = false;
                    } else if (newCalifUni < 1) {
                        addUni = true;
                    }
                }
            }
        });

        imageViewDis = (ImageView) findViewById(R.id.rate_disponibilidad);
        califDis = new Double(arrayList.get(0));
        newCalifDis=califDis.intValue();
        if(newCalifDis==5) {
            addDis = false;
        }else if(newCalifDis<5){
            addDis=true;
        }
        sCalif =  "rate_" + califDis.intValue();
        idCalif =  getResources().getIdentifier(sCalif, "drawable", getPackageName());
        imageViewDis.setImageResource(idCalif);
        imageViewDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addDis) {
                    newCalifDis++;
                    String sCalif = "rate_" + newCalifDis;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewDis.setImageResource(idCalif);
                    if (newCalifDis == 5) {
                        addDis = false;
                    } else if (newCalifDis < 1) {
                        addDis = true;
                    }
                } else if (!addDis) {
                    newCalifDis--;
                    String sCalif = "rate_" + newCalifDis;
                    int idCalif = getResources().getIdentifier(sCalif, "drawable", getPackageName());
                    imageViewDis.setImageResource(idCalif);
                    if (newCalifDis == 5) {
                        addDis = false;
                    } else if (newCalifDis < 1) {
                        addDis = true;
                    }
                }
            }
        });
    }

    public void rutaDescActivity(View v){
        EditText texto = (EditText) findViewById(R.id.textocomentario);
        String comentario = texto.getText().toString();
        if(comentario == null){
            comentario = "";
        }
        comentario = comentario.trim();
        Log.i("SEGURIDAD", Integer.toString(newCalifSeg));
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        String users = sharedPreferences.getString("username", "chuu24");

        Calendar calendar = GregorianCalendar.getInstance();
        String tiempo = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        String fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

        String query="idTrail="+idRuta+"&seguridad="+newCalifSeg+"&servicio="+newCalifSer+"&eficiencia=" +newCalifEfi+"&unidades=" + newCalifUni+"&disponibilidad=" +newCalifDis;
        query = query + "&comentario=" + comentario + "&idUser=" + users + "&fecha=" + fecha + "&tiempo=" + tiempo;
        RateAsyncTask rateAsyncTask = new RateAsyncTask(this, ruta);
        rateAsyncTask.execute("http://paginas.ccm.itesm.mx/~A01215142/Recorrase/evalua_comentario.php", query);
    }
}
