# Roteiro de apresentação do ControleEPI

Gustavo de Souza e Gabriel Florindo  
Projeto 04 de Desenvolvimento de Aplicativos Móveis

**Tempo de ensaio:** 10 minutos de apresentação e 3 de perguntas. A página 18 do enunciado informa 10 minutos de apresentação, mas os tempos da tabela somam 15 minutos antes das perguntas. A divisão abaixo preserva os 5 minutos da demonstração e segue os 10 minutos anunciados no texto.

## Antes de apresentar

- Deixem dois aparelhos ou emuladores já conectados ao mesmo ambiente. Um deve estar no perfil técnico e o outro no perfil colaborador. Mantenham os dois lado a lado na projeção.
- Validem o Firebase e as permissões no ambiente que será usado. Deixem uma segunda conta de colaborador preparada para o teste de isolamento.
- Usem um colaborador fictício vinculado à conta dele e um catálogo com luvas de raspa que vencem em 15 dias, protetor auricular vencido ontem e óculos de proteção que vencem em 90 dias. Ajustem as datas à data da apresentação.
- Deixem abertos o console ou a interface local dos emuladores, o teste de regras e, quando disponíveis, o GitHub Projects e o PR revisado.
- Ensaiem a troca entre slides e aparelhos. Para o vídeo, gravem o mesmo fluxo e confiram o acesso ao link antes da entrega.

## 1. ControleEPI · Gustavo · 0:00–1:15

“Imagine que, durante uma fiscalização, pedem o histórico de quem recebeu um par de luvas. Se a ficha de papel estiver incompleta ou tiver sumido, o técnico precisa reconstruir a informação. Em outra parte da rotina, a data do Certificado de Aprovação pode passar despercebida. O ControleEPI leva essa ficha para o celular e reúne as datas de validade do CA numa consulta.”

## 2. Dois perfis · Gabriel · 1:15–2:00

“O técnico de segurança cadastra o EPI com seu CA e sua validade. Ele vincula o colaborador a uma conta, registra a entrega e abre a ficha para acompanhar o recebimento. Já o colaborador consulta as próprias entregas e confirma cada uma na sua conta. Vamos mostrar esse fluxo nos dois aparelhos.”

## 3. Demonstração · Gustavo e Gabriel · 2:00–7:00

| Tempo | Quem | Ação e resultado esperado |
|---|---|---|
| 2:00–3:20 | Gustavo | Em **Registrar entrega**, selecione o colaborador fictício e **Luvas de raspa**, informe a quantidade 1, use a data da demonstração e salve. Abra a ficha no perfil técnico e mostre a entrega pendente. |
| 3:20–4:35 | Gabriel | Abra **Minhas entregas** no segundo aparelho. Confira se a entrega aparece e confirme o recebimento. |
| 4:35–5:30 | Gustavo | Com a ficha ainda aberta no primeiro aparelho, mostre a mudança para confirmado. Aponte o registro que mudou. |
| 5:30–7:00 | Gustavo | Abra **CAs vencidos e vencendo**. Mostre o protetor vencido e as luvas com validade próxima. Confira que os óculos, com vencimento em 90 dias, ficam fora. |

Falas curtas durante o fluxo

- Gustavo — “Registrei um par de luvas. Na ficha, a entrega está pendente de confirmação.”
- Gabriel — “A entrega apareceu em Minhas entregas. Vou confirmar o recebimento.”
- Gustavo — “A ficha atualizou com a confirmação. Nesta outra tela, aparecem os CAs vencidos e os que vencem nos próximos 30 dias.”

## 4. Dados e segurança · Gabriel · 7:00–8:20

“Entramos com Firebase Authentication. O campo perfil fica no documento `usuarios/{uid}`, atribuído no console. A entrega guarda o UID do colaborador. O Firestore compara esse UID com o da sessão para autorizar a leitura. Na confirmação, a regra só permite que o dono altere o campo `confirmado` para verdadeiro. O listener atualiza a ficha aberta quando o registro muda.”

Mostre as quatro coleções e a regra em `firestore.rules`. Com a segunda conta, mostre que a primeira entrega não aparece em **Minhas entregas**. Para evidenciar a regra no servidor, execute ou mostre o resultado do teste de leitura direta de uma entrega de outra pessoa. Se estiverem usando o Emulator Suite, identifiquem o ambiente como local. O console administrativo tem privilégios próprios e serve para mostrar as coleções.

## 5. Histórias e revisão · Gustavo · 8:20–9:15

“Uma das nossas histórias descreve a confirmação de recebimento. Dado que existe uma entrega pendente, quando o colaborador confirma, então o técnico vê a mudança na ficha. Foi o que acabamos de demonstrar.”

Se o quadro e o PR estiverem disponíveis no GitHub, mostre a issue, o responsável, a coluna e a revisão do outro integrante. Caso estejam pendentes, apresente o backlog e a história preparados localmente e diga o que falta publicar e revisar.

## 6. Próximos passos · Gabriel · 9:15–10:00

“O acesso inicial depende de atribuir o perfil no console. O próximo passo é repetir o fluxo com o Firebase da dupla e os dois aparelhos. Com mais tempo, podemos avaliar quais complementares entram primeiro, como motivo da troca, controle de estoque e busca por matrícula.”

## Perguntas · 10:00–13:00

Os dois integrantes devem saber localizar as regras de acesso, explicar o vínculo por UID, descrever como a confirmação chega à ficha e apontar o recorte de 30 dias. Se perguntarem pela validação, distingam testes no emulador e ensaio com o projeto Firebase da dupla.
