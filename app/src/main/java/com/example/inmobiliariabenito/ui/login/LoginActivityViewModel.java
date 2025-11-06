package com.example.inmobiliariabenito.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
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

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

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
                    Log.d("token", response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mMensaje.setValue("Error de conexión: " + throwable.getMessage());
                Log.d("token", throwable.getMessage());
            }
        });
    }
}
