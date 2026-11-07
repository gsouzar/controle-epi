package br.edu.unisenai.controleepi.adapter;

import android.view.View;
import br.edu.unisenai.controleepi.model.Entrega;
import br.edu.unisenai.controleepi.util.Datas;
import java.util.*;

public class EntregaAdapter extends RegistroAdapter<Entrega> {
  public interface Acao {
    void confirmar(Entrega e);
  }

  private final Acao acao;
  private final Set<String> enviando = new HashSet<>();

  public EntregaAdapter(Acao acao) {
    this.acao = acao;
  }

  public void pendentes(Set<String> ids) {
    enviando.clear();
    enviando.addAll(ids);
    notifyDataSetChanged();
  }

  public void enviando(String id, boolean ativo) {
    if (ativo) enviando.add(id);
    else enviando.remove(id);
    notifyDataSetChanged();
  }

  protected void vincular(Holder h, Entrega e) {
    h.titulo.setText(e.getEpiDescricao());
    h.detalhe.setText(
        "CA "
            + e.getEpiCa()
            + " • Quantidade: "
            + e.getQuantidade()
            + "\n"
            + Datas.formatar(e.getData())
            + "\n"
            + (e.isConfirmado() ? "Confirmado" : "Pendente de recebimento")
            + (e.getMotivoTroca() == null ? "" : "\nMotivo: " + e.getMotivoTroca()));
    if (acao != null && !e.isConfirmado()) {
      h.acao.setVisibility(View.VISIBLE);
      h.acao.setText(enviando.contains(e.getId()) ? "Enviando…" : "Confirmar recebimento");
      h.acao.setEnabled(!enviando.contains(e.getId()));
      h.acao.setOnClickListener(v -> acao.confirmar(e));
    }
  }
}
