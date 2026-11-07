package br.edu.unisenai.controleepi;

import android.content.Intent;
import br.edu.unisenai.controleepi.adapter.EpiAdapter;
import br.edu.unisenai.controleepi.data.EpiRepository;
import br.edu.unisenai.controleepi.data.EstoqueRepository;
import br.edu.unisenai.controleepi.model.Epi;
import br.edu.unisenai.controleepi.util.MensagensErro;
import com.google.firebase.firestore.*;
import java.util.*;

public class EpisActivity extends ListaActivity {
  private EpiAdapter adapter;

  protected boolean vencimentos() {
    return false;
  }

  protected void configurar() {
    titulo(vencimentos() ? "CAs vencidos e vencendo" : "EPIs e certificados");
    adapter = new EpiAdapter();
    if (!vencimentos()) {
      adapter.aoAbrirEstoque(epi ->
          startActivity(
              new Intent(this, EstoqueActivity.class)
                  .putExtra("epiId", epi.getId())
                  .putExtra("descricao", epi.getDescricao())));
    }
    lista.setAdapter(adapter);
    if (vencimentos()) semAdicionar();
    else
      findViewById(R.id.adicionar)
          .setOnClickListener(v -> startActivity(new Intent(this, CadastroEpiActivity.class)));
  }

  protected void carregar() {
    if (!vencimentos()) {
      adapter.mostrarEstoque(false);
      String dono = uid;
      new EstoqueRepository()
          .verificarDisponibilidade()
          .addOnCompleteListener(
              this,
              t -> {
                if (!sessaoAtual(dono)) return;
                if (!t.isSuccessful() && !EstoqueRepository.regrasAnteriores(t.getException())) {
                  erro(MensagensErro.traduzir(t.getException()));
                  return;
                }
                adapter.mostrarEstoque(t.isSuccessful());
              });
    }
    adapter.substituir(Collections.emptyList());
    observar(
        new EpiRepository()
            .observar(
                vencimentos(),
                (s, e) -> {
                  if (e != null) {
                    erro(MensagensErro.traduzir(e));
                    return;
                  }
                  if (s == null) return;
                  List<Epi> itens = new ArrayList<>();
                  for (DocumentSnapshot d : s.getDocuments()) {
                    try {
                      Epi item = d.toObject(Epi.class);
                      if (item != null) {
                        item.setId(d.getId());
                        itens.add(item);
                      }
                    } catch (RuntimeException ex) {
                      erro("Há um EPI com dados inválidos. Revise o cadastro no console.");
                      return;
                    }
                  }
                  adapter.substituir(itens);
                  estadoLista(itens.size(), s.getMetadata().isFromCache());
                }));
  }
}
