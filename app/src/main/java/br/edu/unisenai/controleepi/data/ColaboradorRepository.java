package br.edu.unisenai.controleepi.data;

import br.edu.unisenai.controleepi.model.Colaborador;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

public class ColaboradorRepository {
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();

  public ListenerRegistration observar(EventListener<QuerySnapshot> callback) {
    return db.collection("colaboradores")
        .orderBy("nome")
        .addSnapshotListener(MetadataChanges.INCLUDE, callback);
  }

  public Task<Void> salvar(String uid, Colaborador c) {
    return db.runTransaction(t -> {
      DocumentReference ref = db.collection("colaboradores").document(uid);
      DocumentSnapshot perfil = t.get(db.collection("usuarios").document(uid)),
          existente = t.get(ref);
      if (!"colaborador".equals(perfil.getString("perfil")))
        throw new FirebaseFirestoreException(
            "Perfil não liberado", FirebaseFirestoreException.Code.PERMISSION_DENIED);
      if (existente.exists())
        throw new FirebaseFirestoreException(
            "Já cadastrado", FirebaseFirestoreException.Code.ALREADY_EXISTS);
      t.set(ref, c);
      return null;
    });
  }
}
