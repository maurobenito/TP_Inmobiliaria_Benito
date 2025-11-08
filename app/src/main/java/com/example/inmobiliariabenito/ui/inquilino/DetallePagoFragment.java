package com.example.inmobiliariabenito.ui.inquilino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.inmobiliariabenito.databinding.FragmentDetallePagoBinding;
import com.example.inmobiliariabenito.ui.inquilino.DetallePagoViewModel;

import java.util.ArrayList;

public class DetallePagoFragment extends Fragment {

    private FragmentDetallePagoBinding binding;
    private DetallePagoViewModel vm;
    private PagoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetallePagoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetallePagoViewModel.class);

        adapter = new PagoAdapter(new ArrayList<>(), requireContext());
        binding.recyclerPagos.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerPagos.setAdapter(adapter);


        vm.setIdContrato(requireArguments().getInt("idContrato"));


        vm.getPagos().observe(getViewLifecycleOwner(), pagos -> adapter.actualizarLista(pagos));


        binding.btnVolverPagos.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
