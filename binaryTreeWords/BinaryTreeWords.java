/***************************************************
2 *                                                  *
3 * Júlio Cézar Lossavaro                            *
4 * 2018.0743.029.4                                  *
5 * Implementacão 2                                  *
6 * Disciplina: Estruturas de Dados e Programação I  *
7 * Professor: Ronaldo Fiorilo                       *
8 *                                                  *
9 ***************************************************/




import java.util.Scanner;

public class BinaryTreeWords {

    public static void main(String[] args) {
        Tree tree = new Tree();
        Scanner s1 = new Scanner(System.in);
        String commands = "";
        String[] comandos = new String[5];

        while (commands.compareTo("fim") != 0) {
            commands = s1.nextLine();
            comandos = commands.split(" ");
            if (comandos[0].compareTo("fim") == 0) {
                break;
            }
            tree.menu(comandos);
        }
    }
}
