# ControleEPI

## Code Review Rules

- Verifique a autorização no Firestore. Uma mudança não pode permitir que um colaborador leia, confirme ou altere entregas de outro UID. Na confirmação, só o campo `confirmado` pode mudar para verdadeiro. O cliente também não pode se atribuir um perfil.
- Em alterações de estoque, confira a atomicidade da entrega e da baixa e a idempotência do reenvio. Uma falha ou nova tentativa não pode gerar saldo negativo, duplicar entrega ou contabilizar a mesma baixa duas vezes.
- Em mudanças de login, saída e telas autenticadas, procure dados de outra sessão reaparecendo após logout, rotação ou callback atrasado. Ao reportar, indique o arquivo, o cenário que reproduz a falha e a consequência.

Relate achados para a dupla. A aprovação e a integração são feitas pelos integrantes.
