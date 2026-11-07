package br.edu.unisenai.controleepi;

public class FichaColaboradorActivity extends MinhasEntregasActivity {
  protected String perfilExigido() {
    return "tecnico";
  }

  protected String colaboradorUid() {
    return getIntent().getStringExtra("colaboradorUid");
  }

  protected boolean permiteConfirmar() {
    return false;
  }

  protected void configurar() {
    super.configurar();
    String nome = getIntent().getStringExtra("nome");
    titulo(nome == null ? "Ficha do colaborador" : "Ficha • " + nome);
  }
}
