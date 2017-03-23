package com.example.eric.coletadedados;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by eric on 21/03/2017.
 */

public class Servico extends Service implements InterfaceMetodos {

    private static final String CATEGORIA = "livro";
    private boolean ativo;
    private final ConexaoInterfaceMp3 conexao = new ConexaoInterfaceMp3();
    private ThreadServico threadAtiva;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(CATEGORIA, "onBind(). Aqui retorna o IBinder");
        return conexao;
    }

    @Override
    public void onCreate() {
        Log.i(CATEGORIA, "onCreate()");
        ativo = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(CATEGORIA, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(CATEGORIA, "onDestroy");
        super.onDestroy();
        ativo = false;
    }

    @Override
    public void start() {
        Log.i(CATEGORIA, "start()");
        if (!ativo){
            ativo = true;
            threadAtiva = new ThreadServico();
            threadAtiva.start();
        }
    }

    @Override
    public boolean retornaStatus() {
        Log.i(CATEGORIA, "Chamou o método retornaStatus()");
        ativo = threadAtiva.retornaAtivo();
        return ativo;
    }

    @Override
    public void stop() {
        Log.i(CATEGORIA, "Chamou o método stop()");
        ativo = false;
        threadAtiva.interrupt();
    }

    public class ConexaoInterfaceMp3 extends Binder{
        public InterfaceMetodos getService(){
            return Servico.this;
        }
    }


}
