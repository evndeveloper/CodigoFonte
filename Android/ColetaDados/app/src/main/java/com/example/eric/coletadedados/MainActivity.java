package com.example.eric.coletadedados;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CATEGORIA = "livro";

    private InterfaceMetodos interfaceMetodos;
    private Button btnIniciar;
    private Button btnParar;
    private Button btnContador;
    public static TextView tvStatus;
    private TextView tvGravacao;




    private ServiceConnection conexao = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ONSERVICO", "onServiceConnected, serviço conectado");
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

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);


        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvGravacao = (TextView) findViewById(R.id.tv_gravacao);
        tvGravacao.setText("Local de Gravação: " + System.getenv("EXTERNAL_STORAGE") + "/coleta.txt");

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(this);

        btnParar = (Button) findViewById(R.id.btnParar);
        btnParar.setEnabled(false);
        btnParar.setOnClickListener(this);

        btnContador = (Button) findViewById(R.id.btnContador);
        btnContador.setOnClickListener(this);

        Log.i(CATEGORIA, "Chamando startService()...");
        MainActivity mainActivity = this;
        Intent startService = new Intent(this, Servico.class);
        //startService.putExtra("OBJETO", mainActivity);
        startService(startService);

        Log.i(CATEGORIA, "Chamando bindService()...");
        boolean b = bindService(startService, conexao, 0);
        Log.i(CATEGORIA, "bindService retorno: " + b);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(CATEGORIA, "onResume() MainActivity");
        if (interfaceMetodos != null){
            boolean status = interfaceMetodos.retornaStatus();
            if (status){
                tvStatus.setText("Status: Coletando Dados...");
                btnIniciar.setEnabled(false);
                btnParar.setEnabled(true);
            }else {
                tvStatus.setText("Status: Coleta Parada");
                btnIniciar.setEnabled(true);
                btnParar.setEnabled(false);
            }
        }

    }

    @Override
    public void onClick(View view) {
        try {
            if (view == btnIniciar){
                btnIniciar.setEnabled(false);
                btnParar.setEnabled(true);
               interfaceMetodos.start();

            }else if (view == btnParar){
                Log.i(CATEGORIA, "Parando o servico...");
                btnIniciar.setEnabled(true);
                btnParar.setEnabled(false);
                interfaceMetodos.stop();

            }else if (view == btnContador){
                Intent irParaVisualizacao = new Intent(this, ActivityVisualizacao.class);
                startActivity(irParaVisualizacao);

            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CATEGORIA, "Activity destrída! Mas o serviço continua...");
        unbindService(conexao);



    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
