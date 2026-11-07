package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.lifecycle.ViewModelProvider;
import br.edu.unisenai.controleepi.util.MensagensErro;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import java.util.UUID;

public abstract class FormularioActivity extends BaseActivity {
  protected String operacaoId;
  protected boolean pendente;
  private boolean tentando;
  private OperacaoViewModel operacao;
  private Task<Void> observada;

  protected abstract int layoutFormulario();

  protected abstract void salvar();

  protected abstract String colecao();

  @Override
  protected void criarTela(Bundle b) {
    getLayoutInflater().inflate(layoutFormulario(), corpo, true);
    operacao = new ViewModelProvider(this).get(OperacaoViewModel.class);
    operacaoId =
        b == null
            ? UUID.randomUUID().toString()
            : b.getString("operacaoId", UUID.randomUUID().toString());
    pendente = b != null && b.getBoolean("pendente");
    findViewById(R.id.salvar)
        .setOnClickListener(v -> {
          if (!tentando) salvar();
        });
    preparar(b);
  }

  protected void preparar(Bundle b) {}

  protected void carregar() {
    if (operacao.gravacao != null) acompanhar();
    else if (pendente) resolverPendente();
  }

  protected void enviar(Task<Void> task) {
    operacao.gravacao = task;
    pendente = true;
    acompanhar();
  }

  private void acompanhar() {
    tentando = true;
    travar(corpo, true);
    findViewById(R.id.salvar).setEnabled(false);
    erro("Enviando. Aguarde a confirmação do servidor…");
    findViewById(R.id.repetir).setVisibility(View.GONE);
    if (observada == operacao.gravacao) return;
    observada = operacao.gravacao;
    final String dono = uid;
    operacao.gravacao.addOnCompleteListener(
        this,
        t -> {
          if (!sessaoAtual(dono)) return;
          operacao.gravacao = null;
          observada = null;
          tentando = false;
          if (t.isSuccessful()) {
            mensagem("Registro salvo.");
            finish();
            return;
          }
          if (falhaDefinitiva(t.getException())) {
            liberarEdicao();
            erro(MensagensErro.traduzir(t.getException()));
          } else {
            erro(MensagensErro.traduzir(t.getException()));
            findViewById(R.id.salvar).setEnabled(true);
            ((Button) findViewById(R.id.salvar)).setText("Tentar novamente");
          }
        });
  }

  private boolean falhaDefinitiva(Exception e) {
    if (!(e instanceof FirebaseFirestoreException)) return false;
    FirebaseFirestoreException.Code c = ((FirebaseFirestoreException) e).getCode();
    return c == FirebaseFirestoreException.Code.PERMISSION_DENIED
        || c == FirebaseFirestoreException.Code.ALREADY_EXISTS
        || c == FirebaseFirestoreException.Code.INVALID_ARGUMENT
        || c == FirebaseFirestoreException.Code.FAILED_PRECONDITION
        || c == FirebaseFirestoreException.Code.NOT_FOUND
        || c == FirebaseFirestoreException.Code.UNAUTHENTICATED;
  }

  protected boolean corresponde(DocumentSnapshot d) {
    return true;
  }

  private void liberarEdicao() {
    pendente = false;
    tentando = false;
    operacaoId = UUID.randomUUID().toString();
    travar(corpo, false);
    findViewById(R.id.salvar).setEnabled(true);
    ((Button) findViewById(R.id.salvar)).setText("Salvar");
  }

  private void resolverPendente() {
    tentando = true;
    travar(corpo, true);
    findViewById(R.id.salvar).setEnabled(false);
    final String dono = uid;
    FirebaseFirestore.getInstance()
        .collection(colecao())
        .document(operacaoId)
        .get(Source.SERVER)
        .addOnCompleteListener(
            this,
            t -> {
              if (!sessaoAtual(dono)) return;
              tentando = false;
              if (t.isSuccessful() && t.getResult() != null && t.getResult().exists()) {
                if (corresponde(t.getResult())) {
                  mensagem("Registro já salvo no servidor.");
                  finish();
                } else {
                  liberarEdicao();
                  erro(
                      "Este UID já possui um cadastro diferente. Confira os dados; nada foi"
                          + " substituído.");
                }
                return;
              }
              erro(
                  t.isSuccessful()
                      ? "O envio não foi confirmado. Tente novamente com os mesmos dados."
                      : MensagensErro.traduzir(t.getException()));
              findViewById(R.id.salvar).setEnabled(true);
              ((Button) findViewById(R.id.salvar)).setText("Tentar novamente");
            });
  }

  private void travar(View v, boolean b) {
    if (v instanceof EditText || v instanceof Spinner) v.setEnabled(!b);
    if (v instanceof ViewGroup) {
      ViewGroup g = (ViewGroup) v;
      for (int i = 0; i < g.getChildCount(); i++) travar(g.getChildAt(i), b);
    }
  }

  @Override
  protected void onStop() {
    observada = null;
    super.onStop();
  }

  @Override
  protected void onSaveInstanceState(Bundle out) {
    super.onSaveInstanceState(out);
    out.putString("operacaoId", operacaoId);
    out.putBoolean("pendente", pendente);
  }
}
