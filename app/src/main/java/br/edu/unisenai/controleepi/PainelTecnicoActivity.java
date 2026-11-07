package br.edu.unisenai.controleepi;

import android.content.Intent;
import android.os.Bundle;

public class PainelTecnicoActivity extends BaseActivity {
  protected void criarTela(Bundle b) {
    getLayoutInflater().inflate(R.layout.activity_painel, corpo, true);
    titulo("ControleEPI • Técnico");
  }

  protected void carregar() {
    ligar(R.id.novaEntrega, NovaEntregaActivity.class);
    ligar(R.id.epis, EpisActivity.class);
    ligar(R.id.colaboradores, ColaboradoresActivity.class);
    ligar(R.id.vencimentos, CasVencendoActivity.class);
  }

  private void ligar(int id, Class<?> c) {
    findViewById(id).setOnClickListener(v -> startActivity(new Intent(this, c)));
  }
}
