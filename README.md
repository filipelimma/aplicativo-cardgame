# aplicativo-cardgame
📱 Aplicativo Card Game – Android (Java)

Este projeto foi desenvolvido como atividade acadêmica para a disciplina de Desenvolvimento Android, utilizando Java e o Android Studio Narwhal (2025.1.1).

O aplicativo implementa um sistema de listagem e cadastro de entidades, aplicando corretamente o uso de Menus de Opções, Menu de Ação Contextual e Botão Up na AppBar, conforme as boas práticas da plataforma Android.

✨ Funcionalidades

📋 Tela de Listagem (MainActivity)

Exibição dos itens em uma lista.

Menu de opções na AppBar com:

Adicionar: abre a tela de cadastro aguardando retorno.

Sobre: abre a tela de autoria do aplicativo.

Menu de ação contextual ativado por pressionar um item da lista:

Editar: abre a tela de cadastro com os dados preenchidos.

Excluir: remove o item da lista e atualiza a interface.

✏️ Tela de Cadastro/Edição (CadastroActivity)

Funciona tanto para inclusão quanto para edição de itens.

Menu de opções com:

Salvar: valida e retorna os dados para a tela de listagem.

Limpar: limpa os campos do formulário e exibe uma mensagem via Toast.

Botão Up na AppBar para cancelar a operação e retornar à listagem.

👤 Tela de Autoria (AutoriaActivity)

Apresenta informações sobre a autoria do aplicativo.

Botão Up na AppBar para retorno à tela principal.

🛠️ Tecnologias e Configurações

Linguagem: Java

IDE: Android Studio Narwhal 2025.1.1

SDK:

minSdkVersion: 24

targetSdkVersion: 35

compileSdkVersion: 35

Interface construída com Layouts XML

Uso de:

Menu de opções (onCreateOptionsMenu)

Menu de ação contextual (ActionMode)

Comunicação entre Activities com startActivityForResult

🎯 Objetivo do Projeto

O objetivo principal é demonstrar a aplicação prática de:

Navegação entre Activities;

Uso correto da AppBar;

Manipulação de dados em listas;

Implementação de menus e ações contextuais;

Edição e atualização dinâmica da interface.
