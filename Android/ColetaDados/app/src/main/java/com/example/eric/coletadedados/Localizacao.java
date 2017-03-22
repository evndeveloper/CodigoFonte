package com.example.eric.coletadedados;

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

public class Localizacao extends MainActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String CATEGORIA = "livro";
    private GoogleApiClient mGoogleApiClient;

    public void chamaLocalizacao(){
        Log.i(CATEGORIA, "chamaLocalizacao");
        callConnection();
    }

    private synchronized void callConnection() {
        Log.i(CATEGORIA, "callConnection()");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(Localizacao.this)
                .addConnectionCallbacks(Localizacao.this)
                .addApi(LocationServices.API)
                .build();
        Log.i(CATEGORIA, "connect");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle + ")");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(l != null){
            Log.i("LOG", "latitude: "+l.getLatitude());
            Log.i("LOG", "longitude: "+l.getLongitude());
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
