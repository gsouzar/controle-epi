package br.edu.unisenai.controleepi.model;

import com.google.firebase.firestore.Exclude;
import java.util.Date;

public class Entrega {
  private String id;

  public Entrega() {}

  @Exclude
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private String colaboradorUid;

  public String getColaboradorUid() {
    return colaboradorUid;
  }

  public void setColaboradorUid(String valor) {
    colaboradorUid = valor;
  }

  private String epiId;

  public String getEpiId() {
    return epiId;
  }

  public void setEpiId(String valor) {
    epiId = valor;
  }

  private String epiDescricao;

  public String getEpiDescricao() {
    return epiDescricao;
  }

  public void setEpiDescricao(String valor) {
    epiDescricao = valor;
  }

  private String epiCa;

  public String getEpiCa() {
    return epiCa;
  }

  public void setEpiCa(String valor) {
    epiCa = valor;
  }

  private long quantidade;

  public long getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(long valor) {
    quantidade = valor;
  }

  private Date data;

  public Date getData() {
    return data;
  }

  public void setData(Date valor) {
    data = valor;
  }

  private boolean confirmado;

  public boolean isConfirmado() {
    return confirmado;
  }

  public void setConfirmado(boolean valor) {
    confirmado = valor;
  }

  private String motivoTroca;

  public String getMotivoTroca() {
    return motivoTroca;
  }

  public void setMotivoTroca(String motivo) {
    motivoTroca = motivo;
  }
}
