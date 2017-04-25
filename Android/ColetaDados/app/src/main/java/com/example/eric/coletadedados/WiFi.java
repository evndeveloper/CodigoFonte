package com.example.eric.coletadedados;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by bruno on 23/04/2017.
 */

public class WiFi {

    Context context = ApplicationContextProvider.getContext();

    public String getStatus() {

        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            return "ON";
        }else
            return "OFF";
    }

}
