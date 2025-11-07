package com.example.inmobiliariabenito.ui.inquilino;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.modelo.Pago;
import com.example.inmobiliariabenito.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Pago>> pagos = new MutableLiveData<>();

    public DetallePagoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getPagos() {
        return pagos;
    }

    // Llamada al API
    public void setIdContrato(int idContrato) {
        String token = "Bearer " + ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();

        Call<List<Pago>> call = api.getPagosPorContrato(token, idContrato);
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                pagos.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                pagos.postValue(List.of()); // enviar lista vacía en caso de fallo
            }
        });
    }
}
