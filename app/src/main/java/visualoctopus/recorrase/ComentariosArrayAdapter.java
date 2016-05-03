package visualoctopus.recorrase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class ComentariosArrayAdapter extends ArrayAdapter<Comentario>{

    private String username;

    public ComentariosArrayAdapter(Context context, List<Comentario> list, String username){
        super(context, 0, list);
        this.username = username;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario comentario = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_comentario_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.txt_hora);
        textView.setText(comentario.getHoras());

        TextView textView1 = (TextView) convertView.findViewById(R.id.txt_cusername);
        textView1.setText(username);

        TextView textView2 = (TextView) convertView.findViewById(R.id.txt_comentario);
        textView2.setText(comentario.getTexto());

        return convertView;
    }
}
