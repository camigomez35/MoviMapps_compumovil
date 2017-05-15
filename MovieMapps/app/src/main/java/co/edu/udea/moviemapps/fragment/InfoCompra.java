package co.edu.udea.moviemapps.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;


public class InfoCompra extends Fragment implements View.OnClickListener {
    public static final int ID = 20;
    public TextView movieTitle, info, lista;
    public EditText silla;
    public int contador;
    String sillas="Sillas: ";
    public LinearLayout linear;
    private Button comprar, seleccionar, finalizar;
    private String nombrePelicula, lugar, sala, hora;
    private int precio;
    private OnFragmentInteractionListener mListener;

    public static InfoCompra newInstance(String titulo, String lugar, String hora, String sala, int precio) {
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
        silla = (EditText) view.findViewById(R.id.numero);
        linear = (LinearLayout) view.findViewById(R.id.linear);
        info = (TextView) view.findViewById(R.id.info);
        movieTitle = (TextView) view.findViewById(R.id.movie_title);
        lista = (TextView) view.findViewById(R.id.lista);
        comprar = (Button)  view.findViewById(R.id.comprar);
        comprar.setOnClickListener(this);

        seleccionar = (Button)  view.findViewById(R.id.seleccionar);
        seleccionar.setOnClickListener(this);

        finalizar = (Button) view.findViewById(R.id.finalizar);
        finalizar.setOnClickListener(this);

        movieTitle.setText(nombrePelicula);
        String texto = "Lugar: " + lugar + "\nSala: " + sala+ "\nHora: " + hora
                + "\nPrecio: $" + precio;
        System.out.println(texto);
        info.setText(texto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comprar:
                comprar.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.VISIBLE);
                break;
            case R.id.seleccionar:
                if(silla.getText().toString().equals("")){
                    Toast.makeText(this.getContext(), "Seleccione una silla", Toast.LENGTH_SHORT).show();
                }else if(silla.getText().toString().equals("A1")){
                    Toast.makeText(this.getContext(), "Silla ya vendida", Toast.LENGTH_SHORT).show();
                }else if(silla.getText().toString().equals("I5") || silla.getText().toString().equals("I6")){
                    Toast.makeText(this.getContext(), "Silla reservada", Toast.LENGTH_SHORT).show();
                } else {
                    if(contador>0){
                        sillas = sillas + ", "+silla.getText();
                    }else{
                        sillas = sillas + silla.getText();
                    }
                    silla.setText("");
                    lista.setText(sillas);
                    contador++;
                }
                break;
            case R.id.finalizar:
                if(contador<0){
                    Toast.makeText(this.getContext(), "Por favor seleccione al menos una silla", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Valor: "+precio*contador+" " + sillas);
                    LayoutInflater factory = LayoutInflater.from(getActivity());
                    final View view = factory.inflate(R.layout.image, null);
                    builder.setView(view);
                    builder.setNegativeButton("GUARDAR",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Acciones
                                }
                            });
                    builder.show();
                    finalizar.setEnabled(false);
                    seleccionar.setEnabled(false);
                    silla.setEnabled(false);
                }
                break;
        }
    }

}

