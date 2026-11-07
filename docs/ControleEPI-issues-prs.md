# ControleEPI — textos completos de issues e PRs

Nenhuma publicação realizada. Substituir responsável por um integrante real e #N pelo número que o GitHub atribuir. Critérios abaixo são requisitos, não resultados de testes.

## Issue C1 — Cadastrar EPI

Responsável sugerido: Gustavo de Souza
Status inicial: A fazer

Como técnico de segurança, quero cadastrar EPIs com CA e validade para acompanhar os equipamentos entregues.
- Dado um técnico com descrição, CA e data válidos, quando salvar, então o EPI fica no Firestore e aparece na lista em tempo real.
- Dado descrição vazia, CA não numérico ou data inválida, quando salvar, então o formulário mostra mensagem em português e não cria documento.
- Dado um colaborador, quando tentar criar EPI diretamente no Firestore, então a regra nega.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C1 — Cadastrar EPI
Branch: feat/c1-epis

Este PR implementa cadastrar epi, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---

## Issue C2 — Cadastrar colaborador

Responsável sugerido: Gabriel Florindo
Status inicial: A fazer

Como técnico de segurança, quero vincular nome, matrícula e setor a uma conta para direcionar corretamente as entregas.
- Dado UID com perfil colaborador e campos válidos, quando cadastrar, então colaboradores/{uid} é criado e aparece na lista.
- Dado UID inexistente, perfil inadequado ou campos vazios, quando cadastrar, então não salva e explica o problema.
- Dado UID já cadastrado, quando tentar cadastrar novamente, então o registro existente não é sobrescrito.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C2 — Cadastrar colaborador
Branch: feat/c2-colaboradores

Este PR implementa cadastrar colaborador, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---

## Issue C3 — Registrar entrega

Responsável sugerido: Gustavo de Souza
Status inicial: A fazer

Como técnico de segurança, quero registrar EPI, colaborador, quantidade e data para manter o histórico de distribuição.
- Dado EPI e colaborador existentes e quantidade válida, quando registrar, então a entrega é criada pendente e vinculada ao UID correto.
- Dado quantidade zero, negativa, decimal ou fora do limite, quando salvar, então não grava e informa erro.
- Dado envio em andamento, quando tocar novamente ou girar a tela, então não surge uma segunda entrega para a mesma operação.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C3 — Registrar entrega
Branch: feat/c3-entregas

Este PR implementa registrar entrega, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---

## Issue C4 — Consultar e confirmar minhas entregas

Responsável sugerido: Gabriel Florindo
Status inicial: A fazer

Como colaborador, quero consultar e confirmar minhas entregas para registrar o recebimento.
- Dado entregas minhas e de outra pessoa, quando abrir a lista, então vejo somente as minhas.
- Dado entrega própria pendente, quando confirmar com sucesso no servidor, então confirmado passa a true e a tela mostra Confirmado.
- Dado tentativa de ler entrega alheia ou alterar quantidade, quando acessar diretamente o Firestore, então recebo permission-denied.
- Dado entrega confirmada, quando tentar voltar confirmado para false, então a operação é negada.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C4 — Consultar e confirmar minhas entregas
Branch: feat/c4-confirmacao

Este PR implementa consultar e confirmar minhas entregas, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---

## Issue C5 — Consultar ficha

Responsável sugerido: Gustavo de Souza
Status inicial: A fazer

Como técnico de segurança, quero ver todas as entregas de um colaborador para acompanhar seu histórico e suas confirmações.
- Dado colaborador selecionado, quando abrir a ficha, então aparecem somente as entregas desse UID, em ordem decrescente de data.
- Dado ficha aberta, quando o colaborador confirmar em outro aparelho, então o status muda sem recarregar.
- Dado colaborador sem entregas, quando abrir a ficha, então aparece estado vazio explicativo.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C5 — Consultar ficha
Branch: feat/c5-ficha

Este PR implementa consultar ficha, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---

## Issue C6 — Consultar vencimentos

Responsável sugerido: Gabriel Florindo
Status inicial: A fazer

Como técnico de segurança, quero consultar CAs vencidos ou vencendo em 30 dias para acompanhar a validade dos EPIs.
- Dado CAs de ontem, hoje, +30 e +31 dias, quando abrir a lista, então aparecem os três primeiros e o último fica fora.
- Dado CA anterior a hoje, quando exibir, então aparece Vencido; o CA de hoje aparece Vence hoje.
- Dado cadastro de EPI dentro do período em outro aparelho, quando o listener recebe a mudança, então a lista atualiza sozinha.

### Tarefas
- Implementar a história seguindo Activities/XML, Adapter e Repository quando aplicável.
- Aplicar a política de segurança e os estados vazio, erro e carregando pertinentes.
- Verificar os critérios acima e registrar evidências reais.
- Atualizar checklist e solicitar revisão a outro integrante.

### PR correspondente
Título: C6 — Consultar vencimentos
Branch: feat/c6-vencimentos

Este PR implementa consultar vencimentos, conforme a história vinculada. Descrever aqui o comportamento efetivamente implementado e os arquivos principais alterados antes da publicação.

Validação: PENDENTE — preencher comandos e resultados reais, incluindo os critérios de aceite acima.
Limitações: PENDENTE — registrar configuração Firebase, testes em aparelho e publicação de regras que faltarem.
Revisor: o outro integrante da dupla
Closes #N

---



