package com.example.eric.coletadedados;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ronaldo/vitor on 20/04/2017.
 */


public class Pontos {
    private static final String CATEGORIA = "livro";

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE})
    public static String getPontosProximos() {

        String result = "";
        Context context = ApplicationContextProvider.getContext();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo Info = cm.getActiveNetworkInfo();

        if (Info == null || !Info.isConnectedOrConnecting()) {
            Log.i(CATEGORIA, "No connection");
        } else {
            int netType = Info.getType();
            int netSubtype = Info.getSubtype();

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            List<ScanResult> scanResult = wifiManager.getScanResults();
            //result = ""+scanResult.size()+" ";
            for (int i = 0; i < scanResult.size(); i++) {
                //Log.d("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal
                result = result + scanResult.get(i).BSSID + "/" + scanResult.get(i).frequency + "/" + scanResult.get(i).level + " ";
            }

            wifiManager.startScan();
        }

        return result;
    }
}
