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
    protected int count;
    private boolean ativo;
    private final ConexaoInterfaceMp3 conexao = new ConexaoInterfaceMp3();
    private ThreadServico threadAtiva;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(CATEGORIA, "ServicoMp3onBind(). Aqui retorna o IBinder");
        return conexao;
    }

    @Override
    public void onCreate() {
        Log.i(CATEGORIA, "ServicoMp3 onCreate()");
        ativo = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(CATEGORIA, "ServicoMp3 onStart()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(CATEGORIA, "Chama onDestroy");
        super.onDestroy();
        ativo = false;
    }

    @Override
    public void start() {
        Log.i(CATEGORIA, "Chama start");
        if (!ativo){
            ativo = true;
            //threadAtiva = new ThreadServico();
            //threadAtiva.start();
            Localizacao localizacao = new Localizacao();
            localizacao.chamaLocalizacao();;
        }
    }

    @Override
    public int contador() {
        Log.i(CATEGORIA, "Chamou o método contador() da classe ServicoMp3");
        count = threadAtiva.retornaContador();
        return count;
    }

    @Override
    public void stop() {
        Log.i(CATEGORIA, "Chamou o método stop() da classe ServicoMp3");
        ativo = false;
        threadAtiva.interrupt();
    }

    public class ConexaoInterfaceMp3 extends Binder{
        public InterfaceMetodos getService(){
            return Servico.this;
        }
    }


}
