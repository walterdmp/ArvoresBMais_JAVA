import java.util.ArrayList;
import java.util.List;

class No {
    List<Integer> chaves;
    List<No> filhos;
    boolean folha;
    No proximo;  // Ponteiro para o próximo nó folha

    public No(boolean folha) {
        this.chaves = new ArrayList<>();
        this.filhos = new ArrayList<>();
        this.folha = folha;
        this.proximo = null;
    }
}