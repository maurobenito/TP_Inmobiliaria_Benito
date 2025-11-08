package com.example.inmobiliariabenito.ui.inquilino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.databinding.FragmentDetalleInquilinoBinding;
import com.example.inmobiliariabenito.modelo.Contrato;

public class DetalleInquilinoFragment extends Fragment {

    private FragmentDetalleInquilinoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);


        Contrato contratoActual = (Contrato) requireArguments().getSerializable("contrato");


        binding.etNombre.setText(contratoActual.getInquilino().getNombre());
        binding.etApellido.setText(contratoActual.getInquilino().getApellido());
        binding.etDni.setText(String.valueOf(contratoActual.getInquilino().getDni()));
        binding.etEmail.setText(contratoActual.getInquilino().getEmail());
        binding.etTelefono.setText(contratoActual.getInquilino().getTelefono());

        binding.btnVolver.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack()
        );
        binding.btnVerPagos.setOnClickListener(v -> {
            Bundle bundlePagos = new Bundle();
            bundlePagos.putInt("idContrato", contratoActual.getIdContrato());
            Navigation.findNavController(v).navigate(R.id.detallePagoFragment, bundlePagos);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
