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
    
    //menu inicial com as opções para o utilizador
    public void display_menuInicial(){
        System.out.println("*************************** Jogo Do Loto ***************************");
        System.out.println("Escolha a opção seguinte: ");
        System.out.println("1-Ver o seu cartão.");
        System.out.println("2-Sortear Número.");
        System.out.println("3-Exit.");
        Scanner sc = new Scanner(System.in);
        
        switch (sc.nextInt()){
            case 1:
                //cartao.construir_cartao();
                //System.out.println(cartao);
            case 2:
                //cartao.receber_numero_sorteado();
                //cartao.getSlots_disponiveis();
                question();
            case 3:
                break;
            default:
                System.out.println("Número Inválido.");
                display_menuInicial();               
        }
    }
    
    //menu para após o jogo terminar
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
    
    //utilizador introduz o número sorteado
    public void receber_numero_sorteado(){
            System.out.println("Introduza o número sorteado: ");
            int numero = sc.nextInt();
            //cartao.MarcarNumeroSorteado(numero);
    } 
}
