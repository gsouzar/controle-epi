package br.edu.unisenai.controleepi.model;

import com.google.firebase.firestore.Exclude;
import java.util.Date;

public class Epi {
  private String id;

  public Epi() {}

  @Exclude
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private String descricao;

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String valor) {
    descricao = valor;
  }

  private String ca;

  public String getCa() {
    return ca;
  }

  public void setCa(String valor) {
    ca = valor;
  }

  private Date validadeCa;

  public Date getValidadeCa() {
    return validadeCa;
  }

  public void setValidadeCa(Date valor) {
    validadeCa = valor;
  }
}
