package com.example.inmobiliariabenito.ui.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariabenito.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel mv;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        mv.getMMensaje().observe(this, mensaje ->
                Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show()
        );

        binding.btLogin.setOnClickListener(v -> {
            String usuario = binding.etUsuario.getText().toString().trim();
            String contrasenia = binding.etContraseA.getText().toString().trim();
            mv.logueo(usuario, contrasenia);
        });

        // Inicializamos el sensor de movimiento
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        solicitarPermisos();


    }
    public void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean faltaLlamada = checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED;
            boolean faltaFine = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
            boolean faltaCoarse = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

            if (faltaLlamada || faltaFine || faltaCoarse) {
                requestPermissions(new String[]{
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1000);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // La Activity no toma decisiones, solo pasa los datos al ViewModel
        mv.procesarMovimiento(x, y, z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
