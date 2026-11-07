# GitHub da dupla

[Repositório do ControleEPI](https://github.com/gsouzar/controle-epi). Gustavo usa `gsouzar` e Gabriel usa `GabbFlor`.

## Issues de aceite

- [C1 — Cadastrar EPI](https://github.com/gsouzar/controle-epi/issues/1) · Gustavo de Souza, atribuído
- [C2 — Cadastrar colaborador](https://github.com/gsouzar/controle-epi/issues/4) · Gabriel Florindo, atribuído
- [C3 — Registrar entrega](https://github.com/gsouzar/controle-epi/issues/2) · Gustavo de Souza, atribuído
- [C4 — Consultar e confirmar minhas entregas](https://github.com/gsouzar/controle-epi/issues/5) · Gabriel Florindo, atribuído
- [C5 — Consultar ficha](https://github.com/gsouzar/controle-epi/issues/3) · Gustavo de Souza, atribuído
- [C6 — Consultar vencimentos](https://github.com/gsouzar/controle-epi/issues/6) · Gabriel Florindo, atribuído

## Revisão

1. Gustavo e Gabriel já têm acesso e as seis issues estão atribuídas.
2. No [GitHub Projects](https://github.com/users/gsouzar/projects), adicione as seis issues e use **A fazer**, **Fazendo** e **Feito**.
3. O PR de importação reúne o aplicativo já desenvolvido. A branch `feature/mvp-controle-epi` segue para `develop`. A versão aprovada segue para `main` em um PR de release.
4. Cada integrante deve abrir e revisar PRs reais nos próximos ajustes. Usem uma branch por história e `Closes #N` depois de demonstrar seus critérios.
5. Entreguem as credenciais ao professor por canal privado. O `google-services.json` permanece local.


## Convenções GitFlow

| Branch | Uso | Destino do PR |
|---|---|---|
| main | Versão aprovada da entrega | Recebe releases e correções urgentes |
| develop | Integração do próximo ciclo | Recebe features e correções de desenvolvimento |
| feature/<issue>-<assunto> | Mudança ligada a uma história | develop |
| release/<versao> | Preparação de uma versão a partir de develop | main, com integração das correções de volta a develop |
| hotfix/<issue>-<assunto> | Correção urgente a partir de main | main e develop |

As branches main e develop exigem PR com uma aprovação, conversas resolvidas e nova revisão quando o diff mudar. A regra vale também para administradores. Force push e exclusão estão bloqueados nessas duas branches.
