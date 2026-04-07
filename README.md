# Sistema de Cadastro de Eventos

Um aplicativo simples e direto ao ponto feito em Java (via terminal) para criar, listar e gerenciar a participação em eventos na cidade.

Este projeto foi construído de maneira simples para ser fácil de entender e expandir, contendo essencialmente apenas 3 arquivos de código fonte:

* **`Usuario.java`**: O modelo responsável por guardar os dados (nome, e-mail e telefone) do usuário logado no sistema.
* **`Evento.java`**: A estrutura que define o que compõe um evento (nome, categoria, endereço e horário).
* **`Main.java`**: Ponto central do programa. Responsável por exibir o menu interativo onde o usuário pode cadastrar novos eventos (salvos em um mini banco de dados `.dados`), ver os cadastrados ou adicionar seu nome na lista vip.

## 🚀 Como rodar o projeto

1. Navegue até a pasta do código fonte e compile os arquivos:
```bash
javac Main/src/*.java
```

2. Inicie o sistema no seu terminal executando a classe principal da sua pasta fonte:
```bash
java -cp Main/src Main
```

## 🛠️ Tecnologias
- **Java** puro
- Programação Orientada a Objetos
- Manipulação de Arquivos (`FileWriter` e `FileReader`) e Coleções (`ArrayList`)
