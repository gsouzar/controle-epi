# ControleEPI

Projeto 04 de Desenvolvimento de Aplicativos Móveis. **Integrantes:** Gustavo de Souza e Gabriel Florindo.

O app substitui a ficha de papel de EPIs. O técnico cadastra equipamentos e colaboradores, registra entregas e acompanha confirmações e vencimentos. O colaborador consulta e confirma as próprias entregas.

Android em Java com telas XML, Activities, RecyclerView, Material Components e repositories para Firebase Authentication e Cloud Firestore. A estrutura segue os exemplos de aula, principalmente MuralFilmes, RangoFile e Projeto 1.

## Conectar ao Firebase real

Abra também o [guia em texto simples](GUIA-FIREBASE.txt) no Bloco de Notas.

1. Abra a pasta do projeto no Android Studio. Use JDK 17 ou compatível com Gradle 9.1 e instale a plataforma Android 36 e o Build Tools 36.
2. No [console Firebase](https://console.firebase.google.com/), crie um projeto exclusivo para o ControleEPI e adicione um app Android com o identificador `br.edu.unisenai.controleepi`. Salve o arquivo baixado em `app/google-services.json`. O Git já ignora esse arquivo.
3. Em **Authentication → Sign-in method**, habilite **E-mail/senha**. Em **Firestore Database**, crie o banco padrão em modo de produção.
4. Publique as regras e o índice antes do teste. Com o Firebase CLI instalado e autenticado, execute `firebase deploy --only firestore:rules,firestore:indexes --project ID_DO_PROJETO`. Aguarde o índice de entregas ficar ativo no console. O índice usa `colaboradorUid ASC` e `data DESC`.
5. Recompile o aplicativo após salvar a configuração. No Windows, execute `.\gradlew.bat assembleDebug`. O APK sai em `app/build/outputs/apk/debug/app-debug.apk`.
6. Crie contas separadas para teste pelo app ou em **Authentication → Users**. Copie o UID de cada conta. No Firestore, crie `usuarios/{uid}` e atribua o campo string `perfil` com o valor exato `tecnico` ou `colaborador`.
7. Entre como técnico e vincule a conta de colaborador na tela **Cadastrar colaborador**, usando o UID, nome, matrícula e setor. Em seguida, cadastre EPIs e registre a primeira entrega.

Guarde as contas reais de teste com a dupla. Para validar o isolamento, cadastre uma segunda conta de colaborador. O acesso inicial aguarda o perfil atribuído pelo console. [Documentação de configuração do Firebase para Android](https://firebase.google.com/docs/android/setup).

## Demonstração

Cadastrem luvas de raspa com CA fictício 12345 e validade em 15 dias, óculos de proteção com CA fictício 23456 em 90 dias e protetor auricular com CA fictício 34567 vencido ontem.

1. No aparelho do técnico, abra o painel e registre uma entrega de um par de luvas à colaboradora Ana Souza, matrícula 001, setor Produção.
2. No aparelho da Ana, abra **Minhas entregas** e confirme o recebimento.
3. Deixe a ficha da Ana aberta no aparelho do técnico para mostrar a atualização automática.
4. Abra **CAs vencidos e vencendo**. Confira a luva e o protetor. O item em 90 dias deve ficar fora.
5. Use outra conta para demonstrar o isolamento. Confirme que a segunda conta não vê as entregas da primeira.

Consulte o [desenho](docs/ControleEPI-desenho.md), os [textos de issues e PRs](docs/ControleEPI-issues-prs.md), o [backlog](docs/ControleEPI-backlog.md) e a [checklist e revisão](docs/revisao.md).

## Capturas do emulador

[Login](docs/prints/login-local.png) · [Painel do técnico](docs/prints/painel-local.png) · [CAs vencidos e vencendo](docs/prints/vencimentos-local.png).

### Complementares

A busca por matrícula está em **Colaboradores e fichas**. Motivo de troca e estoque ficam visíveis ao técnico após publicar a versão atual de `firestore.rules`. Consulte a [conferência de entrega](docs/Entrega-professor.md) antes de iniciar um saldo.

## Histórias de usuário e critérios de aceite

### C1 — Cadastrar EPI

Como técnico de segurança, quero cadastrar EPIs com CA e validade para acompanhar os equipamentos entregues.
- Dado um técnico com descrição, CA e data válidos, quando salvar, então o EPI fica no Firestore e aparece na lista em tempo real.
- Dado descrição vazia, CA não numérico ou data inválida, quando salvar, então o formulário mostra mensagem em português e não cria documento.
- Dado um colaborador, quando tentar criar EPI diretamente no Firestore, então a regra nega.

### C2 — Cadastrar colaborador

Como técnico de segurança, quero vincular nome, matrícula e setor a uma conta para direcionar corretamente as entregas.
- Dado UID com perfil colaborador e campos válidos, quando cadastrar, então colaboradores/{uid} é criado e aparece na lista.
- Dado UID inexistente, perfil inadequado ou campos vazios, quando cadastrar, então não salva e explica o problema.
- Dado UID já cadastrado, quando tentar cadastrar novamente, então o registro existente não é sobrescrito.

### C3 — Registrar entrega

Como técnico de segurança, quero registrar EPI, colaborador, quantidade e data para manter o histórico de distribuição.
- Dado EPI e colaborador existentes e quantidade válida, quando registrar, então a entrega é criada pendente e vinculada ao UID correto.
- Dado quantidade zero, negativa, decimal ou fora do limite, quando salvar, então não grava e informa erro.
- Dado envio em andamento, quando tocar novamente ou girar a tela, então não surge uma segunda entrega para a mesma operação.

### C4 — Consultar e confirmar minhas entregas

Como colaborador, quero consultar e confirmar minhas entregas para registrar o recebimento.
- Dado entregas minhas e de outra pessoa, quando abrir a lista, então vejo somente as minhas.
- Dado entrega própria pendente, quando confirmar com sucesso no servidor, então confirmado passa a true e a tela mostra Confirmado.
- Dado tentativa de ler entrega alheia ou alterar quantidade, quando acessar diretamente o Firestore, então recebo permission-denied.
- Dado entrega confirmada, quando tentar voltar confirmado para false, então a operação é negada.

### C5 — Consultar ficha

Como técnico de segurança, quero ver todas as entregas de um colaborador para acompanhar seu histórico e suas confirmações.
- Dado colaborador selecionado, quando abrir a ficha, então aparecem somente as entregas desse UID, em ordem decrescente de data.
- Dado ficha aberta, quando o colaborador confirmar em outro aparelho, então o status muda sem recarregar.
- Dado colaborador sem entregas, quando abrir a ficha, então aparece estado vazio explicativo.

### C6 — Consultar vencimentos

Como técnico de segurança, quero consultar CAs vencidos ou vencendo em 30 dias para acompanhar a validade dos EPIs.
- Dado CAs de ontem, hoje, +30 e +31 dias, quando abrir a lista, então aparecem os três primeiros e o último fica fora.
- Dado CA anterior a hoje, quando exibir, então aparece Vencido; o CA de hoje aparece Vence hoje.
- Dado cadastro de EPI dentro do período em outro aparelho, quando o listener recebe a mudança, então a lista atualiza sozinha.

## Apresentação e GitHub

- [Apresentação em PDF](docs/apresentacao/ControleEPI-Apresentacao.pdf)
- [Roteiro de fala](docs/apresentacao/ControleEPI-Roteiro.md)
- [GitHub da dupla e links para abrir as issues](docs/GitHub-da-dupla.md)
- [Checklist do professor e ativação dos complementares](docs/Entrega-professor.md)

