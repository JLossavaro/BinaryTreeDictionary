 /** *************************************************
 * 2 *                                                  *
 * 3 * Júlio Cézar Lossavaro                            *
 * 4 * 2018.0743.029.4                                  *
 * 5 * Implementacão 2                                  *
 * 6 * Disciplina: Estruturas de Dados e Programação I  *
 * 7 * Professor: Ronaldo Fiorilo                       *
 * 8 *                                                  *
 * 9 ************************************************** */

import java.util.ArrayList;

public class Node implements Comparable<String> {

    char language;
    String value;
    ArrayList<String> synonyms;
    Node left;
    Node right;
    Node parent;

    public Node(String value, char language) {
        this.language = language;
        this.value = value;
        this.synonyms = new ArrayList<String>();

    }

    //Retorna true caso o nó tenha apenas um filho a esquerda
    public boolean hasOnlyLeftChild() {
        return (this.left != null && this.right == null);
    }

    //Retorna true caso o nó tenha apenas um filho a direita
    public boolean hasOnlyRightChild() {
        return (this.left == null && this.right != null);
    }

    //Retorna true caso o nó seja uma folha
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    //CompareTo adaptado para utilizar o primeiro valor lexicográfico como parametro de ordenação
    @Override
    public int compareTo(String o) {
        if (this.value.charAt(0) == o.charAt(0)) {
            return 0;
        }
        if (this.value.charAt(0) > o.charAt(0)) {
            return 1;
        } else {
            return -1;
        }
    }

}
