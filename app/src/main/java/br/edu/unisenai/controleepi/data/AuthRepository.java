package br.edu.unisenai.controleepi.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class AuthRepository {
  private final FirebaseAuth auth = FirebaseAuth.getInstance();

  public FirebaseUser usuario() {
    return auth.getCurrentUser();
  }

  public Task<AuthResult> entrar(String email, String senha) {
    return auth.signInWithEmailAndPassword(email, senha);
  }

  public Task<AuthResult> cadastrar(String email, String senha) {
    return auth.createUserWithEmailAndPassword(email, senha);
  }

  public void sair() {
    auth.signOut();
  }
}
