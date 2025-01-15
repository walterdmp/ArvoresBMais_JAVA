# Introdução

- As Árvores B+ são uma estrutura de dados utilizada para organizar e acessar grandes volumes de informações de forma eficiente.
- Diferentemente de listas ou vetores, as Árvores B+ mantêm os dados organizados e balanceados, otimizando o tempo das operações de busca, inserção e remoção.
- Elas são amplamente usadas em sistemas de banco de dados e arquivos, onde o desempenho é crítico.

## Estrutura da Apresentação

### 1. O que é uma Árvore B+?

- Uma Árvore B+ é uma variação da Árvore B, projetada para:
  - Armazenar os dados apenas nas folhas.
  - Ligar todas as folhas em uma lista encadeada, facilitando a navegação sequencial.
  - Utilizar nós internos apenas para armazenar chaves que direcionam as operações de busca.

### 2. Características Principais

- **Nós internos**: Contêm apenas as chaves usadas para navegar na árvore.
- **Nós folhas**: Armazenam todos os dados reais e estão ligados em sequência.
- **Grau da árvore**: Define o número máximo de filhos que cada nó pode ter.

## Operações Básicas

### Inserção

1. Localize a folha correta para inserir o novo valor.
2. Insira o valor na posição correta para manter a ordem.
3. Se o nó folha exceder sua capacidade, divida-o em dois e ajuste os nós superiores.

### Busca

1. Inicie a busca na raiz da árvore.
2. Navegue pelos nós internos até chegar à folha que contém o valor desejado.
3. Verifique se o valor está presente.

### Remoção

1. Localize o valor a ser removido na folha.
2. Remova o valor e ajuste a estrutura, se necessário, para manter a árvore balanceada.
