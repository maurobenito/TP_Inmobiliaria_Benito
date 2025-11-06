package com.example.inmobiliariabenito.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariabenito.modelo.Propietario;
import com.example.inmobiliariabenito.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getmEstado() {
        return mEstado; }

    public LiveData<String> getmNombre() {
        return mNombre; }

    public LiveData<Propietario> propietario() {
        return propietario; }

    public void cambioBoton(String nombreBoton, String nombre, String apellido, String dni, String telefono, String mail) {
        if (nombreBoton.equalsIgnoreCase("EDITAR")) {
            mEstado.setValue(true);
            mNombre.setValue("GUARDAR");
        } else {

            if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || dni.trim().isEmpty()
                    || telefono.trim().isEmpty() || mail.trim().isEmpty()) {
                Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!dni.matches("\\d+")) {
                Toast.makeText(getApplication(), "El DNI debe contener solo números", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(getApplication(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                return;
            }


            mEstado.setValue(false);
            mNombre.setValue("EDITAR");
            mEstado.setValue(false);
            mNombre.setValue("EDITAR");

            Propietario nuevo = new Propietario();
            nuevo.setIdPropietario(propietario.getValue().getIdPropietario());
            nuevo.setNombre(nombre);
            nuevo.setApellido(apellido);
            nuevo.setDni(dni);
            nuevo.setTelefono(telefono);
            nuevo.setEmail(mail);


            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call = api.ActualizarPropietario("Bearer " + token, nuevo);

            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "ACTUALIZADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                        propietario.postValue(response.body()); // Actualiza los datos en pantalla
                    } else {
                        Toast.makeText(getApplication(), "ERROR AL ACTUALIZAR: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("PerfilViewModel", "Error al actualizar: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("PerfilViewModel", "Error en API: ", t);
                }
            });
        }
    }

    public void obtenerPerfil() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.getPropietario("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener perfil: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("PerfilViewModel", "Error en respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PerfilViewModel", "Error en API: ", t);
            }
        });
    }
}
