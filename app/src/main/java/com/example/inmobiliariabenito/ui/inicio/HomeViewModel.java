package com.example.inmobiliariabenito.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<MapaActual> mutableMapaActual;


    public HomeViewModel(android.app.Application application) {
        super(application);
    }



    public LiveData<MapaActual> getMutableMapaActual(){
        if (mutableMapaActual == null){
            mutableMapaActual = new MutableLiveData<>();

        }
        return mutableMapaActual;
    }
    public void cargarMapa(){
        MapaActual mapaActual = new MapaActual();
        mutableMapaActual.setValue(mapaActual);
    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng SANLUIS = new LatLng(-33.280576, -66.332582);
        LatLng ULP = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("Inmobiliaria Benito"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(SANLUIS)      // Sets the center of the map to Mountain View
                    .zoom(10)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition( cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }

    }





}