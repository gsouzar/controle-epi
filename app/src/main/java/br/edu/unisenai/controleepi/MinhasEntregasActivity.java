package br.edu.unisenai.controleepi;

import br.edu.unisenai.controleepi.adapter.EntregaAdapter;
import br.edu.unisenai.controleepi.data.EntregaRepository;
import br.edu.unisenai.controleepi.model.Entrega;
import br.edu.unisenai.controleepi.util.MensagensErro;
import com.google.firebase.firestore.*;
import java.util.*;

public class MinhasEntregasActivity extends ListaActivity {
  protected EntregaAdapter adapter;
  protected final EntregaRepository repository = new EntregaRepository();

  @Override
  protected String perfilExigido() {
    return "colaborador";
  }

  protected String colaboradorUid() {
    return uid;
  }

  protected boolean permiteConfirmar() {
    return true;
  }

  protected void configurar() {
    titulo("Minhas entregas");
    semAdicionar();
    adapter = new EntregaAdapter(permiteConfirmar() ? this::confirmar : null);
    lista.setAdapter(adapter);
  }

  protected void carregar() {
    String dono = colaboradorUid();
    if (dono == null || dono.isEmpty() || dono.contains("/")) {
      erro("Colaborador não informado.");
      return;
    }
    adapter.substituir(Collections.emptyList());
    if (permiteConfirmar())
      observar(
          FirebaseFirestore.getInstance()
              .collection("colaboradores")
              .document(uid)
              .addSnapshotListener((d, e) -> {
                if (e != null) {
                  erro(MensagensErro.traduzir(e));
                  return;
                }
                if (d != null && !d.exists() && !d.getMetadata().isFromCache())
                  erro("Sua conta aguarda o vínculo de nome, matrícula e setor pelo técnico.");
              }));
    observar(
        repository.observar(
            dono,
            (s, e) -> {
              if (e != null) {
                erro(MensagensErro.traduzir(e));
                return;
              }
              if (s == null) return;
              List<Entrega> itens = new ArrayList<>();
              Set<String> pendentes = new HashSet<>();
              for (DocumentSnapshot d : s.getDocuments()) {
                try {
                  Entrega item = d.toObject(Entrega.class);
                  if (item != null) {
                    item.setId(d.getId());
                    if (d.getMetadata().hasPendingWrites()) {
                      item.setConfirmado(false);
                      pendentes.add(d.getId());
                    }
                    itens.add(item);
                  }
                } catch (RuntimeException ex) {
                  erro("Há uma entrega com dados inválidos. Informe o técnico.");
                  return;
                }
              }
              adapter.pendentes(pendentes);
              adapter.substituir(itens);
              estadoLista(itens.size(), s.getMetadata().isFromCache());
            }));
  }

  private void confirmar(Entrega e) {
    adapter.enviando(e.getId(), true);
    String esperado = uid;
    repository
        .confirmar(e.getId())
        .addOnCompleteListener(
            this,
            t -> {
              if (!sessaoAtual(esperado)) return;
              adapter.enviando(e.getId(), false);
              if (!t.isSuccessful()) erro(MensagensErro.traduzir(t.getException()));
              else mensagem("Recebimento confirmado.");
            });
  }
}
