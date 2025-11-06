package com.example.inmobiliariabenito.ui.inquilino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliariabenito.R;
import com.example.inmobiliariabenito.modelo.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private TextView tvNombre, tvApellido, tvDni, tvTelefono, tvEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflamos el layout (asegurate que el XML esté con este nombre exacto)
        View root = inflater.inflate(R.layout.fragment_detalle_inquilino, container, false);

        tvNombre = root.findViewById(R.id.etNombre);
        tvApellido = root.findViewById(R.id.etApellido);
        tvDni = root.findViewById(R.id.etDni);
        tvTelefono = root.findViewById(R.id.etTelefono);
        tvEmail = root.findViewById(R.id.etEmail);
        Button btnVolver = root.findViewById(R.id.btnVolver);

        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        vm.getInquilinoMutableLiveData().observe(getViewLifecycleOwner(), inq -> {
            if (inq != null) {
                tvNombre.setText("Nombre: " + inq.getNombre());
                tvApellido.setText("Apellido: " + inq.getApellido());
                tvDni.setText("DNI: " + inq.getDni());
                tvTelefono.setText("Teléfono: " + inq.getTelefono());
                tvEmail.setText("Email: " + inq.getEmail());
            }
        });

        // Recibir el inquilino pasado como argumento desde DetalleContratoFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.cargarInquilino(bundle);
        }

        btnVolver.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        return root;
    }
}
