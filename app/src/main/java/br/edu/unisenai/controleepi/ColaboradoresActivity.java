package br.edu.unisenai.controleepi;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.edu.unisenai.controleepi.adapter.ColaboradorAdapter;
import br.edu.unisenai.controleepi.data.ColaboradorRepository;
import br.edu.unisenai.controleepi.model.Colaborador;
import br.edu.unisenai.controleepi.util.MensagensErro;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.*;

public class ColaboradoresActivity extends ListaActivity {
  private ColaboradorAdapter adapter;
  private EditText busca;
  private final List<Colaborador> colaboradores = new ArrayList<>();
  private boolean recebidos, emCache;

  protected void configurar() {
    titulo("Colaboradores");
    busca = findViewById(R.id.buscaMatricula);
    busca.setVisibility(View.VISIBLE);
    busca.addTextChangedListener(
        new TextWatcher() {
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (recebidos) filtrar();
          }

          public void afterTextChanged(Editable texto) {}
        });
    adapter =
        new ColaboradorAdapter(c ->
            startActivity(
                new Intent(this, FichaColaboradorActivity.class)
                    .putExtra("colaboradorUid", c.getId())
                    .putExtra("nome", c.getNome())));
    lista.setAdapter(adapter);
    findViewById(R.id.adicionar)
        .setOnClickListener(
            v -> startActivity(new Intent(this, CadastroColaboradorActivity.class)));
  }

  protected void carregar() {
    recebidos = false;
    colaboradores.clear();
    adapter.substituir(Collections.emptyList());
    observar(
        new ColaboradorRepository()
            .observar((s, e) -> {
              if (e != null) {
                erro(MensagensErro.traduzir(e));
                return;
              }
              if (s == null) return;
              List<Colaborador> itens = new ArrayList<>();
              for (DocumentSnapshot d : s.getDocuments()) {
                try {
                  Colaborador c = d.toObject(Colaborador.class);
                  if (c != null) {
                    c.setId(d.getId());
                    itens.add(c);
                  }
                } catch (RuntimeException ex) {
                  erro("Há um colaborador com dados inválidos. Revise no console.");
                  return;
                }
              }
              colaboradores.clear();
              colaboradores.addAll(itens);
              recebidos = true;
              emCache = s.getMetadata().isFromCache();
              filtrar();
            }));
  }

  private void filtrar() {
    String consulta = Ui.texto(busca).toLowerCase(Locale.ROOT);
    List<Colaborador> visiveis = new ArrayList<>();
    for (Colaborador c : colaboradores) {
      String matricula = c.getMatricula();
      if (consulta.isEmpty()
          || (matricula != null && matricula.toLowerCase(Locale.ROOT).contains(consulta))) {
        visiveis.add(c);
      }
    }
    adapter.substituir(visiveis);
    estadoLista(visiveis.size(), emCache);
    ((TextView) findViewById(R.id.vazio))
        .setText(
            consulta.isEmpty()
                ? "Nenhum colaborador cadastrado."
                : "Nenhum colaborador com essa matrícula.");
  }
}
