package visualoctopus.recorrase;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link ListFragment} subclass.
 */
public class ComentariosRFragment extends ListFragment {


    public ComentariosRFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) getArguments().getSerializable("comentarios");

        ComentarioRutaArrayAdapter adapter = new ComentarioRutaArrayAdapter(getActivity().getApplicationContext(),
                comentarios);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comentarios_r, container, false);
    }

}
