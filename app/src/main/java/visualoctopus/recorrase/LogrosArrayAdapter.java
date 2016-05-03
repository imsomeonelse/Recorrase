package visualoctopus.recorrase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class LogrosArrayAdapter extends ArrayAdapter<Logro>{

    public LogrosArrayAdapter(Context context, List<Logro> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Logro logro = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_logro_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.txt_logro);
        textView.setText(logro.getDescripcion());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_logro);
        int resID = getContext().getResources().getIdentifier(logro.getImagen() , "drawable", getContext().getPackageName());
        imageView.setImageResource(resID);

        return convertView;
    }
}
