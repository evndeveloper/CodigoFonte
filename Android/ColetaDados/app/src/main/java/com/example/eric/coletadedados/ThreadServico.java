package com.example.eric.coletadedados;

import android.util.Log;

/**
 * Created by eric on 21/03/2017.
 */

public class ThreadServico extends Thread {

    private static final String CATEGORIA = "livro";
    private static final int MAX = 100;
    protected int count;


    public void run(){
        Log.i(CATEGORIA, "Entrou no SetThread");
        count = 0;

        while (count < MAX){
            try{
                Log.i(CATEGORIA, "ExemploServico executando... " + count);
                Thread.sleep(1000);
            }catch (InterruptedException e){
                //e.printStackTrace();
                count = 100;
            }
            count++;
        }
        Log.i(CATEGORIA, "ExemploServico fim.");

    }

    public int retornaContador(){
        return count;
    }
}
