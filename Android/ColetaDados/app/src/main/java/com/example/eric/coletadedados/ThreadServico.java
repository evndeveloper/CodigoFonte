package com.example.eric.coletadedados;

import android.util.Log;

/**
 * Created by eric on 21/03/2017.
 */

public class ThreadServico extends Thread {

    private static final String CATEGORIA = "livro";
    protected boolean ativo = false;


    public void run(){
        Log.i(CATEGORIA, "run()");
        ativo = true;
        Localizacao localizacao = new Localizacao();
        while (ativo == true){
            try{
                Log.i(CATEGORIA, "ExemploServico executando... ");
                localizacao.chamaLocalizacao();
                Thread.sleep(5000);
            }catch (InterruptedException e){
                Log.i(CATEGORIA, "InterruptedException");
                //e.printStackTrace();
                ativo = false;
            }
        }
        ativo = false;
        Log.i(CATEGORIA, "ExemploServico fim.");

    }

    public boolean retornaAtivo(){
        return ativo;
    }
}
