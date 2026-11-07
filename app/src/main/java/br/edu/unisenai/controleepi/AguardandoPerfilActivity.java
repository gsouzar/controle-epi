package br.edu.unisenai.controleepi;

import android.content.*;
import android.os.Bundle;
import android.widget.*;

public class AguardandoPerfilActivity extends BaseActivity {
  @Override
  protected String perfilExigido() {
    return null;
  }

  @Override
  protected void criarTela(Bundle b) {
    getLayoutInflater().inflate(R.layout.activity_espera, corpo, true);
    titulo("Liberação de acesso");
  }

  @Override
  protected void carregar() {
    ((TextView) findViewById(R.id.uid)).setText(uid);
    findViewById(R.id.copiar)
        .setOnClickListener(v -> {
          ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE))
              .setPrimaryClip(ClipData.newPlainText("UID da conta", uid));
          mensagem("UID copiado.");
        });
  }
}
