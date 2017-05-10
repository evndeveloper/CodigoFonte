package com.example.eric.coletadedados;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Looper.getMainLooper;

/**
 * Created by eric on 21/03/2017.
 */

public class ThreadServico extends Thread {

    private static final String CATEGORIA = "livro";
    protected boolean ativo = false;
    private CheckConnectionType connectionType;
    private Pontos pontos;

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void run(){
        Log.i(CATEGORIA, "run()");
        ativo = true;
        //Localizacao localizacao = new Localizacao();
        LocApi locApi = new LocApi();
        Bateria bateria = new Bateria();
        Memoria memoria = new Memoria();
        WiFi wifi = new WiFi();
        Bluetooth bluetooth = new Bluetooth();
        this.connectionType = new CheckConnectionType(ApplicationContextProvider.getContext());
        this.pontos = new Pontos(); //Ronaldo/Vitor

        mManager = (WifiP2pManager) ApplicationContextProvider.getContext().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(ApplicationContextProvider.getContext(), getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        String linha;
        Arquivo arquivo = new Arquivo();
        while (ativo == true){
            try{
                Log.i(CATEGORIA, "ExemploServico executando... ");
                //localizacao.chamaLocalizacao();
                Thread.sleep(10000);
                ApplicationContextProvider.getContext().registerReceiver(mReceiver, mIntentFilter);

                //String ult_localizacao = localizacao.retornaLocalizacao();
                String ult_localizacao = locApi.retornaLocalizacao();
                if(ult_localizacao != ""){
                    Log.i(CATEGORIA, "LOCALIZAÇÃO ESTÁ DENTRO DA UFAM");
                    int ult_bateria = bateria.getPorcentagem();
                    String ult_memIntLivre = memoria.getMemoriaInternaLivre();
                    String ult_memExtLivre = memoria.getMemoriaExternaLivre();

                    //Inicio Status do WiFi e do Bluetooth (Bruno, Felipe, Wagner)
                    String statusWiFi = wifi.getStatus();
                    String statusBluetooth = bluetooth.getStatus();
                    //Fim Status do WiFi e do Bluetooth (Bruno, Felipe, Wagner)

                    //CHAMA MÉTODO DO TIPO DE CONEXÃO DE DADOS
                    String connType = this.connectionType.ConnectionType();

                    //CHAMA MÉTODO DE SCAN PONTOS WIFI
                    String pontosProximos = pontos.getPontosProximos(); //Ronaldo/Vitor

                    Log.i(CATEGORIA, "Retorno Localização: " + ult_localizacao);
                    Log.i(CATEGORIA, "Retorno Bateria: " + ult_bateria + "%");
                    Log.i(CATEGORIA, "Retorno Memoria Interna Livre: " + ult_memIntLivre + " bytes");
                    Log.i(CATEGORIA, "Retorno Memoria Externa Livre: " + ult_memExtLivre + " bytes");

                    //Inicio Status do WiFi e do Bluetooth (Bruno, Felipe, Wagner)
                    Log.i(CATEGORIA, "Status do WiFi: " + statusWiFi);
                    Log.i(CATEGORIA, "Status do Bluetooth: " + statusBluetooth);
                    //Fim Status do WiFi e do Bluetooth (Bruno, Felipe, Wagner)
                    Log.d(CATEGORIA, "Retorno Tipo de conexão: " + connType);
                    //adição ronaldo/vitor
                    Log.i(CATEGORIA, "Retorno Pontos Proximos: " + pontosProximos);

                    SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                    String dataCorrente = data.format(new Date());

                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                    String horaCorrente = hora.format(new Date());

                    linha = dataCorrente + ";" + horaCorrente + ";" + ult_localizacao + ";" + ult_bateria + ";" +
                            ult_memIntLivre + ";" + ult_memExtLivre + ";" + statusWiFi + ";" + statusBluetooth +
                            ';' + connType + ";" + ApplicationContextProvider.getSuportep2p() + ";" + pontosProximos;
                    arquivo.salvar(linha);
                }else{
                    Log.i(CATEGORIA, "LOCALIZAÇÃO NÃO ESTÁ DENTRO DA UFAM");
                }

            }catch (InterruptedException e){
                Log.i(CATEGORIA, "InterruptedException");
                //e.printStackTrace();
                ativo = false;
            }
        }
        ativo = false;
        ApplicationContextProvider.getContext().unregisterReceiver(mReceiver);
    }

    public boolean retornaAtivo(){
        return ativo;
    }
}
