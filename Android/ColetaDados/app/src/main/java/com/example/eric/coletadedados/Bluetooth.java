package com.example.eric.coletadedados;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by bruno on 23/04/2017.
 */

public class Bluetooth {

    public static String getStatus() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return "NOPE";
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return "OFF";
            }else
                return "ON";
        }
    }
}
