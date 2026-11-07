package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import br.edu.unisenai.controleepi.data.EstoqueRepository;
import br.edu.unisenai.controleepi.util.MensagensErro;

public class EstoqueActivity extends FormularioActivity {
  private String epiId;
  private boolean consultado, inicializado;
  private Boolean inicializarOperacao;

  @Override
  protected int layoutFormulario() {
    return R.layout.activity_estoque;
  }

  @Override
  protected String colecao() {
    return "estoques/" + epiId + "/reposicoes";
  }

  @Override
  protected void preparar(Bundle estado) {
    epiId = getIntent().getStringExtra("epiId");
    if (estado != null && estado.containsKey("inicializarEstoque")) {
      inicializarOperacao = estado.getBoolean("inicializarEstoque");
    }
    titulo("Estoque");
    ((TextView) findViewById(R.id.descricaoEstoque))
        .setText(getIntent().getStringExtra("descricao"));
  }

  @Override
  protected void carregar() {
    if (epiId == null || epiId.isEmpty() || epiId.contains("/")) {
      findViewById(R.id.salvar).setEnabled(false);
      erro("EPI inválido.");
      return;
    }
    super.carregar();
    consultado = false;
    if (!pendente) findViewById(R.id.salvar).setEnabled(false);
    String dono = uid;
    new EstoqueRepository()
        .consultar(epiId)
        .addOnCompleteListener(
            this,
            t -> {
              if (!sessaoAtual(dono)) return;
              if (!t.isSuccessful()) {
                erro(MensagensErro.traduzir(t.getException()));
                return;
              }
              inicializado = t.getResult().exists();
              Long saldo = t.getResult().getLong("saldo");
              ((TextView) findViewById(R.id.ajuda))
                  .setText(
                      inicializado
                          ? "Saldo atual: " + saldo + ". Informe a quantidade a acrescentar."
                          : "Estoque ainda não controlado. Informe o saldo inicial, inclusive"
                              + " zero.");
              consultado = true;
              if (!pendente) findViewById(R.id.salvar).setEnabled(true);
            });
  }

  @Override
  protected void salvar() {
    if (!consultado) return;
    EditText campo = findViewById(R.id.quantidade);
    String valor = Ui.texto(campo);
    if (!valor.matches("[0-9]{1,9}")) {
      campo.setError("Informe uma quantidade inteira.");
      return;
    }
    long quantidade = Long.parseLong(valor);
    if (!pendente || inicializarOperacao == null) inicializarOperacao = !inicializado;
    if (!inicializarOperacao && quantidade == 0) {
      campo.setError("A reposição deve ser maior que zero.");
      return;
    }
    enviar(new EstoqueRepository().repor(epiId, operacaoId, quantidade, inicializarOperacao));
  }

  @Override
  protected void onSaveInstanceState(Bundle estado) {
    super.onSaveInstanceState(estado);
    if (inicializarOperacao != null) estado.putBoolean("inicializarEstoque", inicializarOperacao);
  }
}
