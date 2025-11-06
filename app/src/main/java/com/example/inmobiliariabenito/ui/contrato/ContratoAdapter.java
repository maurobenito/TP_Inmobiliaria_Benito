package com.example.inmobiliariabenito.ui.contrato;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.modelo.Contrato;
import com.example.inmobiliariabenito.modelo.Inmueble;
import com.example.inmobiliariabenito.request.ApiClient;
import com.example.inmobiliariabenito.ui.inmueble.InmuebleAdapter;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderContrato> {

    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contrato, parent, false);
        return new ViewHolderContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContrato holder, int position) {
        Inmueble  inmActual = listaInmuebles.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        holder.monto.setText(inmActual.getValor()+"");
        Glide.with(context)
                .load(ApiClient.BASE_URL + inmActual.getImagen())
                .placeholder(null)
                .error("null");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmActual);
                navController.navigate(R.id.detalleContratoFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public static class ViewHolderContrato extends RecyclerView.ViewHolder {
        private TextView direccion, monto;

        public ViewHolderContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            monto = itemView.findViewById(R.id.tvValor);

        }
    }
}
