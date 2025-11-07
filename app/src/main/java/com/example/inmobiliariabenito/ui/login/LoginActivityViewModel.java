package com.example.inmobiliariabenito.ui.login;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.MainActivity;
import com.example.inmobiliariabenito.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje;
    private static final String TELEFONO_INMOBILIARIA = "tel:2644123456";

    // Variables para cálculo del movimiento
    private float acelVal = SensorManager.GRAVITY_EARTH;
    private float acelLast = SensorManager.GRAVITY_EARTH;
    private float shake = 0.0f;
    private static final int SHAKE_THRESHOLD = 12;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    // 🔹 Procesa los valores del sensor y decide si hay shake
    public void procesarMovimiento(float x, float y, float z) {
        acelLast = acelVal;
        acelVal = (float) Math.sqrt((x * x + y * y + z * z));
        float delta = acelVal - acelLast;
        shake = shake * 0.9f + delta;

        if (shake > SHAKE_THRESHOLD) {
            onShakeDetected();
        }
    }

    // 🔹 Acción al detectar sacudida
    public void onShakeDetected() {
        mMensaje.setValue("Detectado movimiento: llamando a la inmobiliaria...");

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(TELEFONO_INMOBILIARIA));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            getApplication().startActivity(intent);
        } else {
            mMensaje.setValue("Permiso de llamada no concedido");
        }
    }

    // 🔹 Lógica de login
    public void logueo(String usuario, String contrasenia) {
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            mMensaje.setValue("Error: campos vacíos");
            return;
        }

        ApiClient.InmoServicio inmoServicio = ApiClient.getInmoServicio();
        Call<String> call = inmoServicio.loginForm(usuario, contrasenia);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Log.d("token", token);

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.putExtra("usuario", usuario);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(intent);
                } else {
                    mMensaje.setValue("Credenciales incorrectas o error en servidor");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mMensaje.setValue("Error de conexión: " + throwable.getMessage());
            }
        });
    }
}
