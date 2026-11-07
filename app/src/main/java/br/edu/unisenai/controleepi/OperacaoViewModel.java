package br.edu.unisenai.controleepi;

import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;

public class OperacaoViewModel extends ViewModel {
  public Task<Void> gravacao;
  public boolean criandoConta;
  public com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> autenticacao;
}
