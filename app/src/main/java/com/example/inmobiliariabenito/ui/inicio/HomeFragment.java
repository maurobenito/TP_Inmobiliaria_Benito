package com.example.inmobiliariabenito.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariabenito.R;
import com.google.android.gms.maps.SupportMapFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        vm = new HomeViewModel(requireActivity().getApplication());
        vm = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(HomeViewModel.class);


        vm.getMutableMapaActual().observe(getViewLifecycleOwner(), new Observer<HomeViewModel.MapaActual>() {
            @Override
            public void onChanged(HomeViewModel.MapaActual mapaActual) {
                ((SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map))
                        .getMapAsync(mapaActual);
            }
        });


        vm.cargarMapa();

        return root;
    }
}
