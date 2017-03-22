package com.example.eric.coletadedados;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CATEGORIA = "livro";

    private InterfaceMp3 interfaceMp3;
    private Button btnIniciar;
    private Button btnParar;
    private Button btnContador;

    private ServiceConnection conexao = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(CATEGORIA, "onServiceConnected, serviço conectado");
            ServicoMp3.ConexaoInterfaceMp3 conexao = (ServicoMp3.ConexaoInterfaceMp3) service;
            Log.i(CATEGORIA, "Recuperada a interface para controlar o service");
            interfaceMp3 = conexao.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(CATEGORIA, "onServiceDisconnected, liberando recursos.");
            interfaceMp3 = null;
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(this);

        btnParar = (Button) findViewById(R.id.btnParar);
        btnParar.setOnClickListener(this);

        btnContador = (Button) findViewById(R.id.btnContador);
        btnContador.setOnClickListener(this);

        Log.i(CATEGORIA, "Chamando startService()...");
        startService(new Intent(this, ServicoMp3.class));

        Log.i(CATEGORIA, "Chamando bindService()...");
        boolean b = bindService(new Intent(this, ServicoMp3.class), conexao, 0);
        Log.i(CATEGORIA, "bindService retorno: " + b);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view == btnIniciar){
                //Log.i(CATEGORIA, "Chamando startService()...");
                //startService(new Intent(this, ServicoMp3.class));
                interfaceMp3.start();

            }else if (view == btnParar){
                Log.i(CATEGORIA, "Parando o servico...");
                stopService(new Intent(this, ServicoMp3.class));
                unbindService(conexao);


            }else if (view == btnContador){
                int contador = interfaceMp3.contador();
                Toast.makeText(this, "Contador: " + contador, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CATEGORIA, "Activity destrída! Mas o serviço continua...");
        //unbindService(conexao);
    }
}
