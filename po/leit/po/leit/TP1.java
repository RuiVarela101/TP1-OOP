package po.leit;

import javax.swing.*;

import po.leit.ui.Le;
import po.leit.ui.MyCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }

                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();


                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont=0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */

    public static void mostrarAlunos() {
        if (nomeAlunos[0] == null) {
            interC.showMsg("Não há dados");
        } else {
            System.out.printf("%-2s %-25s", "Codigo","Nome do Estudante");
            System.out.print("\n");
            int contador=1;
            for (String nome:nomeAlunos) {
                if (nome != null && nome.length() > 0) {
                    System.out.printf("%06d %-25s",contador++, nome.trim().strip());
                    System.out.print("\n");
                } else {
                    continue;
                }
            }
            Le.umChar();
        }
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */

    public static void mostrarAlunos(int tela) {
        boolean mudarPagina=false;
        int pagina=0;
        if (nomeAlunos[0] == null) {
            interC.showMsg("Não há dados");
        } else {
            System.out.printf("%-2s %-25s", "Codigo","Nome do Estudante");
            System.out.print("\n");
            int contador=1;
            for (String nome:nomeAlunos) {
                if (nome != null && nome.length() > 0) {
                    if (mudarPagina) {
                        System.out.printf("%-2s %-25s", "Codigo", "Nome do Estudante");
                        System.out.print("\n");
                        mudarPagina=false;
                    }
                    System.out.printf("%06d %-25s", contador++, nome.trim().strip());
                    pagina++;
                    System.out.print("\n");
                } else {
                    continue;
                }
                if (pagina == tela) {
                    interC.showMsg("\nEnter para continuar ... ");
                    pagina = 0;
                    mudarPagina = true;
                }
            }
        }
        Le.umChar();
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */

    private static void calcularMaxMinAvg() {
        if(notasAlunos == null)
            System.out.println("Nao ha dados");
        else{
            for(int nota: notasAlunos){
                notaAvg += nota;
                if(nota > notaMax)
                    notaMax = nota;
                else if(notaMin > nota)
                    notaMin = nota;
                    }
                }
        notaAvg = notaAvg/alunosLidos;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */

    public static void mostraResumo() {
        calcularMaxMinAvg();
        int i=0,j=0;
        System.out.println("Numero de Alunos = " + alunosLidos);
        System.out.println("Media = " + notaAvg);
        System.out.println("Nota Maxima = " + notaMax);
        System.out.println("Nota Minima = " + notaMin);
        for(int nota : notasAlunos){
            if(nota > notaAvg)
                i+=1;
            else
                j+=1;
        }
        System.out.println("Numero de alunos com nota superior a media = " + i);
        System.out.println("Numero de alunos com nota inferior a media = " + j);
        interC.showMsg("");
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */

    public static void mostrarTop() {
        int m1=0,m2=0,m3=0;
        for(int i = 0; alunosLidos > i; i++){
            if(notasAlunos[i] > m1){
                m2=m3;
                m2=m1;
                m1=i;
            }
            else if(notasAlunos[i] > m2){
                m3=m2;
                m2=i;
            }
            else if(notasAlunos[i] > m3)
                m3=i;
        }

        System.out.println("Melhores Notas:");
        System.out.println(nomeAlunos[m1] + "  " + notasAlunos[m1]);
        System.out.println(nomeAlunos[m2]+ "  " + notasAlunos[m2]);
        System.out.println(nomeAlunos[m3] + "  " + notasAlunos[m3]);
        interC.showMsg("");
    }

    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */

    public static void mostrarPauta() {
        Scanner xp = new Scanner(System.in);
        int control = 9;
        for(int i = 0; alunosLidos > i; i++){
            System.out.println(nomeAlunos[i] + "   " + notasAlunos[i]);
            if (i == control){
                System.out.println("\nPressione enter para apresentar mais nomes: ");
                xp.nextLine();
                control = control + 10;
            }
        }
        interC.showMsg("");
    }

    /**
     * Método a ser implementando no TP1
     * Apresentar para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */

    public static void mostrarDetalhesAluno(String nome) { 
        String[] nomeSeparado = nome.split(" ");
        boolean notfound = true;
        boolean sameName = false;
        for (int i = 0; i < nomeAlunos.length; i++) {
            if(nomeAlunos[i] != null){
                String[] palavras = nomeAlunos[i].split(" ");
                for (int j = 0; j < palavras.length; j++){
                    if (j == 0){
                        if (palavras[0].substring(0,2).toLowerCase().equals(nomeSeparado[0].substring(0,2).toLowerCase())){
                            System.out.println(nomeAlunos[i] + "   " + notasAlunos[i]);
                            notfound = false;
                            sameName = true;
                        }
                    }else{
                        for (String nn: nomeSeparado){
                            if (palavras[j].equals(nn) && sameName != true){
                                System.out.println(nomeAlunos[i] + "   " + notasAlunos[i]);
                                notfound = false;
                            }
                        }
                    }
                }
            }
            sameName = false;
        }
        if(notfound)
            System.out.println("Nenhum resultado encontrado");
        interC.showMsg("");
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */

    public static void pesquisar(String nome) {
        mostrarDetalhesAluno(nome);
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */

    public static void pesquisar(int nota) {
        boolean notfound = true;
        for(int i = 0; alunosLidos > i; i++){
            if (nota == notasAlunos[i]){
                System.out.println("Nota encontrada: " + nomeAlunos[i] + "   " + notasAlunos[i]);
                notfound = false;}
        }
        if(notfound)
            System.out.println("Nota nao encontrada");
        interC.showMsg("");
    }

    private String[] searchByName(String nome) {
        return null;
    }

}
