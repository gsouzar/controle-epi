package br.edu.unisenai.controleepi.data;

import br.edu.unisenai.controleepi.model.Entrega;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import java.util.HashMap;
import java.util.Map;

public class EntregaRepository {
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();

  public ListenerRegistration observar(String uid, EventListener<QuerySnapshot> callback) {
    return db.collection("entregas")
        .whereEqualTo("colaboradorUid", uid)
        .orderBy("data", Query.Direction.DESCENDING)
        .addSnapshotListener(MetadataChanges.INCLUDE, callback);
  }

  public Task<Void> registrar(String id, Entrega entrega, boolean controlarEstoque) {
    DocumentReference ref = db.collection("entregas").document(id);
    return db.runTransaction(t -> {
      DocumentSnapshot atual = t.get(ref);
      if (atual.exists()) return null;
      DocumentReference estoque = db.collection("estoques").document(entrega.getEpiId());
      DocumentSnapshot saldoAtual = controlarEstoque ? t.get(estoque) : null;
      if (saldoAtual != null && saldoAtual.exists()) {
        Long saldo = saldoAtual.getLong("saldo");
        if (saldo == null || saldo < entrega.getQuantidade()) {
          throw new FirebaseFirestoreException(
              "Saldo insuficiente.", FirebaseFirestoreException.Code.FAILED_PRECONDITION);
        }
        t.set(estoque, EstoqueRepository.movimento(saldo - entrega.getQuantidade(), id, "entrega"));
      }
      Map<String, Object> dados = new HashMap<>();
      dados.put("colaboradorUid", entrega.getColaboradorUid());
      dados.put("epiId", entrega.getEpiId());
      dados.put("epiDescricao", entrega.getEpiDescricao());
      dados.put("epiCa", entrega.getEpiCa());
      dados.put("quantidade", entrega.getQuantidade());
      dados.put("data", entrega.getData());
      dados.put("confirmado", false);
      if (entrega.getMotivoTroca() != null) dados.put("motivoTroca", entrega.getMotivoTroca());
      t.set(ref, dados);
      return null;
    });
  }

  public Task<DocumentSnapshot> consultar(String id) {
    return db.collection("entregas").document(id).get(Source.SERVER);
  }

  public Task<Void> confirmar(String id) {
    return db.collection("entregas").document(id).update("confirmado", true);
  }
}
