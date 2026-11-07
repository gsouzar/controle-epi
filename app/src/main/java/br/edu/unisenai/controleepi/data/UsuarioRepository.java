package br.edu.unisenai.controleepi.data;

import com.google.firebase.firestore.*;

public class UsuarioRepository {
  public ListenerRegistration observar(String uid, EventListener<DocumentSnapshot> callback) {
    return FirebaseFirestore.getInstance()
        .collection("usuarios")
        .document(uid)
        .addSnapshotListener(MetadataChanges.INCLUDE, callback);
  }
}
