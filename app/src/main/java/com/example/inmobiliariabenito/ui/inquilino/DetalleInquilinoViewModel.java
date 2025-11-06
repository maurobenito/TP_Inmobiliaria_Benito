package com.example.inmobiliariabenito.ui.inquilino;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.inmobiliariabenito.modelo.Inquilino;

public class DetalleInquilinoViewModel extends ViewModel {

    private MutableLiveData<Inquilino> inquilinoMutableLiveData = new MutableLiveData<>();

    public LiveData<Inquilino> getInquilinoMutableLiveData() {
        return inquilinoMutableLiveData;
    }

    public void cargarInquilino(Bundle bundle) {
        if (bundle != null) {
            Inquilino inquilino = (Inquilino) bundle.getSerializable("inquilino");
            if (inquilino != null) {
                inquilinoMutableLiveData.setValue(inquilino);
            }
        }
    }
}
