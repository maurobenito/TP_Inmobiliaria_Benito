package com.example.inmobiliariabenito.ui.contrato;

import androidx.lifecycle.ViewModel;



import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.modelo.Inmueble;
import com.example.inmobiliariabenito.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> listaInmueblesConContrato = new MutableLiveData<>();

    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getListaInmueblesConContrato() {
        return listaInmueblesConContrato;
    }

    public void obtenerInmueblesConContrato() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();

        // 🔹 Llamada al nuevo endpoint que devuelve solo los inmuebles con contrato
        Call<List<Inmueble>> call = api.getInmueblesConContratoVigente("Bearer " + token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    listaInmueblesConContrato.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se pudieron obtener los inmuebles con contrato", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorContratoVM", throwable.getMessage());
                Toast.makeText(getApplication(), "Error al obtener inmuebles con contrato", Toast.LENGTH_LONG).show();
            }
        });
    }
}
