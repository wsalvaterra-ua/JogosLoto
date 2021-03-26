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
    
    public void display_menuInicial(){
        System.out.println("*************************** Jogo Do Loto ***************************");
        System.out.println("O seu cartão é este: ");
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
