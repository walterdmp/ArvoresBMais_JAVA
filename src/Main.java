
public class Main {

	public static void main(String[] args) {
        ArvoreBMais arvore = new ArvoreBMais(4);
        int[] valores = {10, 20, 5, 6, 12, 30, 7, 17};
        
        for (int valor : valores) {
            arvore.inserir(valor);
            System.out.println("\nApós inserir " + valor + ":");
            arvore.imprimirArvore();
        }

        System.out.println("\nBuscando valores:");
        System.out.println("6: " + arvore.buscar(6));
        System.out.println("15: " + arvore.buscar(15));

        System.out.println("\nRemovendo valores:");
        arvore.remover(6);
        System.out.println("Após remover 6:");
        arvore.imprimirArvore();
    }

}
