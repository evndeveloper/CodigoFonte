package com.example.eric.coletadedados;

import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


/**
 * Created by eric on 22/03/2017.
 */

public class Localizacao implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String CATEGORIA = "livro";
    private GoogleApiClient mGoogleApiClient;



    public void chamaLocalizacao(){
        Log.i(CATEGORIA, "chamaLocalizacao");
        callConnection();
    }

    private synchronized void callConnection() {
        Log.i(CATEGORIA, "callConnection()");
        mGoogleApiClient = new GoogleApiClient.Builder(ApplicationContextProvider.getContext())
                .addOnConnectionFailedListener(Localizacao.this)
                .addConnectionCallbacks(Localizacao.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(CATEGORIA, "onConnected()");
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(l != null){
            Log.i(CATEGORIA, "latitude: "+l.getLatitude());
            Log.i(CATEGORIA, "longitude: "+l.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(CATEGORIA, "onConnectionSuspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(CATEGORIA, "onConnectionFailed: " + connectionResult);
    }


}
