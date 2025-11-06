package com.example.inmobiliariabenito.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.modelo.Contrato;
import com.example.inmobiliariabenito.modelo.Inmueble;
import com.example.inmobiliariabenito.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contrato;
    private Context context;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getContrato() {
        if (contrato == null) {
            contrato = new MutableLiveData<>();
        }
        return contrato;
    }

    public void obtenerContratoPorInmueble(Bundle idInmueble) {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = "Bearer " + ApiClient.leerToken(getApplication());
        Call<Contrato> call = api.getContratoPorInmueble(token, idInmueble.getInt("idInmueble"));
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    contrato.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("API", "Error al obtener contrato", t);
            }
        });
    }
}
