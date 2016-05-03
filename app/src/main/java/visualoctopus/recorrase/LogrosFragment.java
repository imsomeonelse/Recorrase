package visualoctopus.recorrase;


import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link ListFragment} subclass.
 */
public class LogrosFragment extends ListFragment {

    List<Logro> logros;

    public LogrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Perfil perfil = (Perfil) getArguments().getSerializable("perfil");
        logros = perfil.getLogros();

        LogrosArrayAdapter adapter = new LogrosArrayAdapter(getActivity().getApplicationContext(), logros);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logros, container, false);
    }

}
