package com.example.inmobiliariabenito.ui.salir;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.inmobiliariabenito.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);

        // Mostrar diálogo inmediatamente al abrir el fragment
        mostrarDialogoSalir();

        return binding.getRoot();
    }

    private void mostrarDialogoSalir() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmación")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    requireActivity().finish(); // Cierra la app
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                    // Volver al fragment anterior (opcional)
                    requireActivity().onBackPressed();
                })
                .show(); //
    }


        @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
