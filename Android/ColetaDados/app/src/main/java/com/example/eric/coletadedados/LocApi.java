package com.example.eric.coletadedados;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

/**
 * Created by eric on 30/03/2017.
 */

public class LocApi {

    private static final String CATEGORIA = "livro";
    private Context contexto = ApplicationContextProvider.getContext();
    public String localizacao = "";
    public Double minLat = -3.1047000;
    public Double minLong = -58.9839000;
    public Double maxLat = -3.0857000;
    public Double maxLong = -59.9600000;

    public String retornaLocalizacao() {

        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i(CATEGORIA, "Permissão negada retornaLocalizacao()");

            localizacao = "";
        } else {
            TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setUsePassive(true)
                            .setTimeBetweenUpdates(5 * 1000)
                            .setMetersBetweenUpdates(1);

            final LocationTracker tracker = new LocationTracker(contexto, settings) {
                @Override
                public void onLocationFound(Location location) {
                    Log.i(CATEGORIA, "onLocationFound()");
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    if(latitude >= minLat && latitude <= maxLat){
                        if(longitude >= minLong && longitude <= maxLong){
                            localizacao = Double.toString(location.getLatitude()) + ";" + Double.toString(location.getLongitude());
                        }else{
                            localizacao = "";
                        }
                    }else{
                        localizacao = "";
                    }

                    stopListening();
                }

                @Override
                public void onTimeout() {
                    Log.i(CATEGORIA, "onTimeout()");
                    localizacao = "";
                }
            };

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {

                    if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }else{
                        Log.i(CATEGORIA, "tracker.startListening()");
                        tracker.startListening();
                    }

                }
            });

        }

        return localizacao;
    }
}
