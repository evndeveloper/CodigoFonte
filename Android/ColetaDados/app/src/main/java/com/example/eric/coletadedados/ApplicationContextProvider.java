package com.example.eric.coletadedados;

import android.app.Application;
import android.content.Context;

/**
 * Created by eric on 22/03/2017.
 */

public class ApplicationContextProvider extends Application {

    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    private static String suportep2p;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }

    public static String getSuportep2p() {
        return suportep2p;
    }

    public static void setSuportep2p(String suportep2p) {
        ApplicationContextProvider.suportep2p = suportep2p;
    }
}
