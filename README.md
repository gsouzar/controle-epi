# ControleEPI

Aplicativo Android em Java para registrar entregas de equipamentos de proteção individual. Projeto de Gustavo de Souza e Gabriel Florindo.

## Executar

1. Abra esta pasta no Android Studio. Use o JDK integrado e instale o Android SDK 36 e Build Tools 36.
2. No console do Firebase, registre o app Android com o pacote `br.edu.unisenai.controleepi` e coloque o arquivo `google-services.json` em `app/`. O Git ignora esse arquivo.
3. No projeto Firebase, habilite a autenticação por e-mail e senha e configure o Cloud Firestore com as regras e o índice da dupla. Os perfis são definidos no campo `perfil` da coleção `usuarios`, usando o UID da conta como ID do documento.
4. Sincronize o Gradle e execute a configuração `app`.
