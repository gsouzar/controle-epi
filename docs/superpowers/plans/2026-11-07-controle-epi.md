# ControleEPI Implementation Plan
> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.
**Goal:** Implementar C1–C6 em Java com autenticação, isolamento por UID e atualização em tempo real.
**Architecture:** Activities XML, modelos, adapters e repositories, conforme referências. Formulários preservam estado e operações não repetem escrita após rotação.
**Tech Stack:** Java 17, AndroidX, Material, Firebase Auth e Firestore; Gradle 9.1/AGP 9.0.1, compile/target SDK 36, min SDK 26.
**Spec:** ../../ControleEPI-desenho.md
## Global Constraints
Mensagens em português; sem câmera/QR/Cloudinary. Sem credenciais ou google-services.json versionado. Perfis apenas console. Nenhuma escrita cliente em usuarios. Branch por história; sem merge na main sem revisão humana. Dependências de teste separadas.
## Review Focus
Conta trocada durante callback: descartar resultado antigo. Rotação durante gravação: ID preservado e servidor consultado. Erro de snapshot: retornar sem tratar como vazio. Data de hoje/+30/+31: UTC civil coerente. Campos Firestore manipulados: testes de segurança negativos.
## Etapas
### 1. Auth e base
Arquivos: build.gradle, app/build.gradle, manifest, ControleEpiApp, LoginActivity, AguardandoPerfilActivity, BaseActivity, data/AuthRepository, data/UsuarioRepository, util/Validacao, model/Usuario, XML/tema.
Interfaces: AuthRepository.usuario(), entrar(email,senha), cadastrar(email,senha), sair(); UsuarioRepository.observar(uid, callback).
- [ ] Testar Validacao.email/senha com entradas inválidas antes da implementação.
- [ ] Configurar projeto sem credenciais: ausência de Firebase mostra instrução clara; não simular conexão.
- [ ] Implementar login, espera, sessão e guarda por perfil.
- [ ] Verificar testes/build, registrar limitações reais e commit em feat/auth-perfis.
### 2. C1 EPIs
Arquivos: model/Epi, data/EpiRepository, EpisActivity, CadastroEpiActivity, adapter/EpiAdapter, XML.
Interfaces: observar(callback), salvar(id, Epi).
- [ ] Testar CA/data inválidos e limite civil.
- [ ] Implementar cadastro e lista, rules restritivas desde já.
- [ ] Verificar build/testes e commit feat/c1-epis.
### 3. C2 Colaboradores
Arquivos: model/Colaborador, data/ColaboradorRepository, ColaboradoresActivity, CadastroColaboradorActivity.
Interfaces: observar(callback), salvar(uid, Colaborador).
- [ ] Testar política UID inexistente/perfil incorreto/registro existente.
- [ ] Implementar vínculo UID sem expor catálogo de usuarios.
- [ ] Verificar e commit feat/c2-colaboradores.
### 4. C3 Entregas
Arquivos: model/Entrega, data/EntregaRepository, NovaEntregaActivity, XML.
Interfaces: registrar(id, Entrega), observar(uid, callback), confirmar(id).
- [ ] Testar quantidade 0/negativa/decimal/9999/10000 e IDs repetidos.
- [ ] Implementar seleção por IDs, snapshots de descrição/CA, data, transação create-only, estado de envio.
- [ ] Verificar e commit feat/c3-entregas.
### 5. C4 Confirmação
Arquivos: MinhasEntregasActivity, adapter/EntregaAdapter, XML.
- [ ] Testar leitura alheia/alteração quantidade/confirmacao reversa bloqueadas.
- [ ] Implementar query filtrada pelo UID e confirmação true apenas.
- [ ] Verificar e commit feat/c4-confirmacao.
### 6. C5 Ficha
Arquivos: FichaColaboradorActivity, reutilização de EntregaAdapter/Repository.
- [ ] Verificar query filtrada e vazios.
- [ ] Implementar ficha somente técnico, sem botão de confirmação.
- [ ] Verificar e commit feat/c5-ficha.
### 7. C6 Vencimentos
Arquivos: CasVencendoActivity, util/Datas, EpiRepository.
- [ ] Testar ontem/hoje/+30/+31 com Clock controlado.
- [ ] Implementar consulta ordenada com limite inclusivo.
- [ ] Verificar e commit feat/c6-vencimentos.
### 8. Segurança, documentação e revisão
Arquivos: firestore.rules, firestore.indexes.json, firebase.json, tests/rules.test.mjs, README, docs/checklist.md, docs/revisao.md.
- [ ] Executar tests JVM e rules no emulador; build debug e lint.
- [ ] Verificar rotação, duas contas/aparelhos se ambiente permitir.
- [ ] Revisão independente, corrigir achados e registrar evidências.
- [ ] Atualizar README, setup Firebase, dados exemplos, demonstração e checklist honesta.
- [ ] Publicação rules, contas e PRs reais dependem de projeto remoto/integrantes; não declarar concluídos sem evidência.
## Decisões de execução
Continuidade autorizada pelo usuário; execução inline para evitar uma nova rodada de escolha de processo. Projeto novo isolado, sem worktree de repositório existente. Branches encadeadas localmente para preservar dependências; PRs reais e merge aguardam remoto e revisão humana. Não copiar configurações Firebase de projetos de referência.

