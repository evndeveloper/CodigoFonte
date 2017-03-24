package com.example.eric.coletadedados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityVisualizacao extends AppCompatActivity {

    private TextView tvArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao);

        tvArquivo = (TextView) findViewById(R.id.tv_arquivo);

        Arquivo arquivo = new Arquivo();
        tvArquivo.setText(arquivo.visualizarArquivo());
    }
}
