# Validação de recebimento em dois aparelhos

Este roteiro acompanha a [issue #5](https://github.com/gsouzar/controle-epi/issues/5). Registre o resultado do teste antes de marcar a história como Feito.

## Preparação

- Instale o [APK da entrega](https://github.com/gsouzar/controle-epi/releases/tag/v1.0.0) em dois aparelhos.
- Entre como técnico no primeiro e como colaborador no segundo. Confirme que o colaborador está vinculado ao cadastro pelo UID.
- Use um EPI e um colaborador de demonstração. Mantenha senhas e dados reais fora das evidências públicas.

## Critérios para verificar

| Ação | Resultado esperado |
| --- | --- |
| O técnico registra a entrega e abre a ficha do colaborador. | A entrega aparece como pendente. |
| O colaborador abre Minhas entregas. | Ele vê somente suas entregas, incluindo a recém-criada. |
| O colaborador confirma o recebimento. | O status muda para confirmado e a ficha aberta do técnico atualiza. |
| Outra conta de colaborador tenta ler a mesma entrega. | A consulta direta usando o UID da outra conta é negada pelas regras do Firestore. |
| O titular tenta alterar a quantidade junto da confirmação. | A operação é negada. Somente o campo confirmado pode mudar. |

Para conferir a recusa, use o simulador de regras no console do Firestore com o caminho do mesmo documento e o UID da outra conta. Confira que as regras testadas correspondem às publicadas.

## Evidências para o PR

Anote a data do teste, os aparelhos usados e o resultado de cada linha. Anexe capturas sem dados pessoais ou link do vídeo. Se um critério falhar, descreva como reproduzir e mantenha a issue aberta.
