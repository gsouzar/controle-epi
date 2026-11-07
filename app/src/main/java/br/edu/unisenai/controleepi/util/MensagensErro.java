package br.edu.unisenai.controleepi.util;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestoreException;

public final class MensagensErro {
  private MensagensErro() {}

  public static String traduzir(Exception e) {
    if (e instanceof FirebaseNetworkException)
      return "Sem conexão. Confira a internet e tente novamente.";
    if (e instanceof FirebaseAuthUserCollisionException)
      return "Este e-mail já possui uma conta. Use Entrar.";
    if (e instanceof FirebaseAuthWeakPasswordException)
      return "A senha não atende à política do projeto. Use uma senha mais forte.";
    if (e instanceof FirebaseAuthException)
      return "Não foi possível autenticar. Confira e-mail, senha e a configuração do Firebase.";
    if (e instanceof FirebaseFirestoreException) {
      FirebaseFirestoreException.Code c = ((FirebaseFirestoreException) e).getCode();
      if (c == FirebaseFirestoreException.Code.ALREADY_EXISTS)
        return "Este UID já possui um cadastro. Confira os dados; nada foi substituído.";
      if (c == FirebaseFirestoreException.Code.PERMISSION_DENIED)
        return "Acesso negado. Confira o perfil e as regras do Firebase.";
      if (c == FirebaseFirestoreException.Code.FAILED_PRECONDITION
          && "Saldo insuficiente.".equals(e.getMessage()))
        return "Estoque insuficiente. Reduza a quantidade ou registre uma reposição.";
      if (c == FirebaseFirestoreException.Code.FAILED_PRECONDITION
          && "Estado do estoque mudou.".equals(e.getMessage()))
        return "O estoque mudou. Toque em Verificar novamente e confira o saldo.";
      if (c == FirebaseFirestoreException.Code.UNAVAILABLE)
        return "Servidor indisponível. Confira a conexão e tente novamente.";
    }
    return "Não foi possível concluir. Tente novamente e confira a configuração do Firebase.";
  }
}
