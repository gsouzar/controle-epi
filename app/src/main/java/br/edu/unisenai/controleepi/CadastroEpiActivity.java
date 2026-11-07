package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.widget.*;
import br.edu.unisenai.controleepi.data.EpiRepository;
import br.edu.unisenai.controleepi.model.Epi;
import br.edu.unisenai.controleepi.util.*;
import java.util.Date;

public class CadastroEpiActivity extends FormularioActivity {
  protected int layoutFormulario() {
    return R.layout.activity_cadastro_epi;
  }

  protected String colecao() {
    return "epis";
  }

  protected void preparar(Bundle b) {
    titulo("Cadastrar EPI");
    ((TextView) findViewById(R.id.ajuda))
        .setText(
            "Informe a validade do certificado. EPIs vencidos também podem ser cadastrados para"
                + " acompanhamento.");
  }

  protected void salvar() {
    EditText descricao = findViewById(R.id.descricao),
        ca = findViewById(R.id.ca),
        validade = findViewById(R.id.validade);
    if (!Validacao.texto(Ui.texto(descricao), 120)) {
      descricao.setError("Informe a descrição (até 120 caracteres).");
      return;
    }
    if (!Validacao.ca(Ui.texto(ca))) {
      ca.setError("Informe um CA com 1 a 20 dígitos.");
      return;
    }
    Date data = Datas.deTexto(Ui.texto(validade));
    if (data == null) {
      validade.setError("Use uma data válida: dd/mm/aaaa.");
      return;
    }
    Epi e = new Epi();
    e.setDescricao(Ui.texto(descricao));
    e.setCa(Ui.texto(ca));
    e.setValidadeCa(data);
    enviar(new EpiRepository().salvar(operacaoId, e));
  }
}
