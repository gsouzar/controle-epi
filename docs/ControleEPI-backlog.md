# ControleEPI — issues e roteiro de PRs
Plano inicial. A situação atual está em revisao.md. A equipe é Gustavo de Souza e Gabriel Florindo.
Criar Project com A fazer, Fazendo, Feito. Cada responsável abaixo precisa ser substituído por um integrante real antes de iniciar. Feito exige todos os critérios demonstrados.
As histórias e critérios completos estão no desenho técnico; copiar integralmente a seção correspondente no corpo de cada issue.

| Issue sugerida (não é número GitHub) | Título | Responsável | Branch | Commit |
|---|---|---|---|---|
| AUTH | Cadastro, login e acesso por perfil | Gustavo de Souza | feat/auth-perfis | feat: adiciona autenticacao e acesso por perfil |
| C1 | Cadastro e listagem de EPIs | Gustavo de Souza | feat/c1-epis | feat: cadastra epis e validade do ca |
| C2 | Cadastro de colaboradores por UID | Gabriel Florindo | feat/c2-colaboradores | feat: vincula colaboradores as contas |
| C3 | Registro de entrega | Gustavo de Souza | feat/c3-entregas | feat: registra entregas de epis |
| C4 | Minhas entregas e confirmação | Gabriel Florindo | feat/c4-confirmacao | feat: permite confirmar recebimento proprio |
| C5 | Ficha do colaborador | Gustavo de Souza | feat/c5-ficha | feat: mostra historico do colaborador |
| C6 | CAs vencidos e próximos do vencimento | Gabriel Florindo | feat/c6-vencimentos | feat: consulta cas por vencimento |
| SEG | Auditoria de segurança e núcleo comum | Gabriel Florindo | test/seguranca-core | test: verifica regras e fluxos do core |

## Corpo de issue CORE — pronto para preencher com a história correspondente
Título: usar a linha correspondente.
Responsável: substituir por @usuario real.
História e critérios: copiar a seção C1, C2, C3, C4, C5 ou C6 do desenho, integralmente.
Escopo técnico: Activity/XML, modelo e Repository quando necessários; regras restritivas desde esta etapa.
Concluído quando: critérios demonstrados, testes pertinentes passando, checklist atualizado e PR revisado por outro integrante.
Evidências: registrar comandos executados e capturas reais. Não usar prints de mockup como prova de funcionamento.

## Issue AUTH
Como usuário, quero cadastrar uma conta e entrar com e-mail/senha para acessar apenas as funções do meu perfil.
- Dado cadastro válido, quando criar conta, então Auth registra e o app aguarda perfil atribuído no console.
- Dado perfil tecnico ou colaborador, quando entrar, então o app abre a área correta.
- Dado conta autenticada, quando reabrir, então a sessão continua; quando sair, então volta ao login e limpa a pilha.
- Dado erro de rede, senha inválida ou perfil ausente, quando entrar/verificar, então recebe mensagem em português sem acesso indevido.
Responsável: A definir. Branch: feat/auth-perfis.

## Issue SEG
Como grupo, quero verificar isolamento de acesso e núcleo comum para demonstrar o CORE com segurança.
- Dado colaborador autenticado, quando testar UID alheio e alteração de perfil/quantidade, então o servidor nega.
- Dado duas contas de perfis distintos, quando executar o roteiro, então as mudanças aparecem em tempo real e a checklist contém as evidências.
- Dado relatório final, quando marcar Feito, então os testes locais e a publicação real estão diferenciados.
Responsável: A definir. Branch: test/seguranca-core.

## Texto de PR por história
Título: mesmo título da issue.
Problema e resultado: descrever o comportamento concreto entregue pela história (ex.: o colaborador passa a ver e confirmar somente suas entregas).
Implementação: indicar telas, Repository e regras afetados.
Validação: colar somente comandos realmente executados e resultados; relacionar os critérios demonstrados.
Pendências: listar o que depende de configuração, publicação ou aparelho.
Closes #N (substituir N pelo número REAL da issue).
Revisor: outro integrante deve rodar a branch e avaliar os critérios antes de aprovar.

## Checklist inicial
| ID | Estado | Evidência / falta |
|---|---|---|
| C1–C6 | Não feito | Histórias escritas; implementação pendente |
| NC1–NC7 | Não feito | Contratos definidos; implementação e testes pendentes |
| NC8 | Não feito | README, integrantes, prints, Firebase e contas pendentes |
| ES1 | Feito (documentação) | Seis histórias com pelo menos dois critérios Dado/Quando/Então no desenho |
| ES2 | Parcial | Roteiro preparado; faltam issues reais, Project e responsáveis |
| ES3 | Parcial | Fluxo e branches propostos; faltam PRs e revisões humanas |
| APK debug | Não feito | Build pendente |
| Slides PDF | Não feito | App e capturas pendentes |
| Vídeo | Não feito | Demonstração real pendente |
| Revisão final | Não feito | Revisão do desenho não substitui auditoria do app |
