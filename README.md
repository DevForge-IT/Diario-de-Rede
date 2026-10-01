# Diário de Rede 📱📡

*Turma:* 3L6LASIR3T  
*Tema:* Tema C — Diário de Rede  

### Integrantes do Grupo
1. Britney Tembe - 202401081
2. Edson Alfredo Tala - 202402723
3. Enzo Rodrigues Guibango - 202401082
4. Vagner Seródio - 202402722

---

## 1. Descrição do Projeto
O *Diário de Rede* é uma aplicação móvel desenvolvida para a plataforma Android (em Java) que monitoriza em tempo real o estado de conexão à Internet do dispositivo (Wi-Fi, Dados móveis ou Sem ligação). 

A aplicação permite ao utilizador registar notas diárias associadas ao estado atual da rede, armazenando cada registo com um carimbo de data e hora gerado automaticamente.

---

## 2. Estrutura do Projeto

```plaintext
DiarioDeRede/
 ├── app/
 │    ├── manifests/
 │    │    └── AndroidManifest.xml
 │    ├── java/com/exemplo/diarioderede/
 │    │    ├── MainActivity.java       (Ecrã Principal - Estado da Rede)
 │    │    ├── NotesActivity.java      (Ecrã Secundário - Gestão de Notas)
 │    │    ├── Note.java               (Modelo de Dados)
 │    │    └── NoteAdapter.java        (Adapter para a RecyclerView)
 │    └── res/
 │         ├── layout/
 │         │    ├── activity_main.xml  (ConstraintLayout da MainActivity)
 │         │    ├── activity_notes.xml (ConstraintLayout da NotesActivity)
 │         │    └── item_note.xml      (Layout dos itens da lista)
 │         └── values/
 │              ├── strings.xml
 │              └── colors.xml
 └── README.md
