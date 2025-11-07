package com.example.inmobiliariabenito.ui.contrato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliariabenito.databinding.FragmentContratoBinding;
import com.example.inmobiliariabenito.modelo.Inmueble;
import com.example.inmobiliariabenito.ui.inmueble.InmuebleAdapter;

public class ContratoFragment extends Fragment {

    private FragmentContratoBinding binding;
    private ContratoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(ContratoViewModel.class);


        vm.getListaInmueblesConContrato().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, getContext(), getLayoutInflater());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            binding.listaContratos.setLayoutManager(glm);
            binding.listaContratos.setAdapter(adapter);
        });

        // Carga inicial de inmuebles con contrato
        vm.obtenerInmueblesConContrato();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
