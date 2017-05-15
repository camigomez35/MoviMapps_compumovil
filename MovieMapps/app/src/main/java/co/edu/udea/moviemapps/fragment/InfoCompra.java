package co.edu.udea.moviemapps.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;


public class InfoCompra extends Fragment implements View.OnClickListener {
    public static final int ID = 20;
    public static final String TITLE = "TITLE";
    public TextView movieTitle, info;
    private Button comprar;
    private String nombrePelicula, lugar, sala, hora, precio;
    private ImageView codigoQR;
    private OnFragmentInteractionListener mListener;

    public static InfoCompra newInstance(String titulo, String lugar, String hora, String sala, String precio) {
        InfoCompra info = new InfoCompra();
        info.nombrePelicula = titulo;
        info.lugar = lugar;
        info.hora = hora;
        info.sala = sala;
        info.precio = precio;
        return info;
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
        info = (TextView) view.findViewById(R.id.info);
        movieTitle = (TextView) view.findViewById(R.id.movie_title);
        codigoQR = (ImageView) view.findViewById(R.id.codigoqr);

        comprar = (Button)  view.findViewById(R.id.comprar);
        comprar.setOnClickListener(this);


        movieTitle.setText(nombrePelicula);
        String texto = "Lugar: " + lugar + "\nSala: " + sala+ "\nHora: " + hora
                + "\nPrecio: " + precio;
        System.out.println(texto);
        info.setText(texto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comprar:
                codigoQR.setVisibility(View.VISIBLE);
                comprar.setVisibility(View.INVISIBLE);
                Toast.makeText(this.getContext(), "Compra Realizada", Toast.LENGTH_LONG).show();
                break;
        }
    }

}

