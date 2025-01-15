public class ArvoreBMais {
    private No raiz;
    private int grau;

    public ArvoreBMais(int grau) {
        if (grau < 3) {
            throw new IllegalArgumentException("Grau deve ser pelo menos 3");
        }
        this.raiz = new No(true);
        this.grau = grau;
    }

    public boolean buscar(int chave) {
        No atual = raiz;
        while (!atual.folha) {
            int i = 0;
            while (i < atual.chaves.size() && chave >= atual.chaves.get(i)) {
                i++;
            }
            atual = atual.filhos.get(i);
        }
        return atual.chaves.contains(chave);
    }

    public void inserir(int chave) {
        if (raiz.chaves.size() == grau - 1) {
            No novaRaiz = new No(false);
            novaRaiz.filhos.add(raiz);
            dividirFilho(novaRaiz, 0);
            raiz = novaRaiz;
        }
        inserirNaoCheio(raiz, chave);
    }

    private void inserirNaoCheio(No no, int chave) {
        if (no.folha) {
            int i = 0;
            while (i < no.chaves.size() && chave > no.chaves.get(i)) {
                i++;
            }
            no.chaves.add(i, chave);
        } else {
            int i = no.chaves.size() - 1;
            while (i >= 0 && chave < no.chaves.get(i)) {
                i--;
            }
            i++;
            
            if (no.filhos.get(i).chaves.size() == grau - 1) {
                dividirFilho(no, i);
                if (chave >= no.chaves.get(i)) {
                    i++;
                }
            }
            inserirNaoCheio(no.filhos.get(i), chave);
        }
    }

    private void dividirFilho(No pai, int indice) {
        No filho = pai.filhos.get(indice);
        No novoFilho = new No(filho.folha);
        int meio = (grau - 1) / 2;

        // Para nós folha
        if (filho.folha) {
            // Copiar metade das chaves para o novo nó
            for (int j = meio; j < filho.chaves.size(); j++) {
                novoFilho.chaves.add(filho.chaves.get(j));
            }
            // Remover as chaves copiadas do nó original
            while (filho.chaves.size() > meio) {
                filho.chaves.remove(meio);
            }
            // Atualizar ponteiros da lista ligada
            novoFilho.proximo = filho.proximo;
            filho.proximo = novoFilho;
            // Adicionar a chave de separação no pai (cópia da primeira chave do novo nó)
            pai.chaves.add(indice, novoFilho.chaves.get(0));
        } else {
            // Para nós internos
            // Copiar metade das chaves para o novo nó
            for (int j = meio + 1; j < filho.chaves.size(); j++) {
                novoFilho.chaves.add(filho.chaves.get(j));
            }
            // Copiar os filhos correspondentes
            for (int j = meio + 1; j < filho.filhos.size(); j++) {
                novoFilho.filhos.add(filho.filhos.get(j));
            }
            // Remover as chaves e filhos copiados do nó original
            while (filho.chaves.size() > meio) {
                filho.chaves.remove(meio);
            }
            while (filho.filhos.size() > meio + 1) {
                filho.filhos.remove(meio + 1);
            }
            // Adicionar a chave de separação no pai
            pai.chaves.add(indice, filho.chaves.get(meio - 1));
        }
        
        pai.filhos.add(indice + 1, novoFilho);
    }

    public void remover(int chave) {
        if (raiz == null) return;
        
        removerRecursivo(raiz, chave);
        
        if (raiz.chaves.isEmpty() && !raiz.folha) {
            raiz = raiz.filhos.get(0);
        }
    }

    private void removerRecursivo(No no, int chave) {
        int pos = encontrarPosicao(no, chave);

        if (no.folha) {
            if (pos < no.chaves.size() && no.chaves.get(pos) == chave) {
                no.chaves.remove(pos);
            }
            return;
        }

        if (pos < no.chaves.size() && no.chaves.get(pos) == chave) {
            // Caso 1: Chave encontrada em nó interno
            No predecessor = obterPredecessor(no.filhos.get(pos));
            no.chaves.set(pos, predecessor.chaves.get(predecessor.chaves.size() - 1));
            removerRecursivo(no.filhos.get(pos), no.chaves.get(pos));
        } else {
            // Caso 2: Chave está em subárvore
            No filho = no.filhos.get(pos);
            if (filho.chaves.size() < (grau / 2)) {
                preencherFilho(no, pos);
            }
            if (pos > no.chaves.size()) {
                removerRecursivo(no.filhos.get(pos - 1), chave);
            } else {
                removerRecursivo(no.filhos.get(pos), chave);
            }
        }
    }

    private int encontrarPosicao(No no, int chave) {
        int pos = 0;
        while (pos < no.chaves.size() && chave > no.chaves.get(pos)) {
            pos++;
        }
        return pos;
    }

    private No obterPredecessor(No no) {
        while (!no.folha) {
            no = no.filhos.get(no.filhos.size() - 1);
        }
        return no;
    }

    private void preencherFilho(No no, int pos) {
        if (pos > 0 && no.filhos.get(pos - 1).chaves.size() >= grau / 2) {
            emprestarDoAnterior(no, pos);
        } else if (pos < no.filhos.size() - 1 && 
                   no.filhos.get(pos + 1).chaves.size() >= grau / 2) {
            emprestarDoProximo(no, pos);
        } else {
            if (pos < no.filhos.size() - 1) {
                mesclarNos(no, pos);
            } else {
                mesclarNos(no, pos - 1);
            }
        }
    }

    private void emprestarDoAnterior(No no, int pos) {
        No filho = no.filhos.get(pos);
        No irmao = no.filhos.get(pos - 1);

        filho.chaves.add(0, no.chaves.get(pos - 1));
        if (!filho.folha) {
            filho.filhos.add(0, irmao.filhos.remove(irmao.filhos.size() - 1));
        }
        no.chaves.set(pos - 1, irmao.chaves.remove(irmao.chaves.size() - 1));
    }

    private void emprestarDoProximo(No no, int pos) {
        No filho = no.filhos.get(pos);
        No irmao = no.filhos.get(pos + 1);

        filho.chaves.add(no.chaves.get(pos));
        no.chaves.set(pos, irmao.chaves.remove(0));
        if (!filho.folha) {
            filho.filhos.add(irmao.filhos.remove(0));
        }
    }

    private void mesclarNos(No no, int pos) {
        No filho = no.filhos.get(pos);
        No irmao = no.filhos.get(pos + 1);

        if (!filho.folha) {
            filho.chaves.add(no.chaves.get(pos));
        }

        filho.chaves.addAll(irmao.chaves);
        if (!filho.folha) {
            filho.filhos.addAll(irmao.filhos);
        } else {
            filho.proximo = irmao.proximo;
        }

        no.chaves.remove(pos);
        no.filhos.remove(pos + 1);
    }

    public void imprimirArvore() {
        imprimirArvoreRecursivo(raiz, 0);
        System.out.println("\nFolhas conectadas:");
        imprimirFolhas();
    }

    private void imprimirArvoreRecursivo(No no, int nivel) {
        System.out.println("Nível " + nivel + ": " + no.chaves + (no.folha ? " (folha)" : " (interno)"));
        if (!no.folha) {
            for (No filho : no.filhos) {
                imprimirArvoreRecursivo(filho, nivel + 1);
            }
        }
    }

    private void imprimirFolhas() {
        No atual = encontrarPrimeiraFolha(raiz);
        while (atual != null) {
            System.out.print(atual.chaves + " -> ");
            atual = atual.proximo;
        }
        System.out.println("null");
    }

    private No encontrarPrimeiraFolha(No no) {
        while (!no.folha) {
            no = no.filhos.get(0);
        }
        return no;
    }
}