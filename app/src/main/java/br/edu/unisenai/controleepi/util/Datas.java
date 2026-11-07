package br.edu.unisenai.controleepi.util;

import java.time.*;
import java.time.format.*;
import java.util.Date;

public final class Datas {
  private static final DateTimeFormatter FORMATO =
      DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

  private Datas() {}

  public static LocalDate hoje() {
    return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
  }

  public static Date deTexto(String texto) {
    try {
      return deDia(LocalDate.parse(texto, FORMATO));
    } catch (Exception e) {
      return null;
    }
  }

  public static Date deDia(LocalDate dia) {
    return Date.from(dia.atTime(12, 0).toInstant(ZoneOffset.UTC));
  }

  public static Date inicioDoDia(LocalDate dia) {
    return Date.from(dia.atStartOfDay(ZoneOffset.UTC).toInstant());
  }

  public static LocalDate dia(Date data) {
    return data.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
  }

  public static String formatar(Date data) {
    return data == null ? "Data não informada" : FORMATO.format(dia(data));
  }

  public static boolean noLimite(LocalDate validade, LocalDate hoje) {
    return !validade.isAfter(hoje.plusDays(30));
  }

  public static String status(Date data) {
    if (data == null) return "Validade não informada";
    LocalDate d = dia(data), h = hoje();
    if (d.isBefore(h)) return "Vencido";
    if (d.equals(h)) return "Vence hoje";
    return noLimite(d, h) ? "Vence em breve" : "Em dia";
  }
}
