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
    int[] slots_disponiveis;
    
    public Cartao() {
        slot = new Slot_Numero[3][9];
        slots_disponiveis = new int[3];
        
        construir_cartao();
    }

    public Slot_Numero[][] getSlot() {
        return slot;
    }
    
    private void construir_cartao(){
        
        for(int i = 0 ; i<3; i++){
            int espacos_vazios_disponiveis = 4;
            int espacos_numeros_disponiveis = 5;
            for( int j = 0 ; j < 9; j++){
                int resultRand =randomNum(0, 2);
                
                if(espacos_vazios_disponiveis> 0 &&  resultRand == 0 || espacos_numeros_disponiveis <1 && espacos_vazios_disponiveis> 0)
                    espacos_vazios_disponiveis--;
                else{
                    slot[i][j] = new Slot_Numero(randomNum( (j * 10 + ((j==8) ? 0:1 )) , (j * 10 + ((j==8) ? 10:9 ) )));
                    espacos_numeros_disponiveis--;
                }
            }
        }
        
        
    }
    private boolean MarcarNumeroSorteado(int numeroSorteado){
        for(int i = 0 ; i<3; i++){
            for( int j = 0 ; j < 9; j++){
                if(slot[i][j] != null)
                    if(!slot[i][j].getMarcado() && slot[i][j].getNumero() == numeroSorteado ){
                        slot[i][j].setMarcado(true);
                        slots_disponiveis[i]--;
                        if(slots_disponiveis[i]<1) System.out.println("Aviso: Linha "+ (i+1) + " está completa!");
                        return true;
                    } 
            }
        }
        return false;
    }
    
    private static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
