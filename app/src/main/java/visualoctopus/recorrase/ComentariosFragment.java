package visualoctopus.recorrase;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link ListFragment} subclass.
 */
public class ComentariosFragment extends ListFragment {

    List<Comentario> comentarios;

    public ComentariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Perfil perfil = (Perfil) getArguments().getSerializable("perfil");
        comentarios = perfil.getComentarios();

        ComentariosArrayAdapter adapter = new ComentariosArrayAdapter(getActivity().getApplicationContext(),
                comentarios, perfil.getUsuario());

        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comentarios, container, false);
    }

}
