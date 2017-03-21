package com.example.eric.coletadedados;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by eric on 20/03/2017.
 */

public class Servico extends Service implements Runnable, Contador {

    private final IBinder conexao = new LocalBinder();
    private static final int MAX = 10;
    private static final String CATEGORIA = "livro";
    protected int count;
    private boolean ativo;

    public class LocalBinder extends Binder{
        public Contador getObjetoServico(){
            return Servico.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return conexao;
    }

    @Override
    public void onCreate() {
        Log.i(CATEGORIA, "ExemploServico.onCreate()");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(CATEGORIA, "ExemploServico.onStartCommand");
        count = 0;
        ativo = true;
        new Thread(this, "ExemploServico-" + startId).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {
        while (ativo && count < MAX){
            fazAlgumaCoisa();
            Log.i(CATEGORIA, "ExemploServico executando... " + count);
            count++;
        }
        Log.i(CATEGORIA, "ExemploServico fim.");
        stopSelf();
    }

    private void fazAlgumaCoisa(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        ativo = false;
        Log.i(CATEGORIA, "ExemploServico.onDestroy()");
    }

    @Override
    public int retornaContador() {
        return count;
    }
}
