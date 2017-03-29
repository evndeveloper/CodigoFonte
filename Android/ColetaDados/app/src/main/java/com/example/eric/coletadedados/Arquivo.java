package com.example.eric.coletadedados;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by eric on 23/03/2017.
 */

public class Arquivo {
    private static final String CATEGORIA = "livro";
    private static final String ARQUIVO = "coleta.txt";


    protected void salvar(String linha){
        Log.i(CATEGORIA, "salvar()");
        try{

            int permissionCheck = ContextCompat.checkSelfPermission(ApplicationContextProvider.getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                File f = getFile(ARQUIVO);
                FileOutputStream out = new FileOutputStream(f, true);
                out.write("\n".getBytes());
                out.write(linha.getBytes());
                out.close();
                Log.i(CATEGORIA, "Linha: " + linha);
                Log.i(CATEGORIA, "Escrito com sucesso");
            }else{
                Log.i(CATEGORIA, "Sem permissão para escrita");
            }


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

    public String visualizarArquivo(){
        Log.i(CATEGORIA, "visualizarArquivo()");

        String lstrlinha;
        String resposta = "";

        File f = getFile(ARQUIVO);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((lstrlinha = br.readLine()) != null){
                if (resposta.equals(""))
                {
                    resposta = lstrlinha;
                }else{
                    resposta += "\n" + lstrlinha;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(CATEGORIA, "Resposta: " + resposta);
        return resposta;
    }
}


