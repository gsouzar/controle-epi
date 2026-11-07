# ControleEPI — desenho técnico
Data: 07/11/2026. Estado: proposta para aprovação; nenhum app implementado ou regra publicada.

## Objetivo e escopo
Aplicativo Android Java para o Projeto 04 do enunciado TechInova, permitindo registrar, consultar e confirmar entregas de EPIs. Técnico administra cadastros e entregas; colaborador acessa somente seus próprios registros. A apresentação deve demonstrar atualização entre dois aparelhos e uma operação proibida bloqueada pelo servidor.

Escopo: C1–C6, NC1–NC8, ES1–ES3. Sem câmera, QR, Cloudinary, estoque, motivo de troca ou busca por matrícula nesta fase. Firebase Auth, Cloud Firestore e Material Components; Android SDK e AndroidX são componentes oficiais permitidos. Bibliotecas de teste ficam fora do app de produção.

O PDF foi lido integralmente. Sua capa cita Cloudinary genericamente, mas o Projeto 04 não depende dele. O enunciado também pede APK debug, slides PDF e vídeo; serão registrados como entregáveis acadêmicos, sem afirmar que foram produzidos. Há divergências no tempo da apresentação entre a capa e a página 18; o roteiro funcional independe disso e a duração final deve ser confirmada com o professor.

## Abordagem e referências
Escolhida após o usuário autorizar continuidade: Activities + XML + Repository. Alternativa considerada: MVVM com ViewModels, que acrescenta separação de estado e mais estrutura do que os exemplos. Usar camadas simples para facilitar a explicação em aula.
- MuralFilmes_Start: model, adapter, data, Repository, listener e RecyclerView.
- RangoFile_Start: autenticação com e-mail/senha e navegação por Intent.
- Projeto_1: restauração de estado via Bundle.
- Projeto_2: lista com Adapter/ViewHolder e formulários.
- emendaai_Start: estados de carregamento, erro e vazio.
Os demais projetos foram inventariados como referências; não há necessidade de copiar integrações ou recursos fora deste domínio.
Reutilizar os padrões, sem copiar listas static, assert como tratamento de erro, inicialização de formulário dentro de callback de insets ou mensagens brutas de exceção.

Destino proposto: pasta ControleEPI.
A pasta ainda não foi criada. O chat tem permissão de escrita em outra pasta; a gravação no destino exigirá autorização do ambiente.
O AGENTS.md de MuralFilmes_Start vale para aquele projeto de referência; ele não será alterado. O projeto novo terá seu próprio escopo.

## Estrutura proposta
Pacote: br.edu.unisenai.controleepi.
- Raiz: ControleEpiApp e Activities (Login, AguardandoPerfil, PainelTecnico, Epis, CadastroEpi, Colaboradores, CadastroColaborador, NovaEntrega, MinhasEntregas, FichaColaborador, CasVencendo).
- model: Epi, Colaborador, Entrega, Usuario.
- adapter: EpiAdapter, ColaboradorAdapter, EntregaAdapter; interfaces de ação sem lógica de Firebase.
- data: AuthRepository, UsuarioRepository, EpiRepository, ColaboradorRepository, EntregaRepository.
- util: Datas, Validacao, MensagensErro.
- res/layout: XML de cada tela e item de lista.
- res/values: strings, cores, tema e dimensões; drawables vetoriais locais.
- firestore.rules, firestore.indexes.json, firebase.json: configuração e políticas.
- docs: histórias, backlog, PRs, checklist, demonstração e revisão.
- .gitignore: google-services.json, local.properties, chaves, build e arquivos locais de IDE.

A Activity controla UI e ciclo de vida; Repository retorna Task/ListenerRegistration. Modelos têm construtor vazio, getters e setters compatíveis com Firestore. IDs de documentos são obtidos do snapshot, sem exigir um campo id duplicado.

## Identidade visual — CodeMakers-Design
Direção escolhida: ferramenta operacional clara, verde profundo e sinalização âmbar. Alternativa considerada: painel industrial escuro; fundo claro favorece a projeção e leitura de formulários.
- Primária #14532D com texto branco; fundo #F6F8F5; texto #18221C.
- Atenção: fundo #FEF3C7 com texto #78350F. Erro: #B91C1C sobre branco.
- Tipografia nativa, corpo de 16sp, títulos de 24sp, informações secundárias legíveis.
- Margens de 16dp; espaçamentos de 8/16/24dp; alvos de toque de pelo menos 48dp.
- Status com texto e ícone: Pendente, Confirmado, Vencido, Vence em breve.
- Botões principais com ícone vetorial e rótulo, labels permanentes nos campos.
- Scroll nos formulários, suporte a teclado e fonte ampliada; evitar altura fixa para texto.
- Carregamento, vazio, erro com Tentar novamente e estado aguardando sincronização.
Esses valores são decisões de projeto; a implementação precisa verificar contraste e renderização em retrato, paisagem e fonte ampliada. Não foi feita revisão visual de telas executáveis.

## Fluxos
Login → cadastro ou entrar → leitura do próprio usuarios/{uid} no servidor.
- Perfil tecnico: painel com Registrar entrega, EPIs, Colaboradores e CAs vencendo.
- Perfil colaborador: Minhas entregas; confirmação individual.
- Documento ausente ou perfil desconhecido: Aguardando liberação, UID copiável, Verificar acesso e Sair.
- Falha de rede ao consultar perfil: estado de erro com repetição; nunca inferir autorização pelo cache.
Cadastro Auth não cria perfil e não libera uma tela operacional por padrão.

Técnico: EPIs → Novo EPI; Colaboradores → Novo colaborador ou Ficha; Nova entrega → selecionar colaborador e EPI → quantidade/data → registrar.
Colaborador: lista própria → confirmar recebimento → aguardar confirmação do servidor → status Confirmado.
Menu Sair em todas as telas autenticadas; limpar pilha de navegação e dados visíveis.

## Provisionamento e vínculo de contas
1. A pessoa se cadastra pelo app (e-mail/senha).
2. Administrador do grupo cria usuarios/{uid} no console com perfil tecnico ou colaborador.
3. Técnico informa UID no cadastro do colaborador e preenche nome, matrícula e setor.
4. App e rules verificam existência de usuarios/{uid} com perfil colaborador.
O documento colaboradores usa exatamente o UID de Auth. UID pode ser copiado na tela de espera, evitando digitação extensa. Não pesquisar contas Auth por e-mail no cliente, não embutir Admin SDK ou chave administrativa.
Usuário com perfil colaborador ainda não vinculado vê mensagem de cadastro pendente, sem lista global.
Integrantes e contas reais de teste ainda não foram fornecidos; usar campos explicitamente pendentes no README, nunca inventar contas criadas.

## Modelo de dados
| Caminho | Campos e tipos |
|---|---|
| usuarios/{uid} | perfil: string (tecnico ou colaborador), administrado no console |
| epis/{id} | descricao: string 1–120; ca: string de 1–20 dígitos; validadeCa: Timestamp |
| colaboradores/{uid} | nome: string 1–120; matricula: string 1–30; setor: string 1–80 |
| entregas/{id} | colaboradorUid: string; epiId: string; epiDescricao: string; epiCa: string; quantidade: inteiro 1–9999; data: Timestamp; confirmado: boolean |

epiDescricao e epiCa são cópias históricas verificadas pelas rules contra o EPI na criação. O colaborador pode ler a descrição na própria entrega sem acessar todo o catálogo. No CORE os cadastros são de criação e consulta; edição/exclusão de históricos não será disponibilizada.
Número de CA como string preserva zeros. Exemplos usam números fictícios identificados como demonstração, sem alegação de certificação válida.
Matrícula obrigatória, sem promessa de unicidade no CORE; não acrescentar um índice de busca complementar.

Datas civis (validade CA e data da entrega) representadas em Timestamp normalizado para 12h UTC da data escolhida; exibir a data usando UTC, sem conversão para o fuso do aparelho. Hoje é calculado no fuso America/Sao_Paulo. CA de hoje permanece válido hoje; vencido se data anterior a hoje. Consulta inclui todos os vencidos e os que vencem até hoje + 30 dias, inclusive. Data de entrega padrão hoje, podendo ser passada, sem data futura na validação da UI.

## Consultas e ciclo de vida
- EPIs: orderBy descricao.
- Colaboradores: orderBy nome.
- Minhas entregas: whereEqualTo colaboradorUid = Auth UID, orderBy data desc.
- Ficha: mesma consulta, usando UID escolhido pelo técnico.
- CAs: whereLessThanOrEqualTo validadeCa = limite inclusivo de 30 dias, orderBy validadeCa.
Índice composto entregas: colaboradorUid ASC + data DESC. Demais usam índices simples.
Listeners iniciados em onStart e removidos em onStop; evitar duplicação após rotação. Em erro, tratar primeiro e retornar; snapshot nulo não é lista vazia.
Dados visíveis carregados somente depois de resolver o perfil. Limpar adapters e remover listeners ao sair/trocar conta. Configurar cache em memória antes do primeiro uso do Firestore; nunca mostrar cache de outro UID.
Acesso offline não é promessa do MVP. Indicar snapshot em cache e gravação pendente; sucesso só após resposta do servidor.
Guardar UID da sessão ao iniciar operações e ignorar callbacks se a sessão mudou.

Formulários com IDs estáveis preservam texto pela restauração de estado; seleção de colaborador/EPI, datas, ID da operação e estado relevante ficam em onSaveInstanceState. Sem listas static e sem senha em arquivo, log ou estado manual.
Gerar um ID para cada nova entrega antes de salvar e reutilizá-lo em repetição/rotação. Bloquear duplo toque durante envio. Em resposta ambígua, consultar esse ID no servidor antes de nova tentativa; impedir criar uma segunda entrega automaticamente.
Confirmação de recebimento usa update apenas de confirmado=true, sem toggle ou desfazer. Repetição idempotente não altera outros campos.

## Contrato das Firestore Security Rules
Proposta de política, ainda sem publicação ou teste:
- Negar qualquer acesso não explicitamente permitido; exigir Auth nas coleções operacionais.
- Resolver perfil por get usuarios/request.auth.uid, sem confiar em extras de Intent.
- usuarios: leitura somente do próprio documento; técnico pode obter por ID documento de perfil colaborador para validar vínculo; list proibido. Nenhuma escrita pelo cliente, inclusive pelo técnico.
- epis: somente técnico lê e cria. Tipos, conjunto exato de campos, tamanhos, CA numérico e Timestamp validados. Sem update/delete no CORE.
- colaboradores: técnico lista/lê/cria; colaborador lê só o próprio documento. Criação exige usuarios/{uid}.perfil == colaborador; campos exatos e tamanhos válidos. Sem update/delete.
- entregas: técnico lê todas; colaborador lê apenas onde colaboradorUid == request.auth.uid e perfil == colaborador.
- Criação somente por técnico, confirmado=false, referências existentes e perfil do destinatário colaborador, quantidade inteira no intervalo, Timestamp e campos exatos. Snapshots descricao/ca devem corresponder ao EPI referenciado.
- Atualização somente por colaborador dono: diff(...).affectedKeys().hasOnly(['confirmado']), valor final boolean true; nenhum outro campo pode mudar. Escrita repetida true é permitida como operação sem alteração.
- Exclusão de entrega proibida; técnico não confirma pelo colaborador.
As queries do colaborador incluem o filtro do UID: regras não filtram uma consulta global.
Regras serão implementadas junto à primeira funcionalidade de dados, e consolidadas/auditadas na última etapa; nunca depender de modo de teste aberto até o final.

## Histórias CORE — escritas antes do código
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

## Validação e evidências exigidas
- Build e lint Android, testes JVM de datas/validação, testes de rules no Firebase Emulator Suite.
- Rules: não autenticado; perfil ausente/desconhecido; elevação de perfil; técnico legítimo; UID alheio; confirmação indevida; alteração de campos; tipos e referências inválidas; queries corretas/incorretas.
- UI: cadastrar/login/sair/reabrir; perfis; rotação com formulário preenchido; quantidade inválida; ausência de dados; falha de rede; fonte ampliada; restauração sem duplicação.
- Integração em dois aparelhos: entrega → lista do colaborador → confirmação → ficha do técnico.
- Publicação das rules e teste real separados dos testes locais. Sem Firebase configurado não marcar integração como concluída.
- Revisão final Superpowers: segurança, duplicidade, nulos, ciclo de vida, credenciais e requisitos. Registrar problemas encontrados, correções e limitações.
Não afirmar revisão/aprovação humana de PR por ter ocorrido revisão de IA.

## Sequência de entrega
1. Auth e perfis; 2. EPIs; 3. colaboradores; 4. entregas; 5. visão do colaborador; 6. ficha; 7. CAs; 8. consolidação de segurança.
Antes disso: aprovação deste desenho e plano de implementação. Cada história em branch própria, sem commit direto na main. Integração por PR revisado por outro integrante. Se não houver remoto/grupo definido, preparar os textos e manter ES2/ES3 parciais; não simular histórico ou responsáveis.
Dados de exemplo: luvas de raspa, óculos de proteção e protetor auricular, um vencido, outro em +15 dias e outro em +90 dias da preparação.
Roteiro: técnico entrega um par de luvas → colaborador confirma → técnico acompanha ficha → mostra CA vencendo → demonstra uma operação proibida.
Decisões reversíveis de implementação (nomes auxiliares, organização dos XML, SDK compatível com ambiente) ficam a cargo do implementador. Firebase alvo, contas reais, repositório remoto e integrantes serão informados quando necessários.

## Fontes técnicas consultadas
- https://firebase.google.com/docs/firestore/security/rules-fields — restrição de campos e tipos.
- https://firebase.google.com/docs/firestore/manage-data/enable-offline — cache e estados offline.


## Ajustes após revisão independente do desenho
A revisão identificou quatro pontos, resolvidos abaixo como decisões explícitas do projeto; nenhuma pergunta adicional de produto é necessária.
1. Entrega atual é desenho e documentação de engenharia. Código, README e testes seguem após aprovação. APK é resultado esperado da implementação; slides/vídeo são entregáveis acadêmicos posteriores e continuam pendentes, sem criação fictícia.
2. Administrador do grupo verifica a correspondência entre conta e pessoa antes de atribuir perfil no console; técnico confere UID e matrícula antes de cadastrar. Demonstração usa pessoas fictícias e contas controladas pelo grupo. Correção de vínculo incorreto é intervenção administrativa no console: retirar o perfil incorreto, avaliar entregas afetadas e corrigir vínculo/histórico sob conferência do grupo, sem recurso de transferência de UID no cliente. Não prometer uma operação simples de troca de dono sem analisar histórico.
3. Limite de data futura é conveniência de validação da UI, não requisito CORE de segurança. As rules exigem Timestamp e acesso autorizado; não se afirma que impedem datas futuras enviadas por um técnico via cliente modificado. A normalização de datas é contrato dos repositories e testes, não propriedade garantida pelas rules. Antes de ampliar isso, definir regra civil compatível com o horário do servidor.
4. Prevenir duplicação cobre duplo toque, rotação e restauração Android com estado salvo. Congelar payload e ID enquanto o envio for ambíguo. Não prometer recuperação automática de rascunho após encerramento sem estado; ao voltar, consultar o histórico sincronizado antes de registrar novamente. Cache em memória não fornece fila durável após encerramento do processo.
Mensagem para UID inexistente/não liberado/não permitido: “UID sem perfil de colaborador liberado. Confira o cadastro no console.” Não divulgar o perfil de outra conta.

## Matriz literal do núcleo comum e evidências
| ID | Requisito | Prova planejada |
|---|---|---|
| NC1 | Cadastro/login e-mail e senha; sessão persiste; menu Sair | Criar conta, entrar, fechar/reabrir e sair |
| NC2 | Firestore e listas principais com snapshot listener | Dois aparelhos, criação/confirmacão sem recarregar |
| NC3 | Regras escritas pelo grupo com controle de perfil | Testes de rules e recusa real no servidor |
| NC4 | usuarios/{uid}.perfil definido no console, nunca pelo cliente | Tentativa de criar/editar perfil negada, inclusive técnico |
| NC5 | RecyclerView e estado vazio | Listas vazias e preenchidas em cada perfil |
| NC6 | Validação e mensagens em português, sem crash com entrada inválida | Campos vazios, números inválidos, nulos e falha de rede |
| NC7 | Rotação preserva o que estava sendo digitado | Girar formulários e conferir texto, seleção e datas |
| NC8 | GitHub/README com integrantes, problema, prints, configuração e contas; JSON fora do repo | Inspecionar README, gitignore e arquivos rastreados |
| ES1 | Uma história por CORE, pelo menos dois critérios Dado/Quando/Então | Seis histórias e 19 critérios escritos neste documento |
| ES2 | Project A fazer/Fazendo/Feito, issues com responsável | Conferir quadro real; não fechar história sem demonstrar critérios |
| ES3 | Branch por história, PR com Closes #N e revisão por outro integrante | Conferir histórico real, autoria e revisões humanas |

## Verificações desta etapa
Arquivos lidos de volta após gravação; seis histórias CORE e 19 critérios encontrados.
Contrastes calculados com a ferramenta CodeMakers-Design: branco/#14532D = 9,11:1; #78350F/#FEF3C7 = 8,15:1. Ambos passam o limite 4,5:1 para texto normal. A análise é só de pares sRGB opacos e não comprova acessibilidade integral.
Revisão independente de planejamento realizada; observações incorporadas acima. Build, testes Android, testes de rules, publicação Firebase e revisão final do app ainda não realizados.

