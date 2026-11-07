package br.edu.unisenai.controleepi.model;

import com.google.firebase.firestore.Exclude;

public class Colaborador {
  private String id;

  public Colaborador() {}

  @Exclude
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private String nome;

  public String getNome() {
    return nome;
  }

  public void setNome(String valor) {
    nome = valor;
  }

  private String matricula;

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String valor) {
    matricula = valor;
  }

  private String setor;

  public String getSetor() {
    return setor;
  }

  public void setSetor(String valor) {
    setor = valor;
  }
}
