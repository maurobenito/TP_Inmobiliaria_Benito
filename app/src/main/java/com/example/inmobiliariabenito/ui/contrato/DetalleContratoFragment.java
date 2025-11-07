package com.example.inmobiliariabenito.ui.contrato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliariabenito.modelo.Contrato;
import com.example.inmobiliariabenito.request.ApiClient;

public class DetalleContratoFragment extends Fragment {

    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel vm;
    private Contrato contratoActual; // 👈 guardamos el contrato que llega

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        int idInmueble = requireArguments().getInt("idInmueble");


        vm.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                contratoActual = contrato; // 👈 lo guardamos

                String fechaInicioFormateada = contrato.getFechaInicio().substring(8, 10) + "/" +
                        contrato.getFechaInicio().substring(5, 7) + "/" +
                        contrato.getFechaInicio().substring(0, 4);

                String fechaFinFormateada = contrato.getFechaFinalizacion().substring(8, 10) + "/" +
                        contrato.getFechaFinalizacion().substring(5, 7) + "/" +
                        contrato.getFechaFinalizacion().substring(0, 4);

                binding.tvFechaInicio.setText(fechaInicioFormateada);
                binding.tvFechaFin.setText(fechaFinFormateada);


                binding.tvMonto.setText("$ " + String.format("%.2f", contrato.getMontoAlquiler()));

                binding.tvInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());



                binding.btnVerInquilino.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contrato", contratoActual);
                    Navigation.findNavController(v).navigate(R.id.detalleInquilinoFragment, bundle);
                });

                binding.btnVerPagos.setOnClickListener(v -> {
                    Bundle bundlePagos = new Bundle();
                    bundlePagos.putInt("idContrato", contratoActual.getIdContrato());
                    Navigation.findNavController(v).navigate(R.id.detallePagoFragment, bundlePagos);
                });

                binding.btnVolver.setOnClickListener(v -> {
                    Navigation.findNavController(v).popBackStack();
                });






            }
        });




        vm.obtenerContratoPorInmueble(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
