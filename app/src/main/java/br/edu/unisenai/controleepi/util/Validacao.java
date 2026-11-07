package br.edu.unisenai.controleepi.util;

public final class Validacao {
  private Validacao() {}

  public static boolean email(String valor) {
    return valor != null && valor.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
  }

  public static boolean senha(String valor) {
    return valor != null && valor.length() >= 6;
  }

  public static boolean texto(String valor, int max) {
    return valor != null && !valor.trim().isEmpty() && valor.trim().length() <= max;
  }

  public static boolean ca(String valor) {
    return valor != null && valor.matches("[0-9]{1,20}");
  }

  public static int quantidade(String valor) {
    try {
      if (valor == null || !valor.matches("[0-9]+")) return -1;
      int n = Integer.parseInt(valor);
      return n >= 1 && n <= 9999 ? n : -1;
    } catch (NumberFormatException e) {
      return -1;
    }
  }
}
