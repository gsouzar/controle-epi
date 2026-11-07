package br.edu.unisenai.controleepi.data;

import br.edu.unisenai.controleepi.model.Epi;
import br.edu.unisenai.controleepi.util.Datas;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

public class EpiRepository {
  private final CollectionReference itens = FirebaseFirestore.getInstance().collection("epis");

  public ListenerRegistration observar(boolean vencimentos, EventListener<QuerySnapshot> callback) {
    Query q =
        vencimentos
            ? itens
                .whereLessThan("validadeCa", Datas.inicioDoDia(Datas.hoje().plusDays(31)))
                .orderBy("validadeCa")
            : itens.orderBy("descricao");
    return q.addSnapshotListener(MetadataChanges.INCLUDE, callback);
  }

  public Task<Void> salvar(String id, Epi epi) {
    DocumentReference ref = itens.document(id);
    return FirebaseFirestore.getInstance()
        .runTransaction(t -> {
          if (!t.get(ref).exists()) t.set(ref, epi);
          return null;
        });
  }
}
