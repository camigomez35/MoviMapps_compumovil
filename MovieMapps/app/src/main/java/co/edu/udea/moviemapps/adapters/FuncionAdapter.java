package co.edu.udea.moviemapps.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.moviemapps.listener.OnItemFunctionListener;
import co.edu.udea.moviemapps.model.Function;
import co.edu.udea.moviemapps.util.MovieMappsUtils;

import static co.edu.udea.moviemapps.R.id;
import static co.edu.udea.moviemapps.R.layout;


public class FuncionAdapter extends RecyclerView.Adapter<FuncionAdapter.ViewHolder> {
    private ArrayList<Function> funcion;
    private OnItemFunctionListener listener;

    public FuncionAdapter(ArrayList<Function> funcion, OnItemFunctionListener listener) {
        this.funcion = funcion;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.funciones_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (funcion.get(position) != null) {

            holder.funcionHora.setText("Hora: " + funcion.get(position).getHora());
            holder.funcionLugar.setText("Cine: "+String.valueOf(funcion.get(position).getLugar()));
            holder.funcionSala.setText(String.valueOf(funcion.get(position).getSala()));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (listener != null) {
                    listener.onItemClick(funcion.get(position), holder.itemView, position);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return funcion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView funcionLugar, funcionSala, funcionHora;

        public ViewHolder(View itemView) {
            super(itemView);
            funcionHora = (TextView) itemView.findViewById(id.funcionHora);
            funcionLugar = (TextView) itemView.findViewById(id.funcionLugar);
            funcionSala = (TextView) itemView.findViewById(id.funcionSala);
        }
    }


}
