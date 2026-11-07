package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import br.edu.unisenai.controleepi.data.*;
import br.edu.unisenai.controleepi.model.*;
import br.edu.unisenai.controleepi.util.*;
import com.google.firebase.firestore.*;
import java.util.*;

public class NovaEntregaActivity extends FormularioActivity {
  private final List<Epi> epis = new ArrayList<>();
  private final List<Colaborador> colaboradores = new ArrayList<>();
  private Spinner escolhaEpi, escolhaColaborador;
  private String epiSelecionado, colaboradorSelecionado, descricaoHistorica, caHistorico;
  private boolean episProntos, colaboradoresProntos, extrasConsultados, extrasAtivos;
  private Spinner escolhaMotivo;
  private String motivoOperacao;
  private static final List<String> MOTIVOS =
      Arrays.asList("Sem motivo de troca", "Desgaste", "Perda", "Tamanho errado");

  protected int layoutFormulario() {
    return R.layout.activity_nova_entrega;
  }

  protected String colecao() {
    return "entregas";
  }

  protected void preparar(Bundle b) {
    titulo("Registrar entrega");
    escolhaEpi = findViewById(R.id.escolhaEpi);
    escolhaColaborador = findViewById(R.id.escolhaColaborador);
    escolhaMotivo = findViewById(R.id.motivoTroca);
    popular(escolhaMotivo, MOTIVOS);
    ((TextView) findViewById(R.id.ajuda))
        .setText(
            "A entrega será registrada como pendente. Somente o colaborador poderá confirmar o"
                + " recebimento.");
    if (b == null)
      ((EditText) findViewById(R.id.data)).setText(Datas.formatar(Datas.deDia(Datas.hoje())));
    else {
      epiSelecionado = b.getString("epi");
      colaboradorSelecionado = b.getString("colaborador");
      descricaoHistorica = b.getString("descricaoHistorica");
      caHistorico = b.getString("caHistorico");
      motivoOperacao = b.getString("motivoOperacao");
    }
  }

  protected void carregar() {
    super.carregar();
    episProntos = false;
    colaboradoresProntos = false;
    extrasConsultados = false;
    String dono = uid;
    new EstoqueRepository()
        .verificarDisponibilidade()
        .addOnCompleteListener(
            this,
            t -> {
              if (!sessaoAtual(dono)) return;
              if (!t.isSuccessful() && !EstoqueRepository.regrasAnteriores(t.getException())) {
                erro(MensagensErro.traduzir(t.getException()));
                return;
              }
              extrasAtivos = t.isSuccessful();
              extrasConsultados = true;
              findViewById(R.id.blocoMotivo).setVisibility(extrasAtivos ? View.VISIBLE : View.GONE);
              habilitar();
            });
    if (!pendente) findViewById(R.id.salvar).setEnabled(false);
    observar(
        new EpiRepository()
            .observar(
                false,
                (s, e) -> {
                  if (e != null) {
                    erro(MensagensErro.traduzir(e));
                    return;
                  }
                  if (s == null || s.getMetadata().isFromCache()) return;
                  String anterior = selecionadoEpi();
                  List<Epi> novos = new ArrayList<>();
                  List<String> nomes = new ArrayList<>();
                  nomes.add("Selecione um EPI");
                  for (DocumentSnapshot d : s.getDocuments()) {
                    try {
                      Epi item = d.toObject(Epi.class);
                      if (item != null) {
                        item.setId(d.getId());
                        novos.add(item);
                        nomes.add(item.getDescricao() + " • CA " + item.getCa());
                      }
                    } catch (RuntimeException ex) {
                      erro("Cadastro de EPI inválido.");
                      return;
                    }
                  }
                  epis.clear();
                  epis.addAll(novos);
                  popular(escolhaEpi, nomes);
                  selecionarEpi(anterior != null ? anterior : epiSelecionado);
                  episProntos = true;
                  habilitar();
                }));
    observar(
        new ColaboradorRepository()
            .observar((s, e) -> {
              if (e != null) {
                erro(MensagensErro.traduzir(e));
                return;
              }
              if (s == null || s.getMetadata().isFromCache()) return;
              String anterior = selecionadoColaborador();
              List<Colaborador> novos = new ArrayList<>();
              List<String> nomes = new ArrayList<>();
              nomes.add("Selecione um colaborador");
              for (DocumentSnapshot d : s.getDocuments()) {
                try {
                  Colaborador c = d.toObject(Colaborador.class);
                  if (c != null) {
                    c.setId(d.getId());
                    novos.add(c);
                    nomes.add(c.getNome() + " • " + c.getMatricula());
                  }
                } catch (RuntimeException ex) {
                  erro("Cadastro de colaborador inválido.");
                  return;
                }
              }
              colaboradores.clear();
              colaboradores.addAll(novos);
              popular(escolhaColaborador, nomes);
              selecionarColaborador(anterior != null ? anterior : colaboradorSelecionado);
              colaboradoresProntos = true;
              habilitar();
            }));
  }

  private void popular(Spinner s, List<String> nomes) {
    ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes);
    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    s.setAdapter(a);
  }

  private void habilitar() {
    if (!pendente)
      findViewById(R.id.salvar)
          .setEnabled(episProntos && colaboradoresProntos && extrasConsultados);
    if (episProntos && colaboradoresProntos && (epis.isEmpty() || colaboradores.isEmpty()))
      erro("Cadastre ao menos um EPI e um colaborador antes de registrar a entrega.");
  }

  private String selecionadoEpi() {
    int n = escolhaEpi.getSelectedItemPosition() - 1;
    return n >= 0 && n < epis.size() ? epis.get(n).getId() : null;
  }

  private String selecionadoColaborador() {
    int n = escolhaColaborador.getSelectedItemPosition() - 1;
    return n >= 0 && n < colaboradores.size() ? colaboradores.get(n).getId() : null;
  }

  private void selecionarEpi(String id) {
    for (int i = 0; i < epis.size(); i++)
      if (epis.get(i).getId().equals(id)) {
        escolhaEpi.setSelection(i + 1);
        break;
      }
  }

  private void selecionarColaborador(String id) {
    for (int i = 0; i < colaboradores.size(); i++)
      if (colaboradores.get(i).getId().equals(id)) {
        escolhaColaborador.setSelection(i + 1);
        break;
      }
  }

  protected void salvar() {
    EditText qtd = findViewById(R.id.quantidade), dt = findViewById(R.id.data);
    int quantidade = Validacao.quantidade(Ui.texto(qtd));
    if (quantidade < 1) {
      qtd.setError("Informe um inteiro entre 1 e 9999.");
      return;
    }
    Date data = Datas.deTexto(Ui.texto(dt));
    if (data == null || Datas.dia(data).isAfter(Datas.hoje())) {
      dt.setError("Informe uma data válida, até hoje.");
      return;
    }
    if (!pendente) {
      epiSelecionado = selecionadoEpi();
      colaboradorSelecionado = selecionadoColaborador();
      if (epiSelecionado == null || colaboradorSelecionado == null) {
        erro("Selecione o EPI e o colaborador.");
        return;
      }
      Epi item = epis.get(escolhaEpi.getSelectedItemPosition() - 1);
      descricaoHistorica = item.getDescricao();
      caHistorico = item.getCa();
    }
    if (epiSelecionado == null || colaboradorSelecionado == null) {
      erro("Seleção não recuperada. Volte e confira os cadastros.");
      return;
    }
    Entrega e = new Entrega();
    e.setColaboradorUid(colaboradorSelecionado);
    e.setEpiId(epiSelecionado);
    e.setEpiDescricao(descricaoHistorica);
    e.setEpiCa(caHistorico);
    e.setQuantidade(quantidade);
    e.setData(data);
    e.setConfirmado(false);
    if (!extrasConsultados) {
      erro("Aguarde a verificação dos recursos do servidor.");
      return;
    }
    if (!pendente) {
      int posicaoMotivo = escolhaMotivo.getSelectedItemPosition();
      motivoOperacao = extrasAtivos && posicaoMotivo > 0 ? MOTIVOS.get(posicaoMotivo) : null;
    }
    if (motivoOperacao != null && !extrasAtivos) {
      erro("O motivo informado não pode ser gravado. Confira as regras do Firebase.");
      return;
    }
    e.setMotivoTroca(motivoOperacao);
    enviar(new EntregaRepository().registrar(operacaoId, e, extrasAtivos));
  }

  @Override
  protected void onSaveInstanceState(Bundle b) {
    if (!pendente) {
      if (episProntos) epiSelecionado = selecionadoEpi();
      if (colaboradoresProntos) colaboradorSelecionado = selecionadoColaborador();
    }
    super.onSaveInstanceState(b);
    b.putString("epi", epiSelecionado);
    b.putString("colaborador", colaboradorSelecionado);
    b.putString("descricaoHistorica", descricaoHistorica);
    b.putString("caHistorico", caHistorico);
    b.putString("motivoOperacao", motivoOperacao);
  }
}
