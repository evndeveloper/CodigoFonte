package com.example.eric.coletadedados;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CATEGORIA = "livro";

    private InterfaceMetodos interfaceMetodos;
    private Button btnIniciar;
    private Button btnParar;
    private Button btnContador;


    private ServiceConnection conexao = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(CATEGORIA, "onServiceConnected, serviço conectado");
            Servico.ConexaoInterfaceMp3 conexao = (Servico.ConexaoInterfaceMp3) service;
            Log.i(CATEGORIA, "Recuperada a interface para controlar o service");
            interfaceMetodos = conexao.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(CATEGORIA, "onServiceDisconnected, liberando recursos.");
            interfaceMetodos = null;
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
        startService(new Intent(this, Servico.class));

        Log.i(CATEGORIA, "Chamando bindService()...");
        boolean b = bindService(new Intent(this, Servico.class), conexao, 0);
        Log.i(CATEGORIA, "bindService retorno: " + b);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view == btnIniciar){
                interfaceMetodos.start();

            }else if (view == btnParar){
                Log.i(CATEGORIA, "Parando o servico...");
                interfaceMetodos.stop();

            }else if (view == btnContador){
                boolean status = interfaceMetodos.retornaStatus();
                if (status){
                    Toast.makeText(this, "Status: Ativo", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Status: Desativado", Toast.LENGTH_SHORT).show();
                }

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
