package com.example.eric.coletadedados;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by eric on 21/03/2017.
 */

public class ServicoMp3 extends Service implements InterfaceMp3 {

    private static final String CATEGORIA = "livro";
    private static final int MAX = 10;
    protected int count;
    private boolean ativo;
    private final ConexaoInterfaceMp3 conexao = new ConexaoInterfaceMp3();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(CATEGORIA, "ServicoMp3onBind(). Aqui retorna o IBinder");
        return conexao;
    }

    @Override
    public void onCreate() {
        Log.i(CATEGORIA, "ServicoMp3 onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(CATEGORIA, "ServicoMp3 onStart()");
        //setThread();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ativo = false;
    }

    public void setThread(){
        Log.i(CATEGORIA, "Entrou no SetThread");
        count = 0;
        ativo = true;
        new Thread(){
            public void run(){
                while (ativo && count < MAX){
                    try{
                        Log.i(CATEGORIA, "ExemploServico executando... " + count);
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    count++;
                }
                Log.i(CATEGORIA, "ExemploServico fim.");
                stopSelf();
            }
        }.start();
    }



    @Override
    public void start() {
        setThread();
    }

    @Override
    public int contador() {
        Log.i(CATEGORIA, "Chamou o método contador() da classe ServicoMp3");
        return count;
    }

    @Override
    public void stop() {
        Log.i(CATEGORIA, "Chamou o método stop() da classe ServicoMp3");
        stopSelf();
    }

    public class ConexaoInterfaceMp3 extends Binder{
        public InterfaceMp3 getService(){
            return ServicoMp3.this;
        }
    }


}
