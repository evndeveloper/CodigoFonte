package com.example.eric.coletadedados;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

    private static final String CATEGORIA = "livro";
    private Contador objServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ServiceConnection conexao = this;
        final Intent it = new Intent("SERVICE 1");
        Button btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(CATEGORIA, "Iniciar Serviço - bindService");
                Class classeServico = Servico.class;
                bindService(new Intent(MainActivity.this, classeServico), conexao, Context.BIND_AUTO_CREATE);
            }
        });

        Button btnParar = (Button) findViewById(R.id.btnParar);
        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(CATEGORIA, "Parar Service - unbindService");
                unbindService(conexao);
            }
        });

        Button btnContador = (Button) findViewById(R.id.btnContador);
        btnContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = objServico.retornaContador();
                Log.i(CATEGORIA, "Contador: " + count);
                Toast.makeText(MainActivity.this, "Contador: " + count, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CATEGORIA, "ExemploIniciarServico.onDestroy()");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Servico.LocalBinder binder = (Servico.LocalBinder) service;
        objServico = binder.getObjetoServico();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        objServico = null;
    }
}
