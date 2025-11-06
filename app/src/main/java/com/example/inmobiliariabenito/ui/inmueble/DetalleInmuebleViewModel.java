package com.example.inmobiliariabenito.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.modelo.Inmueble;
import com.example.inmobiliariabenito.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void setMInmueble(MutableLiveData<Inmueble> mInmueble) {
        this.mInmueble = mInmueble;
    }

    public void recuperarInmueble(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.get("inmuebleBundle");
        if (inmueble != null) {
            mInmueble.setValue(inmueble);
        }
    }


    public void actualizarDisponibleLocal(boolean disponible) {
        Inmueble actual = mInmueble.getValue();
        if (actual != null) {
            actual.setDisponible(disponible);
            mInmueble.setValue(actual);
        }
    }


    public void guardarCambios() {
        Inmueble actual = mInmueble.getValue();
        if (actual != null) {
            actualizarInmueble(actual);
        }
    }

    public void actualizarInmueble(Inmueble inmueble) {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = "Bearer " + ApiClient.leerToken(getApplication());
        Call<Inmueble> call = api.actualizarInmueble(token, inmueble);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("InmuebleVM", "Error en la respuesta: " + response.code());
                    Toast.makeText(getApplication(), "No se pudo actualizar el inmueble (" + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("errorActualizar", "Error: " + t.getMessage());
            }
        });
    }
}
