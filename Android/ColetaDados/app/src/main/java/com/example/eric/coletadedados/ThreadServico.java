package com.example.eric.coletadedados;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eric on 21/03/2017.
 */

public class ThreadServico extends Thread {

    private static final String CATEGORIA = "livro";
    protected boolean ativo = false;


    public void run(){
        Log.i(CATEGORIA, "run()");
        ativo = true;
        //Localizacao localizacao = new Localizacao();
        LocApi locApi = new LocApi();
        Bateria bateria = new Bateria();
        Memoria memoria = new Memoria();
        String linha;
        Arquivo arquivo = new Arquivo();
        while (ativo == true){
            try{
                Log.i(CATEGORIA, "ExemploServico executando... ");
                //localizacao.chamaLocalizacao();
                Thread.sleep(5000);

                //String ult_localizacao = localizacao.retornaLocalizacao();
                String ult_localizacao = locApi.retornaLocalizacao();
                int ult_bateria = bateria.getPorcentagem();
                String ult_memIntLivre = memoria.getMemoriaInternaLivre();
                String ult_memExtLivre = memoria.getMemoriaExternaLivre();

                Log.i(CATEGORIA, "Retorno Localização: " + ult_localizacao);
                Log.i(CATEGORIA, "Retorno Bateria: " + ult_bateria + "%");
                Log.i(CATEGORIA, "Retorno Memoria Interna Livre: " + ult_memIntLivre + " bytes");
                Log.i(CATEGORIA, "Retorno Memoria Externa Livre: " + ult_memExtLivre + " bytes");

                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                String dataCorrente = data.format(new Date());

                SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                String horaCorrente = hora.format(new Date());

                linha = dataCorrente + ";" + horaCorrente + ";" + ult_localizacao + ";" + ult_bateria + ";" +
                        ult_memIntLivre + ";" + ult_memExtLivre;
                arquivo.salvar(linha);

            }catch (InterruptedException e){
                Log.i(CATEGORIA, "InterruptedException");
                //e.printStackTrace();
                ativo = false;
            }
        }
        ativo = false;


    }

    public boolean retornaAtivo(){
        return ativo;
    }
}
