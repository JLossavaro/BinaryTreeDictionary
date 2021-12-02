/** *************************************************
 * 2 *                                                  *
 * 3 * Júlio Cézar Lossavaro                            *
 * 4 * 2018.0743.029.4                                  *
 * 5 * Implementacão 2                                  *
 * 6 * Disciplina: Estruturas de Dados e Programação I  *
 * 7 * Professor: Ronaldo Fiorilo                       *
 * 8 *                                                  *
 * 9 ************************************************** */

import java.util.Collections;

public class Tree {

    private Node root;

    public boolean isEmpty() {
        return this.root == null;
    }

    public void recursiveAdd(String[] elementos) {

        if (isEmpty()) {                                                                        //CASO 1(RAIZ VAZIA)
            this.root = new Node(elementos[2], elementos[1].charAt(0));                         //CASO 1 - RAIZ RECEBE UM NÒ
            recursiveAdd(this.root, elementos[4], elementos[3].charAt(0), elementos[2]);        //CASO 1 - JA QUE A RAIZ ESTA VAZIA ASSUMIMOS QUE O NÓ NÃO EXISTE, ADICIONAMOS NO
            root.synonyms.add(elementos[4]);
        } else {
            Node findNode = this.search(elementos[2], elementos[1].charAt(0));

            if (findNode == null) {                                                             //CASO 2(PALAVRA 1 NAO ENCONTRADA) 
                recursiveAdd(this.root, elementos[2], elementos[1].charAt(0), elementos[4]);    //CASO 2 - ENCADEIA A PROXIMA PALAVRA Ja QUE O SINONIMO ESTA VAZIO
            } else {
                if (findNode.synonyms == null) {                                                //CASO 3 -  ELA FOI ENCONTRADA
                    findNode.synonyms.add(elementos[4]);
                } else if (findNode.synonyms.contains(elementos[4]) == false) {                 //CASO 3 - PALAVRA JA EXISTE NA LISTA DE SINONIMOS             
                    findNode.synonyms.add(elementos[4]);                                        //CASO 3 - ENCADEIA A SEGUNDA PALAVRA NA LISTA  
                }
            }

            findNode = this.search(elementos[4], elementos[3].charAt(0));                        //CASO 1(PALAVRA 2) PROCURA A SEGUNDA PALAVRA 

            if (findNode == null) {                                                              //CASO 1 NAO ENCONTRADA
                recursiveAdd(this.root, elementos[4], elementos[3].charAt(0), elementos[2]);     //CASO 1 ADICIONAMOS A PALAVRA 2 A ESTRUTURA

            } else {                                                                             //CASO 2(PALAVRA 2) PALAVRA ENCONTRADA
                if (findNode.synonyms.contains(elementos[2]) == false) {                         //CASO 2 VERIFICA SE A PALAVRA ESTA NA LISTA DE SINONIMOS E ADICIONA(CASO NÃO)
                    findNode.synonyms.add(elementos[2]);
                }
            }
        }

    }

    //Função que percorre a estrutura recursivamente e adiciona a palavra a estrutura
    private void recursiveAdd(Node node, String element, char elem_lang, String Sym) {
        Node newNode = null;
        if (node.compareTo(element) > 0) {
            if (node.left == null) {
                newNode = new Node(element, elem_lang);
                newNode.synonyms.add(Sym);
                node.left = newNode;
                newNode.parent = node;
                return;
            }
            recursiveAdd(node.left, element, elem_lang, Sym);

        } else {
            if (node.right == null) {
                newNode = new Node(element, elem_lang);
                newNode.synonyms.add(Sym);
                node.right = newNode;
                newNode.parent = node;
                return;
            }
            recursiveAdd(node.right, element, elem_lang, Sym);
        }
    }

    //RETORNA O MENOR VALOR DA ESSQUERDA
    public Node min() {
        if (isEmpty()) {
            return null;
        }
        return min(this.root);
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }

    //RETORNA O MAIOR VALOR DA DIREITA
    public Node max() {
        if (isEmpty()) {
            return null;
        }
        Node node = this.root;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    //Procura um valor que pode substituir um nó(usado na remoção)
    public Node sucessor(Node node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return min(node.right);
        } else {
            Node aux = node.parent;
            while (aux != null && (node.compareTo(aux.value) < 0)) {
                aux = aux.parent;
            }
            return aux;
        }
    }

    //Responsavel por chamas as funções de remoção com base na entrada
    public void remove(String[] elements) {
        Node word = findWord(elements[1]);
        if (word != null) {
            if (elements.length == 2) {
                remove(word);
            } else {
                remove(word, elements[2]);
            }
        } else {
            System.out.println("comando invalido");
        }
    }

    //Função que remove um nó e adiciona o seu substituto(caso necessário)
    private void remove(Node toRemove) {
        // CASO 1: O Nó É UMA FOLHA
        if (toRemove.isLeaf()) {
            if (toRemove == this.root) {
                this.root = null;
            } else {
                if (toRemove.parent.compareTo(toRemove.value) > 0) {  
                    toRemove.parent.left = null;
                } else {
                    toRemove.parent.right = null;
                }
            }

            // CASO 2: UM FILHO A ESQUERDA OU DIREITA
        } else if (toRemove.hasOnlyLeftChild()) {
            if (toRemove == this.root) {
                this.root = toRemove.left;
                this.root.parent = null;
            } else {
                toRemove.left.parent = toRemove.parent;
                if (toRemove.parent.compareTo(toRemove.value) > 0) {
                    toRemove.parent.left = toRemove.left;
                } else {
                    toRemove.parent.right = toRemove.left;
                }
            }
        } else if (toRemove.hasOnlyRightChild()) {
            if (toRemove == this.root) {
                this.root = toRemove.right;
                this.root.parent = null;
            } else {
                toRemove.right.parent = toRemove.parent;
                if (toRemove.parent.compareTo(toRemove.value) > 0) {
                    toRemove.parent.left = toRemove.right;
                } else {
                    toRemove.parent.right = toRemove.right;
                }
            }

            // CASO 3: O NÓ TEM DOIS FILHOS
        } else {
            Node sucessor = sucessor(toRemove);
            toRemove.value = sucessor.value;
            toRemove.synonyms = sucessor.synonyms;
            toRemove.language = sucessor.language;
            remove(sucessor);
        }

    }

    //Remove sinonimos. Caso a lista esteja vazia ele apaga o nó
    public void remove(Node w, String wordTwo) {
        Node w2 = findWord(wordTwo);
        if (w != null && w2 != null) {
            w.synonyms.remove(w2.value);
            w2.synonyms.remove(w.value);

            if (w.synonyms.isEmpty()) {
                remove(w);
            }
            if (w2.synonyms.isEmpty()) {
                remove(w2);
            }
        } else {
            System.out.println("Uma das palavras não foi encontrada");
        }

    }

    //Procura uma palavra na estrutura, diferenciando paralavras em 'en' e 'pt'
    public Node search(String element, char elem_lang) {
        return recursiveSearch(this.root, element, elem_lang);
    }

    private Node recursiveSearch(Node node, String element, char elem_lang) {
        if (node == null) {
            return null;
        }

        if (element.compareTo(node.value) == 0 && node.language == elem_lang) {
            return node;
        }

        if (node.compareTo(element) > 0) {
            return recursiveSearch(node.left, element, elem_lang);
        } else {
            return recursiveSearch(node.right, element, elem_lang);
        }
    }

    //Procura uma palavra na estrutura independente da sua linguagem e printa seus sinonimos
    public void find(String element) {
        Node n = findWord(element);
        if (n != null) {
            for (int i = 0; i < n.synonyms.size(); i++) {
                System.out.println(n.synonyms.get(i));
            }
        } else {
            System.out.println("hein?");
        }

    }

    //Procura uma palavra na estrutura independente da sua linguagem e retorna um no
    public Node findWord(String element) {
        Node n = search(element, 'e');
        if (n != null) {
            return n;
        }

        n = search(element, 'p');
        if (n != null) {
            return n;
        } else {
            return null;
        }
    }

    //Percorre a arvore de maneira decrescente(Percurso em ordem)
    public void inOrder(char lang) {
        inOrder(this.root, lang);
    }

    //Percorre a arvore de maneira decrescente(Percurso em ordem)
    private void inOrder(Node node, char lang) {
        if (node != null) {
            inOrder(node.left, lang);
            if (node.language == lang) {
                Collections.sort(node.synonyms);

                String str = "";
                for (int i = 0; i < node.synonyms.size(); i++) {
                    if (i + 1 < node.synonyms.size()) {
                        str += node.synonyms.get(i) + ", ";
                    } else {
                        str += node.synonyms.get(i);
                    }
                }
                System.out.println(node.value + " : " + str);

            }
            inOrder(node.right, lang);
        }
    }

    //Percorre a arvore de maneira decrescente(Percurso em ordem), e retorna somente os valores entre dois caracteres
    public void inOrderBetwen(String[] letters) {
        char c[] = new char[2];
        c[0] = letters[2].charAt(0);
        c[1] = letters[3].charAt(0);
        inOrderBetwen(this.root, letters[1].charAt(0), c);
    }

    //Percorre a arvore de maneira decrescente(Percurso em ordem), e retorna somente os valores entre dois caracteres
    private void inOrderBetwen(Node node, char lang, char[] letter) {
        if (node != null) {
            inOrderBetwen(node.left, lang, letter);
            if (node.language == lang && (letter[0] <= node.value.charAt(0) && letter[1] >= node.value.charAt(0))) {
                Collections.sort(node.synonyms);
                String str = "";
                for (int i = 0; i < node.synonyms.size(); i++) {
                    if (i + 1 < node.synonyms.size()) {
                        str += node.synonyms.get(i) + ", ";
                    } else {
                        str += node.synonyms.get(i);
                    }
                }
                System.out.println(node.value + " : " + str);
            }
            inOrderBetwen(node.right, lang, letter);
        }
    }

    //menu responsavel por chamar funçoes de acordo com a entrada
    public void menu(String[] commands) {
        char c = commands[0].charAt(0);
        switch (c) {

            case 'i':
                recursiveAdd(commands);
                break;

            case 'l':
                if (commands.length > 2) {
                    inOrderBetwen(commands);
                    break;
                }
                inOrder(commands[1].charAt(0));
                break;

            case 'b':
                find(commands[1]);
                break;

            case 'r':
                remove(commands);
                break;
        }

    }

}
