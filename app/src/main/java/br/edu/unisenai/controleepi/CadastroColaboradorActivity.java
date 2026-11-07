package br.edu.unisenai.controleepi;

import android.os.Bundle;
import android.widget.*;
import br.edu.unisenai.controleepi.data.ColaboradorRepository;
import br.edu.unisenai.controleepi.model.Colaborador;
import br.edu.unisenai.controleepi.util.Validacao;

public class CadastroColaboradorActivity extends FormularioActivity {
  protected int layoutFormulario() {
    return R.layout.activity_cadastro_colaborador;
  }

  protected String colecao() {
    return "colaboradores";
  }

  protected void preparar(Bundle b) {
    titulo("Cadastrar colaborador");
    ((TextView) findViewById(R.id.ajuda))
        .setText(
            "Confira a pessoa e o UID. A conta deve ter perfil colaborador atribuído no console"
                + " Firebase. Um cadastro existente não será substituído.");
  }

  protected boolean corresponde(com.google.firebase.firestore.DocumentSnapshot d) {
    return Ui.texto((EditText) findViewById(R.id.nome)).equals(d.getString("nome"))
        && Ui.texto((EditText) findViewById(R.id.matricula)).equals(d.getString("matricula"))
        && Ui.texto((EditText) findViewById(R.id.setor)).equals(d.getString("setor"));
  }

  protected void salvar() {
    EditText conta = findViewById(R.id.contaUid),
        nome = findViewById(R.id.nome),
        matricula = findViewById(R.id.matricula),
        setor = findViewById(R.id.setor);
    if (!Validacao.texto(Ui.texto(conta), 128) || Ui.texto(conta).contains("/")) {
      conta.setError("Informe o UID válido da conta.");
      return;
    }
    if (!Validacao.texto(Ui.texto(nome), 120)) {
      nome.setError("Informe o nome.");
      return;
    }
    if (!Validacao.texto(Ui.texto(matricula), 30)) {
      matricula.setError("Informe a matrícula (até 30 caracteres).");
      return;
    }
    if (!Validacao.texto(Ui.texto(setor), 80)) {
      setor.setError("Informe o setor.");
      return;
    }
    operacaoId = Ui.texto(conta);
    Colaborador c = new Colaborador();
    c.setNome(Ui.texto(nome));
    c.setMatricula(Ui.texto(matricula));
    c.setSetor(Ui.texto(setor));
    enviar(new ColaboradorRepository().salvar(operacaoId, c));
  }
}
