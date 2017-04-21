package com.example.eric.coletadedados;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * Created by eric on 23/03/2017.
 */

public class Memoria {

    private static final String CATEGORIA = "livro";

    public static String getMemoriaInternaLivre() {
        String extStore = System.getenv("EXTERNAL_STORAGE");
        File path = new File(extStore);
        Log.i(CATEGORIA, "Memoria Interna: " + path.getPath());
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return String.valueOf((availableBlocks * blockSize));
    }

    public static String getMemoriaExternaLivre() {

        String secStore = System.getenv("SECONDARY_STORAGE");
        if(secStore != null){
            File path = new File(secStore);
            Log.i(CATEGORIA, "Pasta existe: " + path.exists());
            if (path.exists()){
                Log.i(CATEGORIA, "Memoria Externa: " + path.getPath());
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                return String.valueOf(availableBlocks * blockSize);
            }else{
                return "0";
            }
        }
        return "0";
    }

}
