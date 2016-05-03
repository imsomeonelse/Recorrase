package visualoctopus.recorrase;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sue on 06/03/2016.
 */
public class RutaAdapter extends ArrayAdapter<Ruta> {
    public RutaAdapter(Context context, List<Ruta> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ruta ruta = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ruta_row_layout, parent, false);
        }
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/steelfish rg.ttf");
        TextView textview = (TextView)convertView.findViewById(R.id.rutaNombre);
        textview.setText(ruta.getNombre());
        textview.setTypeface(font);
        ImageView logo=(ImageView) convertView.findViewById(R.id.logo);
        if(ruta instanceof RutaMetro){
            logo.setImageResource(R.drawable.metro);
        }

        if(ruta instanceof RutaMetrobus){
            logo.setImageResource(R.drawable.trenligero);
        }

        if(ruta instanceof RutaCamion){
            logo.setImageResource(R.drawable.camion3);
        }
        return convertView;
    }
}
