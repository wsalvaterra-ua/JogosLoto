/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosloto;

import java.util.Scanner;

/**
 *
 * @author bil
 */
public class JogosLoto {

    Scanner sc;
//    Cartao cartao;
    public static void main(String[] args) {
            Cartao cartao = null;
            display_menuInicial(cartao);
            
    }
    
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
                for(int i = 0 ; i<3; i++){
                    for( int j = 0 ; j < 9; j++){
                        System.out.print("[");
                        if(slot[i][j] != null){
                            if(slot[i][j].getMarcado())
                                System.out.print("\"" + slot[i][j].getNumero() + "\"");
                            else System.out.print(slot[i][j].getNumero());  
                        }else System.out.print(" ");
                        System.out.print("] ");  
                    }
                    System.out.println("");
                } 
                sc.next();
                display_menuInicial(cartao);
                break;
            case 2:
                int numero;
                System.out.println("Introduza o número sorteado: ");
                numero = sc.nextInt();
                if(cartao.MarcarNumeroSorteado(numero)) System.out.println("Numero: " + numero + " foi sorteado.");
                else  System.out.println("Numero: " + numero + " não foi sorteado.");
                display_menuInicial(cartao);
                break;
            case 3:
                break;
            default:
                System.out.println("Número Inválido.");
                display_menuInicial(cartao);
        } 
    }
    
    public void question(){
        System.out.println("Terminou o Jogo, Parabéns!");
        System.out.println("Escolha a opção seguinte: ");
        System.out.println("1-Iniciar um novo jogo com um novo cartão.");
        System.out.println("2-Iniciar um novo jogo com o mesmo cartão.");
        System.out.println("3-Exit.");
        Scanner sc = new Scanner(System.in);
        
        switch (sc.nextInt()){
            case 1: 
                //cartao.contruir_Cartao();
                //cartao.receber_Numero_Sorteado();
                question();
            case 2:
                //cartao.receber_numero_sorteado();
                question();
            case 3:
                break;
            default:
                System.out.println("Número Inválido.");
                question();
        }  
    }
    public int receber_numero_sorteado(int numero){
            System.out.println("Introduza o número sorteado: ");
            numero = sc.nextInt();
            return numero;
    }    
}
