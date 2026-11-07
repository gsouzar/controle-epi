package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.*;

public abstract class ListaActivity extends BaseActivity {
  protected RecyclerView lista;

  @Override
  protected void criarTela(Bundle b) {
    getLayoutInflater().inflate(R.layout.activity_lista, corpo, true);
    lista = findViewById(R.id.lista);
    lista.setLayoutManager(new LinearLayoutManager(this));
    configurar();
  }

  protected abstract void configurar();

  protected void estadoLista(int tamanho, boolean cache) {
    findViewById(R.id.carregando).setVisibility(View.GONE);
    findViewById(R.id.vazio).setVisibility(tamanho == 0 ? View.VISIBLE : View.GONE);
    ((TextView) findViewById(R.id.resumo))
        .setText(cache ? "Aguardando sincronização com o servidor…" : tamanho + " registro(s)");
  }

  protected void semAdicionar() {
    findViewById(R.id.adicionar).setVisibility(View.GONE);
  }
}
