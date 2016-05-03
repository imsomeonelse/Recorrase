package visualoctopus.recorrase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chuch_000 on 23/04/2016.
 */
public class ComentarioRutaArrayAdapter  extends ArrayAdapter<Comentario> {

    public ComentarioRutaArrayAdapter(Context context, List<Comentario> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario comentario = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_comentario_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.txt_hora);
        textView.setText(comentario.getFecha() + " - " + comentario.getHoras());

        TextView textView1 = (TextView) convertView.findViewById(R.id.txt_cusername);
        textView1.setText(comentario.getUsuario());

        TextView textView2 = (TextView) convertView.findViewById(R.id.txt_comentario);
        textView2.setText(comentario.getTexto());

        return convertView;
    }
}
