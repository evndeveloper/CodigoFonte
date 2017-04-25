package com.example.eric.coletadedados;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by Thais on 23/04/2017.
 */

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;
    private static final String CATEGORIA = "livro";

   public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        //this.mActivity = activity;
    }

     @Override
     public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Arquivo arq = new Arquivo();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                ApplicationContextProvider.setSuportep2p("SUPORTE");
                Log.i(CATEGORIA,"SUPORTE WIFI DIRECT: true");
            } else {
                ApplicationContextProvider.setSuportep2p("NOPE");
                Log.i(CATEGORIA,"SUPORTE WIFI DIRECT: false");
            }
        }
    }
}
