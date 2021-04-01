/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosloto;

import java.util.Scanner;

/**
 *
 * @author rui
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
                            else System.out.printf("%02d",slot[i][j].getNumero());  
                        }else System.out.print("  ");
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