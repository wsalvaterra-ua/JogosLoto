package JogosLotoJogador;


import java.util.HashMap;
import java.util.Iterator;
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
        System.out.println("3-Gerar novo Cartão.");
        System.out.println("4-Verificar Integridade.");
        System.out.println("5-Editar Cartao(Linha Coluna Numero).");
        System.out.println("6-Sair.");

        Scanner sc = new Scanner(System.in);
        Iterator<HashMap<Integer,Slot_Numero >> iteradorArrayList = cartao.getLinhasArrayList().iterator();
        switch (sc.nextInt()){
            case 1:
                
                
                while ( iteradorArrayList.hasNext() ) 
                {
                    HashMap<Integer,Slot_Numero > coluna = iteradorArrayList.next();

                    for (int i : coluna.keySet()) {
                        System.out.print("[");
                        if( coluna.get(i) != null){
                            if(coluna.get(i).getMarcado())
                                System.out.print("x" + coluna.get(i).getNumero() + "x");
                            else System.out.printf(" %02d ",coluna.get(i).getNumero());  
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
                HashMap<Integer,Slot_Numero > linhaSelecionada = cartao.MarcarNumeroSorteado(numero);
                if(linhaSelecionada != null){
                    System.out.println("Numero: " + numero + " foi sorteado.\n");
                    boolean temNumerosNaoMarcados = false;     
                    boolean temNumerosNaoMarcadosNaLinha = false;     
                    while ( iteradorArrayList.hasNext() ){ 
                        HashMap<Integer,Slot_Numero > linhaIT = iteradorArrayList.next();

                        for (int i : linhaIT.keySet()) 
                            if( linhaIT.get(i) != null){
                                if(linhaIT.get(i).getMarcado() == false)
                                    temNumerosNaoMarcados = true;
                                if(linhaIT == linhaSelecionada)
                                    if(linhaIT.get(i).getMarcado() == false)
                                        temNumerosNaoMarcadosNaLinha = true;
                            }
                    }
                    
                    
                    if(!temNumerosNaoMarcadosNaLinha){
                        System.out.println("A linha " + (cartao.LinhasArrayList.indexOf(linhaSelecionada) +1) +  " foi completamente marcada!");
                    }
                    if(!temNumerosNaoMarcados){
                        completed_card(cartao);
                        break;
                    }
                }
                else  System.out.println("Numero: " + numero + " não foi sorteado.\n");
                display_menuInicial(cartao);
                break;
            case 3:
                display_menuInicial(null);
                break;
            case 4:
                if(cartao.verificar_integridade())
                    System.out.println("Integro");
                else
                    System.out.println("Nao Integro");
                display_menuInicial(cartao);
                break;
            case 5:
                System.out.println("Introduza  separado por espaço a Linha Coluna Numero");
                if(cartao.editar_cartao(sc.nextInt(),sc.nextInt(),sc.nextInt()))
                    System.out.println("Editado com sucesso");
                else System.out.println("Erro ao editar");
                display_menuInicial(cartao);
                //editar
                break;
            case 6:
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