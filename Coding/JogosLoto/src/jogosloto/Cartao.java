/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosloto;

import java.util.Random;

/**
 *
 * @author bilss
 */
public class Cartao {
    Slot_Numero [][] slot;
    int[][] slots_disponiveis;
    
    public Cartao() {
        slot = new Slot_Numero[3][15];
        construir_cartao();
    }
    public  void construir_cartao(){
        
        for(int i = 0 ; i<3; i++){
            int espacos_vazios_disponiveis = 4;
            int espacos_numeros_disponiveis = 5;
            for( int j = 0 ; j<9; j++){
                int resultRand =randomNum(0, 1);
                
                if(espacos_vazios_disponiveis> 0 &&  resultRand == 0 || espacos_numeros_disponiveis <1 && espacos_vazios_disponiveis> 0)
                    espacos_vazios_disponiveis--;
                else{
                    
                    espacos_numeros_disponiveis--;
                }
               
            }
        }
        
        
    }
    public static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
