package com.example.inmobiliariabenito.ui.inquilino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.modelo.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolderPago> {

    private List<Pago> listaPago;
    private Context context;

    public PagoAdapter(List<Pago> listaPago, Context context) {
        this.listaPago = listaPago;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_pago, parent, false);
        return new ViewHolderPago(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {
        Pago pago = listaPago.get(position);
        String fecha = pago.getFechaPago().substring(8,10) + "/" +
                pago.getFechaPago().substring(5,7) + "/" +
                pago.getFechaPago().substring(0,4);
        holder.tvFechaPago.setText("Fecha: " + fecha);
        holder.tvMontoPago.setText("Monto: $" + pago.getMonto());
        holder.tvDetallePago.setText("Detalle: " + pago.getDetalle());
        holder.tvEstadoPago.setText("Estado: " + (pago.isEstado() ? "Pagado" : "Pendiente"));
    }

    @Override
    public int getItemCount() {
        return listaPago.size();
    }
    public void actualizarLista(List<Pago> nuevaLista) {
        listaPago.clear();
        listaPago.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    public static class ViewHolderPago extends RecyclerView.ViewHolder {
        TextView tvFechaPago, tvMontoPago, tvDetallePago, tvEstadoPago;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);

            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvMontoPago = itemView.findViewById(R.id.tvMontoPago);
            tvDetallePago = itemView.findViewById(R.id.tvDetallePago);
            tvEstadoPago = itemView.findViewById(R.id.tvEstadoPago);
        }
    }
}
