package br.edu.unisenai.controleepi.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import java.util.HashMap;
import java.util.Map;

public class EstoqueRepository {
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();

  public Task<QuerySnapshot> verificarDisponibilidade() {
    return db.collection("estoques").limit(1).get(Source.SERVER);
  }

  public static boolean regrasAnteriores(Exception erro) {
    return erro instanceof FirebaseFirestoreException
        && ((FirebaseFirestoreException) erro).getCode()
            == FirebaseFirestoreException.Code.PERMISSION_DENIED;
  }

  public Task<DocumentSnapshot> consultar(String epiId) {
    return db.collection("estoques").document(epiId).get(Source.SERVER);
  }

  public Task<Void> repor(String epiId, String operacao, long quantidade, boolean inicializar) {
    DocumentReference estoque = db.collection("estoques").document(epiId);
    DocumentReference reposicao = estoque.collection("reposicoes").document(operacao);
    return db.runTransaction(t -> {
      if (t.get(reposicao).exists()) return null;
      DocumentSnapshot anterior = t.get(estoque);
      if (anterior.exists() == inicializar) {
        throw new FirebaseFirestoreException(
            "Estado do estoque mudou.", FirebaseFirestoreException.Code.FAILED_PRECONDITION);
      }
      Long saldo = anterior.exists() ? anterior.getLong("saldo") : Long.valueOf(0L);
      if (saldo == null
          || saldo < 0
          || quantidade < 0
          || (anterior.exists() && quantidade == 0)
          || saldo > 1_000_000_000L - quantidade) {
        throw new FirebaseFirestoreException(
            "Quantidade fora do limite do estoque.",
            FirebaseFirestoreException.Code.FAILED_PRECONDITION);
      }
      Map<String, Object> dados = new HashMap<>();
      dados.put("quantidade", quantidade);
      t.set(reposicao, dados);
      t.set(estoque, movimento(saldo + quantidade, operacao, "reposicao"));
      return null;
    });
  }

  static Map<String, Object> movimento(long saldo, String operacao, String origem) {
    Map<String, Object> dados = new HashMap<>();
    dados.put("saldo", saldo);
    dados.put("ultimaOperacao", operacao);
    dados.put("origem", origem);
    return dados;
  }
}
