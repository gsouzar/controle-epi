# Conferência de entrega — ControleEPI

Auditoria do Projeto 04, páginas 3, 4, 11, 18 e 19 do enunciado. Gustavo de Souza e Gabriel Florindo.

| Item | Situação | Evidência ou trabalho restante |
|---|---|---|
| C1 | Parcial | Formulário, catálogo e regras implementados. Repetir cadastro com a versão final no Firebase real. |
| C2 | Parcial | Cadastro vinculado ao UID e matrícula. A ficha existente abriu com sessão real. Validar novo cadastro e busca na entrega final. |
| C3 | Parcial | Entrega com data e quantidade. Regras locais cobrem criação e consistência do estoque. Repetir o fluxo completo em dois aparelhos. |
| C4 | Parcial | Regras verificadas localmente para leitura própria, confirmação e bloqueio de acesso alheio. Demonstrar uma recusa e confirmar com uma conta real de colaborador. |
| C5 | Feito | Ficha com status de confirmação. Navegação até a ficha e retorno ao painel verificados com Firebase real. |
| C6 | Feito | Lista aberta no app real. Consulta inclui o dia inteiro do limite de 30 dias. Na demonstração, use um CA dentro e outro fora do prazo. |
| NC1 | Feito | Cadastro/login e sessão implementados. Ação Sair visível e retorno ao login verificados. |
| NC2 | Parcial | Listeners em Firestore. Gravar evidência de atualização entre dois dispositivos. |
| NC3 | Parcial | Regras no projeto. Suíte local com 14 verificações passou. Publicar a versão nova das regras para habilitar motivo e estoque. |
| NC4 | Feito | Perfil atribuído no console. Escrita de perfil pelo app é proibida. |
| NC5 | Feito | RecyclerView, carregamento e mensagens de lista vazia. |
| NC6 | Feito | Validação dos campos e mensagens em português. |
| NC7 | Feito | Formulário preserva texto e seleção. A versão anterior foi verificada com recriação no Android. |
| NC8 | Parcial | README, guia, capturas e .gitignore disponíveis. Entregar credenciais reais ao professor em canal privado e publicar o repositório. |
| ES1 | Feito | Seis histórias e critérios de aceite também no README. |
| ES2 | Parcial | Repositório e responsáveis identificados. Issues prontas. A integração ainda precisa de acesso ao repositório, e o quadro precisa ser criado. |
| ES3 | Parcial | Implementação local em branch de trabalho. O histórico original concentrou o MVP. PRs, autoria e revisões cruzadas reais ainda faltam. |
| Extra: matrícula | Feito no código | Filtro por matrícula como texto, preservando zeros à esquerda. |
| Extra: motivo | Parcial | Motivo opcional na entrega e na ficha. Regras locais validadas. Ativar publicando o novo firestore.rules. |
| Extra: estoque | Parcial | Saldo inicial, reposição, baixa vinculada à entrega e proteção contra saldo negativo. Regras locais testadas. Ativar no Firebase real e conferir no app. |
| APK debug | Feito | Build debug com o Firebase real da dupla e lint concluídos. |
| Slides PDF | Feito | Seis slides em PDF e fonte editável. |
| Vídeo | Não feito | Gravar duas sessões reais e disponibilizar link de vídeo não listado ou no Drive com acesso ao professor. |

## Ativar os complementares no Firebase real

1. Instale a versão atual nos aparelhos da dupla. A versão antiga não registra entregas para EPIs que já passaram a ter estoque controlado.
2. Em Firestore Database → Regras, guarde uma cópia das regras atuais e publique o conteúdo do arquivo `firestore.rules` desta versão. Os registros existentes continuam acessíveis e os EPIs existentes começam sem saldo informado.
3. No app do técnico, volte ao painel. Abra **EPIs e certificados → Estoque** e informe o saldo inicial. Para uma reposição, informe só a quantidade que chegou.
4. Em **Registrar entrega**, selecione o motivo quando houver uma troca. Para um EPI com estoque iniciado, a entrega e a baixa são salvas juntas. Confira o saldo na tela de estoque e o motivo na ficha.
5. Se aparecer erro de saldo, confira o saldo atual e reduza a quantidade ou registre uma reposição real.

## Roteiro de gravação

Gravem as duas telas com contas reais preparadas e omitindo senhas. Mostrem o técnico registrando uma entrega, o colaborador confirmando e o técnico vendo o status mudar. Em seguida, abram CAs vencendo, mostrem o isolamento com outra conta e mostrem o quadro e um PR revisado. O vídeo deve ser não listado ou compartilhado com o professor. O PDF contém orientações conflitantes de tempo e composição do grupo. Confirmem com o professor a duração e a autorização para a dupla.

Os testes automatizados desta revisão foram executados fora da pasta de entrega. O app distribuído usa o Firebase real e não contém modo de emulador, contas fictícias ou scripts de teste.
