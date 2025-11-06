package com.example.inmobiliariabenito.ui.inmueble;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliariabenito.modelo.Inmueble;



public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                binding.etDireccion.setText(inmueble.getDireccion());
                binding.etUso.setText(inmueble.getUso());
                binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
                binding.etTipo.setText(inmueble.getTipo());
                binding.cbDisponible.setChecked(inmueble.isDisponible());

                binding.etCodigo.setEnabled(false);
                binding.etDireccion.setEnabled(false);
                binding.etUso.setEnabled(false);
                binding.etAmbientes.setEnabled(false);
                binding.etPrecio.setEnabled(false);
                binding.etTipo.setEnabled(false);
                binding.cbDisponible.setEnabled(true);

                String url = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/"
                        + inmueble.getImagen().replace("\\", "/");

                Glide.with(requireContext())
                        .load(url)
                        .into(binding.imgPortada);
            }
        });


        binding.cbDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mv.actualizarDisponibleLocal(isChecked);
            }
        });


        binding.btnGuardarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.guardarCambios();
            }
        });

        mv.recuperarInmueble(getArguments());

        return binding.getRoot();
    }
}
