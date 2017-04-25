package com.example.eric.coletadedados;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by dkmsi on 22/04/2017.
 */

public class CheckConnectionType {

    private Context context;

    public CheckConnectionType(Context context) {
        this.context = context;
    }

    //http://stackoverflow.com/questions/2802472/detect-network-connection-type-on-android
    private NetworkInfo getNetworkInfo(){
        ConnectivityManager cm = (ConnectivityManager)
                this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo();
    }

    //http://stackoverflow.com/questions/9283765/how-to-determine-if-network-type-is-2g-3g-or-4g
    private String getNetworkType() {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                this.context.getSystemService(Context.TELEPHONY_SERVICE);

        int networkType = mTelephonyManager.getNetworkType();

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "INDEFINIDO";
        }
    }

    //http://stackoverflow.com/questions/2802472/detect-network-connection-type-on-android
    public String ConnectionType(){
        NetworkInfo info = this.getNetworkInfo();

        boolean isWifi = (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
        boolean isMobile = (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);

        if (isWifi)
            return "WIFI";
        else
        if (isMobile)
            return getNetworkType();
        else
            return "DESCONHECIDO";

    }
}
