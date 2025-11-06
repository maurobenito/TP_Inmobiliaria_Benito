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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        vm.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvFechaInicio.setText(contrato.getFechaInicio());
                binding.tvFechaFin.setText(contrato.getFechaFinalizacion());
                binding.tvMonto.setText(String.valueOf(contrato.getMontoAlquiler()));
                binding.tvInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());


                String url = ApiClient.BASE_URL + contrato.getInmueble().getImagen().replace("\\", "/");

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
