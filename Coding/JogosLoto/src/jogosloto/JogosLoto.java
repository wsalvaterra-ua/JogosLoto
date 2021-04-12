
package jogosloto;

import java.util.Scanner;

/**
 * User Interface do programa Jogos Loto.
 * Ponto Inicial do programa, apresenta um menu, onde é possivel ver o cartão,  e sortear número.
 * @author Rui Oliveira e William Salvaterra
 */
public class JogosLoto {

 
/**
 * Ponto Inicial do programa, inicia o menu do jogo.
     * @param args Recebe argumentos da linha de comandos ao executar o programa
 */
    public static void main(String[] args) {
            
            display_menuInicial(null);      
    }
/**
 * Função que apresenta um menu, onde é possivel ver o cartão, e sortear número ou Terminar o Programa
     * @param cartao recebe como parametro um objeto cartão que será usado durante o jogo
 */
    public static void display_menuInicial(Cartao cartao){
        if(cartao== null){
            cartao = new Cartao();
            System.out.println("Cartão criado com sucesso.");          
        }
        
        System.out.println("*************************** Jogo Do Loto ***************************");
        System.out.println("1-Ver Cartão.");
        System.out.println("2-Sortear um número.");
        System.out.println("3-Sair.");

        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()){
            case 1: 
                Slot_Numero [][] slot = cartao.getSlot();
                for(int i = 0 ; i<cartao.getLinhas_dim(); i++){
                    for( int j = 0 ; j < cartao.getColunas_dim(); j++){
                        System.out.print("[");
                        if(slot[i][j] != null){
                            if(slot[i][j].getMarcado())
                                System.out.print("x" + slot[i][j].getNumero() + "x");
                            else System.out.printf(" %02d ",slot[i][j].getNumero());  
                        }else System.out.print("    ");
                        System.out.print("] ");  
                    }
                    System.out.println("");
                }     
                display_menuInicial(cartao);
                break;
            case 2:
                int numero;
                System.out.println("Introduza o número sorteado: ");
                numero = sc.nextInt();
                if(cartao.MarcarNumeroSorteado(numero)){
                    System.out.println("Numero: " + numero + " foi sorteado.\n");
                    if(cartao.getSlots_disponiveis()[0] <1 && cartao.getSlots_disponiveis()[1] <1 && cartao.getSlots_disponiveis()[2] <1)
                        completed_card(cartao);
                }
                else  System.out.println("Numero: " + numero + " não foi sorteado.\n");
                display_menuInicial(cartao);
                break;
            case 3:
                break;
            default:
                System.out.println("Número Inválido.");
                display_menuInicial(cartao);
        } 
    }
/**
 * Função que apresenta um menu, quando o utilizador marcar todos os números do cartão.
 * O utilizador tem a possibilidade de iniciar um novo jogo com um novo cartão, com um novo cartão, ou terminar o jogo.
       * @param cartao recebe como parametro o objeto cartão utilizado anteriormente dando ao utilizador 
       * a possibilidade de iniciar um novo jogo com o mesmo cartão.
       * 
 */
    public static void completed_card(Cartao cartao){
        System.out.println("Terminou o Jogo, Parabéns!");
        System.out.println("Escolha a opção seguinte: ");
        System.out.println("1-Iniciar um novo jogo com um novo cartão.");
        System.out.println("2-Iniciar um novo jogo com o mesmo cartão.");
        System.out.println("3-Exit.");
        Scanner sc = new Scanner(System.in);
        
        switch (sc.nextInt()){
            case 1: 
                System.out.println("");
                display_menuInicial(null);
                break;
            case 2:
                System.out.println("");
                cartao.DesMarcarNumeros();
                display_menuInicial(cartao);               
            case 3:
                break;
            default:
                System.out.println("Número Inválido.");
                completed_card(cartao);
        }  
    }
}