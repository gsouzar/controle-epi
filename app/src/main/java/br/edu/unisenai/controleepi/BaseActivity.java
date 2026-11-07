package br.edu.unisenai.controleepi;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.edu.unisenai.controleepi.data.UsuarioRepository;
import br.edu.unisenai.controleepi.util.MensagensErro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.*;

public abstract class BaseActivity extends AppCompatActivity {
  protected String uid;
  protected Bundle estadoInicial;
  protected FrameLayout corpo;
  private ListenerRegistration perfilListener;
  private final List<ListenerRegistration> registros = new ArrayList<>();
  private FirebaseAuth.AuthStateListener sessao;
  private boolean liberado;
  private boolean navegando;
  private TextView aviso;

  protected abstract void criarTela(Bundle estado);

  protected abstract void carregar();

  protected String perfilExigido() {
    return "tecnico";
  }

  protected final void titulo(String valor) {
    getSupportActionBar().setTitle(valor);
  }

  @Override
  protected final void onCreate(Bundle b) {
    super.onCreate(b);
    if (!ControleEpiApp.configurado()) {
      login();
      return;
    }
    setContentView(R.layout.activity_base);
    Ui.inset(findViewById(R.id.raiz));
    Toolbar barra = findViewById(R.id.toolbar);
    setSupportActionBar(barra);
    if (voltaAoInicio()) {
      barra.setNavigationIcon(R.drawable.ic_voltar);
      barra.setNavigationContentDescription(R.string.voltar_inicio);
      barra.setNavigationOnClickListener(v -> voltarAoInicio());
    }
    findViewById(R.id.sair).setOnClickListener(v -> sair());
    corpo = findViewById(R.id.corpo);
    aviso = findViewById(R.id.aviso);
    estadoInicial = b;
    criarTela(b);
    findViewById(R.id.repetir)
        .setOnClickListener(v -> {
          parar();
          autorizar();
        });
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (corpo == null) return;
    sessao =
        a -> {
          if (a.getCurrentUser() == null || (uid != null && !uid.equals(a.getUid()))) login();
        };
    FirebaseAuth.getInstance().addAuthStateListener(sessao);
    autorizar();
  }

  private void autorizar() {
    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      login();
      return;
    }
    uid = FirebaseAuth.getInstance().getUid();
    final String esperado = uid;
    liberado = false;
    corpo.setVisibility(View.GONE);
    erro("Verificando acesso…");
    perfilListener =
        new UsuarioRepository()
            .observar(
                uid,
                (doc, e) -> {
                  if (isFinishing() || !esperado.equals(FirebaseAuth.getInstance().getUid()))
                    return;
                  if (e != null) {
                    revogar();
                    erro(MensagensErro.traduzir(e));
                    return;
                  }
                  if (doc == null || doc.getMetadata().isFromCache()) return;
                  Object p = doc.exists() ? doc.get("perfil") : null;
                  if (perfilExigido() == null) {
                    if ("tecnico".equals(p)) {
                      abrir(PainelTecnicoActivity.class);
                      return;
                    }
                    if ("colaborador".equals(p)) {
                      abrir(MinhasEntregasActivity.class);
                      return;
                    }
                  } else if (!perfilExigido().equals(p)) {
                    abrir(AguardandoPerfilActivity.class);
                    return;
                  }
                  if (!liberado) {
                    liberado = true;
                    corpo.setVisibility(View.VISIBLE);
                    erro("");
                    carregar();
                  }
                });
  }

  protected final boolean sessaoAtual(String esperado) {
    return !isFinishing()
        && !isDestroyed()
        && esperado != null
        && esperado.equals(FirebaseAuth.getInstance().getUid());
  }

  protected final void observar(ListenerRegistration r) {
    registros.add(r);
  }

  protected final void erro(String mensagem) {
    aviso.setText(mensagem);
    aviso.setVisibility(mensagem.isEmpty() ? View.GONE : View.VISIBLE);
    findViewById(R.id.repetir).setVisibility(mensagem.isEmpty() ? View.GONE : View.VISIBLE);
  }

  protected final void mensagem(String texto) {
    Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
  }

  protected final void abrir(Class<?> tela) {
    if (navegando || isFinishing()) return;
    navegando = true;
    startActivity(
        new Intent(this, tela)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    finish();
  }

  private boolean voltaAoInicio() {
    return "tecnico".equals(perfilExigido()) && !(this instanceof PainelTecnicoActivity);
  }

  private void voltarAoInicio() {
    startActivity(
        new Intent(this, PainelTecnicoActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    finish();
  }

  private void sair() {
    parar();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    if (sessao != null) {
      auth.removeAuthStateListener(sessao);
      sessao = null;
    }
    auth.signOut();
    login();
  }

  private void login() {
    abrir(LoginActivity.class);
  }

  private void revogar() {
    for (ListenerRegistration r : registros) r.remove();
    registros.clear();
    liberado = false;
    corpo.setVisibility(View.GONE);
  }

  private void parar() {
    if (perfilListener != null) {
      perfilListener.remove();
      perfilListener = null;
    }
    revogar();
  }

  @Override
  protected void onStop() {
    if (corpo != null) parar();
    if (sessao != null) FirebaseAuth.getInstance().removeAuthStateListener(sessao);
    super.onStop();
  }
}
