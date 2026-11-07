# Checklist e revisão

O código de C1 a C6 e o fluxo de autenticação estão implementados. A confirmação em dois aparelhos com o Firebase real está pendente da configuração do projeto da dupla.

| Item | Estado | Evidência ou pendência |
|---|---|---|
| C1 | Parcial | Cadastro/lista implementados. Validadores e regra local verificados. Falta conferir no Firebase real. |
| C2 | Parcial | Vínculo por UID e proteção contra sobrescrita. Regra local verificada. Falta provisionar as contas reais. |
| C3 | Parcial | Registro com seleção, validação e ID reaproveitado em reenvio. Criação autorizada testada no emulador Firestore. |
| C4 | Parcial | Confirmação e isolamento testados no emulador Firestore. Falta teste com as contas reais. |
| C5 | Parcial | Ficha com listener e estado de confirmação. Falta validar em dois aparelhos com Firebase real. |
| C6 | Parcial | Consulta por data e limites testados em Java. Falta verificar com catálogo do projeto real. |
| NC1 | Parcial | Cadastro, login, sessão e saída implementados. Falta validar com Auth real. |
| NC2 | Parcial | Listas usam listeners. Falta validação em dois aparelhos. |
| NC3 | Parcial | 12 testes de regras passaram localmente. Publicação real pendente. |
| NC4 | Feito | O cliente não escreve perfil. Restrição testada no emulador. Atribuição real é feita no console. |
| NC5 | Feito | RecyclerView e estados vazio/carregando/erro nas listas. |
| NC6 | Feito | Validação em português e 8 testes JVM de validação/datas. |
| NC7 | Feito | Campos verificados após recriar a Activity no Android. A seleção é preservada por UID no Bundle. |
| NC8 | Parcial | README com integrantes, instalação, dados locais e .gitignore. Capturas locais incluídas. Faltam contas reais e publicação no GitHub. |
| ES1 | Feito | Seis histórias com 19 critérios Dado/Quando/Então. |
| ES2 | Parcial | Textos e responsáveis preparados. Falta montar o quadro e abrir issues no GitHub. |
| ES3 | Parcial | Branches e roteiro preparados. Falta abrir PRs e fazer revisão cruzada entre os integrantes. |

## Revisão independente com Superpowers

- Formulários destravam após falhas definitivas. Uma tentativa de cadastro duplicado mantém o documento original e informa o problema.
- A recuperação de cadastro de colaborador compara nome, matrícula e setor com os dados submetidos.
- Login e formulários mantêm a tarefa em execução durante a recriação da Activity. As confirmações pendentes são reconciliadas pelos metadados do Firestore.
- Seleções de entrega usam IDs e preservam as escolhas enquanto os catálogos recarregam. A lista é substituída depois de ler todos os documentos.
- Tipos inesperados no campo perfil são tratados sem acesso às telas protegidas.
- Regras negam por padrão, restringem o catálogo ao técnico e permitem ao colaborador confirmar somente suas entregas, alterando apenas confirmado para true.
- Uma falha de rede ambígua reaproveita o ID da operação. Um encerramento sem restauração de estado pode perder o rascunho.

Verificação local inicial: 8 testes JVM, 12 testes Firestore, compilação debug e lint. A entrega acadêmica ainda precisa do ensaio com as contas reais, slides, vídeo e PRs revisados.


Verificação final: 8 testes JVM, 12 testes de regras e 3 testes instrumentados passaram. O APK debug padrão e o lint foram gerados. A tela de CAs exibiu luvas com validade em 15 dias e protetor vencido e deixou os óculos com validade em 90 dias fora da lista.
O código consolidado está na branch local feat/controle-epi. A separação por história para os PRs do GitHub e a revisão entre os integrantes ainda estão pendentes.
