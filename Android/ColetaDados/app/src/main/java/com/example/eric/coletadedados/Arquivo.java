package com.example.eric.coletadedados;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by eric on 23/03/2017.
 */

public class Arquivo {
    private static final String CATEGORIA = "livro";
    private static final String ARQUIVO = "arquivo.txt";

    protected void salvar(String linha){
        Log.i(CATEGORIA, "salvar()");
        try{
            File f = getFile(ARQUIVO);
            FileOutputStream out = new FileOutputStream(f, true);
            out.write("\n".getBytes());
            out.write(linha.getBytes());
            out.close();
            Log.i(CATEGORIA, "Linha: " + linha);
            Log.i(CATEGORIA, "Escrito com sucesso");
        }catch (FileNotFoundException e){
            Log.e(CATEGORIA, e.getMessage(), e);
        }catch (IOException e){
            Log.e(CATEGORIA, e.getMessage(), e);
        }
    }

    public static File getFile(String nomeArquivo){
        Log.i(CATEGORIA, "getFile()");
        String extStore = System.getenv("EXTERNAL_STORAGE");
        File dirLocal = new File(extStore);
        if (!dirLocal.exists()){
            dirLocal.mkdir();
        }
        File file = new File(dirLocal, nomeArquivo);
        return file;
    }
}


