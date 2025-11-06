package com.example.inmobiliariabenito.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariabenito.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel mv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = new ViewModelProvider(this).get(LoginActivityViewModel.class);


        mv.getMMensaje().observe(this, mensaje ->
                Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show()
        );

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.etUsuario.getText().toString().trim();
                String contrasenia = binding.etContraseA.getText().toString().trim();
                mv.logueo(usuario, contrasenia);
            }

        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
