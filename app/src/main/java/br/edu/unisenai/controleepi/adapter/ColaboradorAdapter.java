package br.edu.unisenai.controleepi.adapter;

import android.view.View;
import br.edu.unisenai.controleepi.model.Colaborador;

public class ColaboradorAdapter extends RegistroAdapter<Colaborador> {
  public interface Acao {
    void abrir(Colaborador c);
  }

  private final Acao acao;

  public ColaboradorAdapter(Acao acao) {
    this.acao = acao;
  }

  protected void vincular(Holder h, Colaborador c) {
    h.titulo.setText(c.getNome());
    h.detalhe.setText("Matrícula " + c.getMatricula() + " • " + c.getSetor());
    h.acao.setVisibility(View.VISIBLE);
    h.acao.setText("Ver ficha");
    h.acao.setOnClickListener(v -> acao.abrir(c));
  }
}
