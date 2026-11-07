package br.edu.unisenai.controleepi.adapter;

import android.view.View;
import br.edu.unisenai.controleepi.model.Epi;
import br.edu.unisenai.controleepi.util.Datas;

public class EpiAdapter extends RegistroAdapter<Epi> {
  public interface AcaoEstoque {
    void abrir(Epi epi);
  }

  private AcaoEstoque acaoEstoque;
  private boolean estoqueVisivel;

  public void aoAbrirEstoque(AcaoEstoque acao) {
    acaoEstoque = acao;
  }

  public void mostrarEstoque(boolean visivel) {
    estoqueVisivel = visivel;
    notifyDataSetChanged();
  }

  protected void vincular(Holder h, Epi e) {
    h.titulo.setText(e.getDescricao());
    h.detalhe.setText(
        "CA "
            + e.getCa()
            + "\nValidade: "
            + Datas.formatar(e.getValidadeCa())
            + "\n"
            + Datas.status(e.getValidadeCa()));
    if (estoqueVisivel && acaoEstoque != null) {
      h.acao.setText("Estoque");
      h.acao.setVisibility(View.VISIBLE);
      h.acao.setOnClickListener(v -> acaoEstoque.abrir(e));
    }
  }
}
