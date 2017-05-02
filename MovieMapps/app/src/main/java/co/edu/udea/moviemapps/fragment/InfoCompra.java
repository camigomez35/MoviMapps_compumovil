package co.edu.udea.moviemapps.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;


public class InfoCompra extends Fragment implements View.OnClickListener {
    public static final int ID = 20;
    public static final String TITLE = "TITLE";
    public TextView movieTitle, info;
    private Button comprar;
    private String text, nombrePelicula;
    private OnFragmentInteractionListener mListener;

    public static InfoCompra newInstance() {
        InfoCompra info = new InfoCompra();
        return info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombrePelicula = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detalle_compra, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = (OnFragmentInteractionListener) getActivity();
        info = (TextView) view.findViewById(R.id.overview);
        movieTitle = (TextView) view.findViewById(R.id.movie_title);

        comprar = (Button)  view.findViewById(R.id.verFunciones);
        comprar.setOnClickListener(this);

        movieTitle.setText(nombrePelicula);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comprar:
                comprar.setText("Comprado");
                comprar.invalidate();
                break;
        }
    }

}

