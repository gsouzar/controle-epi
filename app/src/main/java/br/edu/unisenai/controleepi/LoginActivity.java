package br.edu.unisenai.controleepi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.unisenai.controleepi.data.AuthRepository;
import br.edu.unisenai.controleepi.util.*;

public class LoginActivity extends AppCompatActivity {
  private EditText email, senha;
  private View entrar, cadastrar, progresso;
  private TextView mensagem, statusLogin;
  private AuthRepository auth;
  private OperacaoViewModel operacao;

  @Override
  protected void onCreate(Bundle b) {
    super.onCreate(b);
    setContentView(R.layout.activity_login);
    Ui.inset(findViewById(R.id.loginRoot));
    email = findViewById(R.id.email);
    senha = findViewById(R.id.senha);
    entrar = findViewById(R.id.entrar);
    cadastrar = findViewById(R.id.cadastrar);
    progresso = findViewById(R.id.progresso);
    mensagem = findViewById(R.id.mensagem);
    statusLogin = findViewById(R.id.statusLogin);
    if (!ControleEpiApp.configurado()) {
      mensagem.setText(
          "Configuração necessária\n\n"
              + "Crie um projeto Firebase, registre br.edu.unisenai.controleepi e coloque"
              + " google-services.json em app/. Depois compile novamente. Consulte o README.");
      entrar.setEnabled(false);
      cadastrar.setEnabled(false);
      return;
    }
    auth = new AuthRepository();
    operacao = new androidx.lifecycle.ViewModelProvider(this).get(OperacaoViewModel.class);
    entrar.setOnClickListener(v -> enviar(false));
    cadastrar.setOnClickListener(v -> enviar(true));
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (auth != null) {
      if (auth.usuario() != null) abrir();
      else acompanhar();
    }
  }

  private void enviar(boolean criar) {
    if (operacao.autenticacao != null && !operacao.autenticacao.isComplete()) return;
    String e = email.getText().toString().trim(), s = senha.getText().toString();
    if (!Validacao.email(e)) {
      email.setError("Informe um e-mail válido.");
      return;
    }
    if (!Validacao.senha(s)) {
      senha.setError("Use pelo menos 6 caracteres.");
      return;
    }
    mensagem.setText("");
    operacao.criandoConta = criar;
    operacao.autenticacao = criar ? auth.cadastrar(e, s) : auth.entrar(e, s);
    acompanhar();
  }

  private void acompanhar() {
    if (operacao == null || operacao.autenticacao == null) {
      ocupado(false);
      return;
    }
    statusLogin.setText(operacao.criandoConta ? R.string.criando_conta : R.string.entrando);
    ocupado(true);
    operacao.autenticacao.addOnCompleteListener(
        this,
        t -> {
          ocupado(false);
          operacao.autenticacao = null;
          if (t.isSuccessful()) abrir();
          else mensagem.setText(MensagensErro.traduzir(t.getException()));
        });
  }

  private void ocupado(boolean b) {
    entrar.setEnabled(!b);
    cadastrar.setEnabled(!b);
    progresso.setVisibility(b ? View.VISIBLE : View.GONE);
  }

  private void abrir() {
    startActivity(
        new Intent(this, AguardandoPerfilActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    finish();
  }
}
